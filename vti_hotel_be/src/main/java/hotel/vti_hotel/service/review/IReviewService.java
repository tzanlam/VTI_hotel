package hotel.vti_hotel.service.review;

import hotel.vti_hotel.modal.request.ReviewRequest;
import hotel.vti_hotel.modal.response.dto.ReviewDTO;

import java.util.List;

public interface IReviewService {
    // find
    List<ReviewDTO> findReviews();
    ReviewDTO findReviewById(int id);

    // create
    ReviewDTO createReview(ReviewRequest request);

    // update
    ReviewDTO updateReview(int id, ReviewRequest request);
}
