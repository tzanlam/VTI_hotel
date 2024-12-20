package hotel.vti_hotel.modal.request;

import lombok.Data;

@Data
public class BookingRequest {
    private int accountId;
    private int roomId;
    private String typeBooking;
    private String checkInDate;
    private String checkOutDate;
    private String checkInTime;
    private String checkOutTime;
    private int voucherId;
}
