package hotel.vti_hotel.service.fastbooking;

import hotel.vti_hotel.modal.entity.Booking;
import hotel.vti_hotel.modal.entity.FastBooking;
import hotel.vti_hotel.modal.entity.Room;
import hotel.vti_hotel.modal.request.FastBookingRequest;
import hotel.vti_hotel.modal.response.dto.FastBookingDTO;
import hotel.vti_hotel.repository.FastBookingRepository;
import hotel.vti_hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
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
        FastBooking newFastBooking = populate(fastBooking, request);
        fastBookingRepository.save(newFastBooking);
        return new FastBookingDTO(newFastBooking);
    }

    @Override
    public FastBookingDTO changeStatusFastBook(int id, String status) {
        return null;
    }

    private FastBooking populate(FastBooking fastBooking, FastBookingRequest request) {
        fastBooking.setFullName(request.getFullName());
        fastBooking.setPhoneNumber(request.getPhoneNumber());

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NullPointerException("Not found with Room id: " + request.getRoomId()));
        fastBooking.setRoom(room);

        Booking.TypeBooking typeBooking = convertToEnum(Booking.TypeBooking.class, request.getTypeBooking());
        fastBooking.setType(typeBooking);
        return fastBooking;
    }
}
