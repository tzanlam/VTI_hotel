package hotel.vti_hotel.modal.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private int accountId;
    private int roomId;
    private int bookingId;
    private int fastBookingId;
    private int rating;
    private String comment;
}