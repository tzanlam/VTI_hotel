package hotel.vti_hotel.service.fastbooking;

import hotel.vti_hotel.modal.entity.Booking;
import hotel.vti_hotel.modal.entity.FastBooking;
import hotel.vti_hotel.modal.request.FastBookingRequest;
import hotel.vti_hotel.modal.response.dto.FastBookingDTO;
import hotel.vti_hotel.repository.FastBookingRepository;
import hotel.vti_hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private void populate(FastBooking fastBooking, FastBookingRequest request) {
        fastBooking.setFullName(request.getFullName());
        fastBooking.setPhoneNumber(request.getPhoneNumber());
        fastBooking.setRoom(roomRepository.findById(request.getRoomId()).orElseThrow(() -> new NullPointerException("Room not found")));
        fastBooking.setType(convertToEnum(Booking.TypeBooking.class, request.getTypeBooking()));

        LocalDateTime checkIn = buildLocalDateTime(request.getCheckInDate(), request.getCheckInTime());
        LocalDateTime checkOut;

        switch (convertToEnum(Booking.TypeBooking.class, request.getTypeBooking())) {
            case HOURLY:
                if (request.getCheckOutTime() == null || request.getCheckOutTime().isBlank()) {
                    throw new IllegalArgumentException("HOURLY booking requires a valid checkout time.");
                }
                checkOut = buildLocalDateTime(request.getCheckInDate(), request.getCheckOutTime());
                if (checkOut.isBefore(checkIn) || Duration.between(checkIn, checkOut).toHours() > 8) {
                    throw new IllegalArgumentException("HOURLY booking must not exceed 8 hours and checkout must be after check-in.");
                }
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
                break;

            case NIGHTLY:
                checkIn = checkIn.withHour(22).withMinute(0).withSecond(0).withNano(0);
                checkOut = checkIn.plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0);
                break;

            default:
                throw new IllegalArgumentException("Invalid booking type.");
        }

        fastBooking.setCheckIn(checkIn);
        fastBooking.setCheckOut(checkOut);
    }
}
