package hotel.vti_hotel.service.booking;

import hotel.vti_hotel.modal.request.BookingRequest;
import hotel.vti_hotel.modal.response.dto.BookingDTO;

import java.util.List;

public interface IBookingService {
    // find
    List<BookingDTO> readBookings();
    BookingDTO readBooking(int bookingId);
    List<BookingDTO> readBookingsByName(String name);

    // create
    BookingDTO createBooking(BookingRequest request) throws Exception;

    // update
    BookingDTO updateBooking(int id,BookingRequest request) throws Exception;
}
