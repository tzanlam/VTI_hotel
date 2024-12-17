package hotel.vti_hotel.service.review;

import hotel.vti_hotel.modal.entity.Booking;
import hotel.vti_hotel.modal.entity.FastBooking;
import hotel.vti_hotel.modal.entity.Review;
import hotel.vti_hotel.modal.request.ReviewRequest;
import hotel.vti_hotel.modal.response.dto.ReviewDTO;
import hotel.vti_hotel.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final FastBookingRepository fastBookingRepository;
    private final BookingRepository bookingRepository;

    public ReviewService(ReviewRepository reviewRepository, FastBookingRepository fastBookingRepository, BookingRepository bookingRepository, AccountRepository accountRepository) {
        this.reviewRepository = reviewRepository;
        this.fastBookingRepository = fastBookingRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<ReviewDTO> findReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO findReviewById(int id) {
        return new ReviewDTO(reviewRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Review not found")
        ));
    }

    @Override
    public ReviewDTO createReview(ReviewRequest request) {
        Review review = new Review();
        populate(request, review);
        reviewRepository.save(review);
        return new ReviewDTO(review);
    }

    @Override
    public ReviewDTO updateReview(int id, ReviewRequest request) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Review not found")
        );
        populate(request, review);
        return new ReviewDTO(review);
    }

    private void populate (ReviewRequest request, Review review){
        if (request.getBookingId() != 0 && request.getFastBookingId() != 0){
            throw new IllegalArgumentException("Only one of booking or fast booking id can be set");
        }

        if (request.getBookingId() != 0){
            Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(
                    () -> new IllegalArgumentException("Booking not found")
            );
            review.setBooking(booking);
            review.setRoom(booking.getRoom());
            review.setFullName(booking.getAccount().getFullName());
            review.setRating(request.getRating());
            review.setComment(request.getComment());
            review.setFastBooking(null);
        }
        else if (request.getFastBookingId() != 0){
            FastBooking fastBooking = fastBookingRepository.findById(request.getFastBookingId()).orElseThrow(
                    () -> new IllegalArgumentException("Fast booking not found")
            );
            review.setRoom(fastBooking.getRoom());
            review.setFastBooking(fastBooking);
            review.setBooking(null);
            review.setFullName(fastBooking.getFullName());
            review.setRating(request.getRating());
            review.setComment(request.getComment());
        }
    }
}
