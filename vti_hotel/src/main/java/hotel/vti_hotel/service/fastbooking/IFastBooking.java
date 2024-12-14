package hotel.vti_hotel.service.fastbooking;

import hotel.vti_hotel.modal.response.dto.FastBookingDTO;

import java.util.List;

public interface IFastBooking {
    // find
    List<FastBookingDTO> getFastBookings();
    FastBookingDTO getFastBookingById(int id);
    List<FastBookingDTO> getFastBookingByName(String name);

}
