package hotel.vti_hotel.modal.request;

import lombok.Data;

@Data
public class FastBookingRequest {
    private String fullName;
    private String phoneNumber;
    private int roomId;
    private String checkIn;
    private String checkOut;
}
