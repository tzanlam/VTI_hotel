package hotel.vti_hotel.modal.request;

import hotel.vti_hotel.validation.ValidHourly.HourlyBooking;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@HourlyBooking
public class FastBookingRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Room ID is required")
    private Integer roomId;

    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
            message = "Check-in time must be in the format HH:mm:ss")
    private String checkInTime;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$",
            message = "Check-in date must be in the format dd/MM/yyyy")
    private String checkInDate;

    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
            message = "Check-out time must be in the format HH:mm:ss")
    private String checkOutTime;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$",
            message = "Check-out date must be in the format dd/MM/yyyy")
    private String checkOutDate;

    @NotBlank(message = "Type booking is required")
    private String typeBooking;
}