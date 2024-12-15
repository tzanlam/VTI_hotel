package hotel.vti_hotel.modal.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FastBookingRequest {
    private String fullName;
    private String phoneNumber;
    private int roomId;

    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
            message = "Check-in must be in the format HH:mm:ss")
    private String checkIn;

    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
            message = "Check-out must be in the format HH:mm:ss")
    private String checkOut;

    private String typeBooking;
}