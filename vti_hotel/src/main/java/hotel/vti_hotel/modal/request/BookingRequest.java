package hotel.vti_hotel.modal.request;

import lombok.Data;

@Data
public class BookingRequest {
    private int accountId;
    private int roomId;
    private String typeBooking;
    private String checkIn;
    private String checkOut;
    private int voucherId;
}
