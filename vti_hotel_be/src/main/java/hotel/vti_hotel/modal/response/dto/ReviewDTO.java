package hotel.vti_hotel.modal.response.dto;

import hotel.vti_hotel.modal.entity.Review;
import lombok.Data;

@Data
public class ReviewDTO {
    private String reviewId;
    private String fullName;
    private String room;
    private String booking;
    private String fastBooking;
    private String rating;
    private String comment;

    public ReviewDTO(Review review) {
        this.reviewId = String.valueOf(review.getId());
        this.fullName = review.getFullName();
        this.room = String.valueOf(new RoomDTO(review.getRoom()));
        this.booking = String.valueOf(new BookingDTO(review.getBooking()));
        this.fastBooking = String.valueOf(new FastBookingDTO(review.getFastBooking()));
        this.rating = String.valueOf(review.getRating());
        this.comment = review.getComment();
    }
}
