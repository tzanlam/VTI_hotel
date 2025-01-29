package hotel.vti_hotel.service.booking;

import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.modal.entity.Booking;
import hotel.vti_hotel.modal.entity.Room;
import hotel.vti_hotel.modal.entity.Voucher;
import hotel.vti_hotel.modal.request.BookingRequest;
import hotel.vti_hotel.modal.request.MailSenderRequest;
import hotel.vti_hotel.modal.response.dto.BookingDTO;
import hotel.vti_hotel.repository.AccountRepository;
import hotel.vti_hotel.repository.BookingRepository;
import hotel.vti_hotel.repository.RoomRepository;
import hotel.vti_hotel.repository.VoucherRepository;
import hotel.vti_hotel.service.mailSender.IMailSender;
import hotel.vti_hotel.support.Calculate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static hotel.vti_hotel.support.ConvertString.*;

@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;
    private final VoucherRepository voucherRepository;
    private final IMailSender mailSender;

    public BookingService(BookingRepository bookingRepository, AccountRepository accountRepository, RoomRepository roomRepository, VoucherRepository voucherRepository, IMailSender mailSender) {
        this.bookingRepository = bookingRepository;
        this.accountRepository = accountRepository;
        this.roomRepository = roomRepository;
        this.voucherRepository = voucherRepository;
        this.mailSender = mailSender;
    }

    @Override
    public List<BookingDTO> readBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO readBooking(int bookingId) {
        return new BookingDTO(bookingRepository.findById(bookingId).orElseThrow(
                ()-> new NullPointerException("Booking id " + bookingId + " not found")
        ));
    }

    @Override
    public List<BookingDTO> readBookingsByName(String name) {
        return bookingRepository.findByAccount_Username(name).stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO createBooking(BookingRequest request) throws Exception {
        // Tạo đối tượng Booking từ BookingRequest
        Booking booking = new Booking();
        populateBooking(booking, request);

        // Lưu thông tin đặt phòng vào database
        bookingRepository.save(booking);

        // Chuẩn bị gửi email
        MailSenderRequest mailRequest = new MailSenderRequest();
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account ID không tồn tại"));

        String email = account.getEmail();
        if (email == null || email.isEmpty()) {
            throw new NullPointerException("Email của người đặt phòng không tồn tại");
        }

        // Thiết lập thông tin email
        mailRequest.setTo(email);
        mailRequest.setSubject("Thông tin đặt phòng của bạn");
        mailRequest.setBody(buildEmailBody(booking));

        // Gửi email thông báo thông tin đặt phòng
        mailSender.mailInformationBooking(booking.getId(), mailRequest);

        // Trả về DTO cho Booking
        return new BookingDTO(booking);
    }

    /**
     * Tạo nội dung email thông báo thông tin đặt phòng
     * @param booking Đối tượng Booking
     * @return Nội dung email dưới dạng chuỗi HTML
     */
    private String buildEmailBody(Booking booking) {
        String voucherInfo = (Objects.nonNull(booking.getVoucher())) ? booking.getVoucher().toString() : "Không có";
        return "<h1 style=\"text-align: center;\">Thông tin đặt phòng của bạn</h1>" +
                "<p><strong>Mã đặt phòng:</strong> " + booking.getId() + "</p>" +
                "<p><strong>Người đặt phòng:</strong> " + booking.getAccount().getFullName() + "</p>" +
                "<p><strong>Loại phòng:</strong> " + booking.getRoom().getName() + "</p>" +
                "<p><strong>Loại đặt phòng:</strong> " + booking.getTypeBooking() + "</p>" +
                "<p><strong>Thời gian nhận phòng:</strong> " + booking.getCheckIn() + "</p>" +
                "<p><strong>Thời gian trả phòng:</strong> " + booking.getCheckOut() + "</p>" +
                "<p><strong>Voucher:</strong> " + voucherInfo + "</p>" +
                "<p><strong>Tổng phí:</strong> " + booking.getTotalPrice() + " VND</p>";
    }

    @Override
    public BookingDTO updateBooking(int id, BookingRequest request){
        Booking booking = bookingRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("Booking id " + id + " not found")
        );
        populateBooking(booking, request);
        bookingRepository.save(booking);
        return new BookingDTO(booking);
    }


    private void populateBooking(Booking booking, BookingRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NullPointerException("Account id " + request.getAccountId() + " not found"));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NullPointerException("Room id " + request.getRoomId() + " not found"));

        booking.setAccount(account);
        if (room.getQuantity() != 0) {
            booking.setRoom(room);
        }else {
            throw new NullPointerException("Hết phòng");
        }
        booking.setTypeBooking(convertToEnum(Booking.TypeBooking.class, request.getTypeBooking()));

        LocalDateTime checkIn ;
        LocalDateTime checkOut;
        double totalPrice;

        switch (booking.getTypeBooking()) {
            case HOURLY:
                if (request.getCheckOutTime() == null || request.getCheckOutTime().isBlank()) {
                    throw new IllegalArgumentException("HOURLY booking requires a valid checkout time.");
                }
                if (request.getCheckInTime() == null || request.getCheckInTime().isBlank()) {
                    throw new IllegalArgumentException("HOURLY booking requires a valid checkin time.");
                }
                checkIn = buildLocalDateTime(request.getCheckInDate(), request.getCheckInTime());
                checkOut = buildLocalDateTime(request.getCheckInDate(), request.getCheckOutTime());
                if (checkOut.isBefore(checkIn) || Duration.between(checkIn, checkOut).toHours() > 8) {
                    throw new IllegalArgumentException("HOURLY booking must not exceed 8 hours and checkout must be after check-in.");
                }
                float hours = Duration.between(checkIn, checkOut).toHours();
                totalPrice = Calculate.calculatePriceHourly(room, hours);
                break;

            case DAILY:
                checkIn = buildLocalDateTime(request.getCheckInDate(), "12:00:00");
                checkOut = buildLocalDateTime(request.getCheckOutDate(), "12:00:00");
                if (checkOut.isBefore(checkIn)) {
                    throw new IllegalArgumentException("DAILY booking checkout must be after check-in.");
                }
                long days = Duration.between(checkIn, checkOut).toDays();
                totalPrice = Calculate.calculatePriceDay(room, (int) days);
                break;

            case NIGHTLY:
                checkIn = buildLocalDateTime(request.getCheckInDate(), "22:00:00");
                checkOut = checkIn.plusDays(1).withHour(12).withMinute(0);
                totalPrice = Calculate.calculatePriceNight(room);
                break;

            default:
                throw new IllegalArgumentException("Invalid booking type.");
        }
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setTotalPrice(totalPrice);
        Voucher voucher = voucherRepository.findById(request.getVoucherId()).orElse(null);
        booking.setVoucher(voucher);
        booking.setStatus(Booking.StatusBooking.PENDING);
        account.setAmountSpent(account.getAmountSpent() + totalPrice);
        account.setCumulativePoints(account.getCumulativePoints() + totalPrice/10000);
        accountRepository.save(account);
    }
}