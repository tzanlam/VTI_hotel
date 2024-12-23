package hotel.vti_hotel.service.payment;

import hotel.vti_hotel.modal.entity.Booking;
import hotel.vti_hotel.modal.entity.FastBooking;
import hotel.vti_hotel.modal.entity.Payment;
import hotel.vti_hotel.modal.request.PaymentRequest;
import hotel.vti_hotel.modal.response.dto.PaymentDTO;
import hotel.vti_hotel.repository.BookingRepository;
import hotel.vti_hotel.repository.FastBookingRepository;
import hotel.vti_hotel.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService implements IPaymentService {
    @Value("${vnp.tmn-code}")
    private String vnpTmnCode;

    @Value("${vnp.hash-secret}")
    private String vnpHashSecret;

    @Value("${vnp.url}")
    private String vnpPayUrl;

    @Value("${vnp.return-url}")
    private String vnpReturnUrl;

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final FastBookingRepository fastBookingRepository;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository, FastBookingRepository fastBookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.fastBookingRepository = fastBookingRepository;
    }

    @Override
    public List<PaymentDTO> getPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO getPaymentById(int id) {
        return new PaymentDTO(paymentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("Payment not found")
        ));
    }

    @Override
    public PaymentDTO getPaymentByTransactionId(String transactionId){
        return new PaymentDTO(paymentRepository.findByTransactionId(transactionId));
    }

    @Override
    public PaymentDTO createPayment(PaymentRequest request) {
        Payment payment = new Payment();

        // Kiểm tra loại đặt phòng
        Booking booking = null;
        FastBooking fastBooking = null;
        String orderInfo = "Thanh toán dịch vụ VTI Hotel";

        if (request.getBookingId() != 0) {
            booking = bookingRepository.findById(request.getBookingId()).orElseThrow(
                    () -> new NullPointerException("Booking not found")
            );
            orderInfo = "Thanh toán đặt phòng: " + booking.getId();
        } else if (request.getFastBookingId() != 0) {
            fastBooking = fastBookingRepository.findById(request.getFastBookingId()).orElseThrow(
                    () -> new NullPointerException("FastBooking not found")
            );
            orderInfo = "Thanh toán đặt phòng nhanh: " + fastBooking.getId();
        } else {
            throw new IllegalArgumentException("Either bookingId or fastBookingId must be provided");
        }

        try {
            // Bắt đầu xây dựng tham số thanh toán
            Map<String, String> vnpParams = new HashMap<>();
            vnpParams.put("vnp_Version", "2.1.0");
            vnpParams.put("vnp_Command", "pay");
            vnpParams.put("vnp_TmnCode", vnpTmnCode);

            // Tính giá trị thanh toán (đơn vị VNPay là VND * 100)
            double amount = booking != null ? booking.getTotalPrice() : fastBooking.getTotalPrice();
            vnpParams.put("vnp_Amount", String.valueOf((long) (amount * 100)));

            vnpParams.put("vnp_CurrCode", "VND");
            vnpParams.put("vnp_TxnRef", String.valueOf(System.currentTimeMillis())); // Unique ID
            vnpParams.put("vnp_OrderInfo", orderInfo);
            vnpParams.put("vnp_OrderType", "other");
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_ReturnUrl", vnpReturnUrl);
            vnpParams.put("vnp_CreateDate", java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

            // Sắp xếp tham số
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            vnpParams.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        hashData.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
                        try {
                            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII))
                                    .append('=')
                                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII))
                                    .append('&');
                        } catch (Exception ignored) {}
                    });

            // Remove trailing '&'
            hashData.setLength(hashData.length() - 1);
            query.setLength(query.length() - 1);

            // Tạo URL thanh toán
            String secureHash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(vnpHashSecret + hashData.toString());
            String paymentUrl = vnpPayUrl + "?" + query.toString() + "&vnp_SecureHash=" + secureHash;

            // Lưu Payment
            payment.setTransactionId(vnpParams.get("vnp_TxnRef"));
            payment.setPrice(amount);
            payment.setStatus(Payment.StatusPayment.PENDING);

            if (booking != null) {
                payment.setBooking(booking);
            } else {
                payment.setFastBooking(fastBooking);
            }

            paymentRepository.save(payment);

            // Trả về DTO
            PaymentDTO paymentDTO = new PaymentDTO(payment);
            paymentDTO.setPaymentUrl(paymentUrl);
            return paymentDTO;

        } catch (Exception e) {
            throw new RuntimeException("Error generating VNPay URL: " + e.getMessage(), e);
        }
    }

    @Override
    public PaymentDTO handleVNPayResponse(Map<String, String> vnParams) {
        try {
            // Lấy vnp_SecureHash từ params
            String vnpSecureHash = vnParams.get("vnp_SecureHash");
            vnParams.remove("vnp_SecureHash");

            // Sắp xếp tham số theo thứ tự từ điển
            StringBuilder hashData = new StringBuilder();
            vnParams.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> hashData.append(entry.getKey()).append('=').append(entry.getValue()).append('&'));

            // Xóa ký tự cuối cùng '&'
            hashData.setLength(hashData.length() - 1);

            // Xác thực chữ ký
            String calculatedHash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(vnpHashSecret + hashData.toString());
            if (!calculatedHash.equals(vnpSecureHash)) {
                throw new SecurityException("Invalid VNPay signature");
            }

            // Lấy các tham số cần thiết từ phản hồi VNPay
            String responseCode = vnParams.get("vnp_ResponseCode");
            String transactionId = vnParams.get("vnp_TxnRef");
            String transactionStatus = vnParams.get("vnp_TransactionStatus");
            double amount = Double.parseDouble(vnParams.get("vnp_Amount")) / 100;

            // Tìm thông tin thanh toán từ database
            Payment payment = paymentRepository.findByTransactionId(transactionId);

            // Cập nhật trạng thái thanh toán và phản hồi từ VNPay
            payment.setVnpResponseCode(responseCode);
            payment.setVnpTransactionStatus(transactionStatus);

            if ("00".equals(responseCode) && "00".equals(transactionStatus)) {
                payment.setStatus(Payment.StatusPayment.SUCCESS);
            } else {
                payment.setStatus(Payment.StatusPayment.FAILED);
            }

            paymentRepository.save(payment);

            // Trả về thông tin PaymentDTO
            return new PaymentDTO(payment);

        } catch (Exception e) {
            throw new RuntimeException("Error handling VNPay response: " + e.getMessage(), e);
        }
    }
}
