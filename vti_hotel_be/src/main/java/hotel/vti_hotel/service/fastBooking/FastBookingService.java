package hotel.vti_hotel.service.fastBooking;

import hotel.vti_hotel.modal.entity.*;
import hotel.vti_hotel.modal.request.BookingRequest;
import hotel.vti_hotel.modal.request.FastBookingRequest;
import hotel.vti_hotel.modal.response.dto.FastBookingDTO;
import hotel.vti_hotel.repository.FastBookingRepository;
import hotel.vti_hotel.repository.RoomRepository;
import hotel.vti_hotel.support.Calculate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static hotel.vti_hotel.modal.entity.Booking.TypeBooking.*;
import static hotel.vti_hotel.support.ConvertString.*;

@Service
public class FastBookingService implements IFastBooking{
    private final FastBookingRepository fastBookingRepository;
    private final RoomRepository roomRepository;
    public FastBookingService(FastBookingRepository fastBookingRepository, RoomRepository roomRepository) {
        this.fastBookingRepository = fastBookingRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<FastBookingDTO> getFastBookings() {
        return fastBookingRepository.findAll().stream()
                .map(FastBookingDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public FastBookingDTO getFastBookingById(int id) {
        return new FastBookingDTO(fastBookingRepository.findById(id).orElseThrow(()-> new NullPointerException("Not found with FastBooking id: "+ id)));
    }

    @Override
    public List<FastBookingDTO> getFastBookingByName(String name) {
        return fastBookingRepository.findByFullName(name).stream()
                .map(FastBookingDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<FastBookingDTO> getByPhoneNumber(String phoneNumber) {
        return fastBookingRepository.findByPhoneNumber(phoneNumber).stream()
                .map(FastBookingDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public FastBookingDTO createFastBook(FastBookingRequest request) {
        FastBooking fastBooking = new FastBooking();
        populate(fastBooking, request);
        return new FastBookingDTO(fastBookingRepository.save(fastBooking));
    }

    @Override
    public FastBookingDTO changeStatusFastBook(int id, String status) {
        return null;
    }

    private void populate(FastBooking faBooking, FastBookingRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NullPointerException("Room id " + request.getRoomId() + " not found"));
        faBooking.setFullName(request.getFullName());
        faBooking.setPhoneNumber(request.getPhoneNumber());
        if (room.getQuantity() != 0) {
            faBooking.setRoom(room);
        }else {
            throw new NullPointerException("Hết phòng");
        }
        faBooking.setType(convertToEnum(Booking.TypeBooking.class, request.getTypeBooking()));

        LocalDateTime checkIn ;
        LocalDateTime checkOut;
        double totalPrice;

        switch (faBooking.getType()) {
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
        faBooking.setCheckIn(checkIn);
        faBooking.setCheckOut(checkOut);
        faBooking.setTotalPrice(totalPrice);
        faBooking.setStatus(Booking.StatusBooking.PENDING);
    }
}
