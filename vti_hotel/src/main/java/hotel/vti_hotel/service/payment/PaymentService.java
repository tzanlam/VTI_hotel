package hotel.vti_hotel.service.payment;

import hotel.vti_hotel.modal.request.PaymentRequest;
import hotel.vti_hotel.modal.response.dto.PaymentDTO;
import hotel.vti_hotel.repository.BookingRepository;
import hotel.vti_hotel.repository.FastBookingRepository;
import hotel.vti_hotel.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService implements IPaymentService {
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
    public PaymentDTO getPaymentByTransactionId(String transactionId) {
        return new PaymentDTO(paymentRepository.findByTransactionId(transactionId));
    }

    @Override
    public PaymentDTO createPayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentDTO handleVNPayResponse(Map<String, String> vnParams) {
        return null;
    }
}
