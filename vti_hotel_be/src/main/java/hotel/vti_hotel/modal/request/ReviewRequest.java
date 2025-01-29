package hotel.vti_hotel.modal.request;

import hotel.vti_hotel.validation.ValidOneFieldRequired.OneFieldRequired;
import lombok.Data;

@Data
@OneFieldRequired
public class ReviewRequest {
    private int accountId;
    private int roomId;
    private int bookingId;
    private int fastBookingId;
    private int rating;
    private String comment;
}