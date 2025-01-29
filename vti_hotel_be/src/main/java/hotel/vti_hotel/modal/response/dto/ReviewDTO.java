package hotel.vti_hotel.modal.response.dto;

import hotel.vti_hotel.modal.entity.Review;
import lombok.Data;

import java.util.Objects;

@Data
public class ReviewDTO {
    private String reviewId;
    private String fullName;
    private String room;
    private String booking;
    private String fastBooking;
    private String code;
    private String rating;
    private String comment;

    public ReviewDTO(Review review) {
        this.reviewId = String.valueOf(review.getId());
        this.fullName = review.getFullName();
        this.room = String.valueOf(new RoomDTO(review.getRoom()));
                if(Objects.nonNull(review.getBooking())){
                    this.code = String.valueOf(review.getBooking().getId());
                }
                else { this.code = String.valueOf(review.getFastBooking().getId());}
        this.rating = String.valueOf(review.getRating());
        this.comment = review.getComment();
    }
}
