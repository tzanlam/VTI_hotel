package hotel.vti_hotel.modal.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FastBookingRequest {
    private String fullName;
    private String phoneNumber;
    private int roomId;
    private String checkIn;
    private String checkOut;
    private String typeBooking;
}