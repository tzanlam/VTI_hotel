package hotel.vti_hotel.service.booking;

import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.modal.entity.Booking;
import hotel.vti_hotel.modal.entity.Room;
import hotel.vti_hotel.modal.entity.Voucher;
import hotel.vti_hotel.modal.request.BookingRequest;
import hotel.vti_hotel.modal.response.dto.BookingDTO;
import hotel.vti_hotel.repository.AccountRepository;
import hotel.vti_hotel.repository.BookingRepository;
import hotel.vti_hotel.repository.RoomRepository;
import hotel.vti_hotel.repository.VoucherRepository;
import hotel.vti_hotel.support.Calculate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static hotel.vti_hotel.support.ConvertString.buildLocalDateTime;
import static hotel.vti_hotel.support.ConvertString.convertToEnum;

@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;
    private final VoucherRepository voucherRepository;

    public BookingService(BookingRepository bookingRepository, AccountRepository accountRepository, RoomRepository roomRepository, VoucherRepository voucherRepository) {
        this.bookingRepository = bookingRepository;
        this.accountRepository = accountRepository;
        this.roomRepository = roomRepository;
        this.voucherRepository = voucherRepository;
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
    public BookingDTO createBooking(BookingRequest request) {
        Booking booking = new Booking();
        populateBooking(booking, request);
        bookingRepository.save(booking);
        return new BookingDTO(booking);
    }

    @Override
    public BookingDTO updateBooking(int id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("Booking id " + id + " not found")
        );
        populateBooking(booking, request);
        bookingRepository.save(booking);
        return new BookingDTO(booking);
    }


    private void populateBooking(Booking booking, BookingRequest request) {
        Account account = accountRepository.findById(booking.getAccount().getId()).orElseThrow(
                () -> new NullPointerException("Account id " + booking.getAccount().getId() + " not found")
        );
        Room room = roomRepository.findById(booking.getRoom().getId()).orElseThrow(
                () -> new NullPointerException("Room id " + booking.getRoom().getId() + " not found")
        );
        booking.setTypeBooking(convertToEnum(Booking.TypeBooking.class, request.getTypeBooking()));

        LocalDateTime checkIn = buildLocalDateTime(request.getCheckInDate(), request.getCheckInTime());
        LocalDateTime checkOut;

        double totalPrice = 0;

        switch (convertToEnum(Booking.TypeBooking.class, request.getTypeBooking())) {
            case HOURLY:
                if (request.getCheckOutTime() == null || request.getCheckOutTime().isBlank()) {
                    throw new IllegalArgumentException("HOURLY booking requires a valid checkout time.");
                }
                checkOut = buildLocalDateTime(request.getCheckInDate(), request.getCheckOutTime());
                if (checkOut.isBefore(checkIn) || Duration.between(checkIn, checkOut).toHours() > 8) {
                    throw new IllegalArgumentException("HOURLY booking must not exceed 8 hours and checkout must be after check-in.");
                }
                float fractionalHours = Duration.between(checkIn, checkOut).toHours();
                totalPrice = Calculate.calculatePriceHourly(room, fractionalHours);
                break;

            case DAILY:
                checkIn = checkIn.withHour(14).withMinute(0).withSecond(0).withNano(0);
                checkOut = checkIn.plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0);

                if (request.getCheckOutDate() != null && !request.getCheckOutDate().isBlank()) {
                    LocalDateTime customCheckOut = buildLocalDateTime(request.getCheckOutDate(), "12:00:00");
                    if (customCheckOut.isAfter(checkIn)) {
                        checkOut = customCheckOut;
                    } else {
                        throw new IllegalArgumentException("DAILY booking checkout must be after check-in.");
                    }
                }
                long days = Duration.between(checkIn, checkOut).toDays();
                totalPrice = Calculate.calculatePriceDay(room, (int) days);
                break;

            case NIGHTLY:
                checkIn = checkIn.withHour(22).withMinute(0).withSecond(0).withNano(0);
                checkOut = checkIn.plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0);
                totalPrice = Calculate.calculatePriceNight(room);
                break;

            default:
                throw new IllegalArgumentException("Invalid booking type.");
        }

        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);

        // Set giá vào booking
        booking.setTotalPrice(totalPrice);

        Voucher voucher = voucherRepository.findById(request.getVoucherId()).orElseThrow(
                () -> new NullPointerException("Voucher not found")
        );
        booking.setStatus(Booking.StatusBooking.PENDING);
    }
}
