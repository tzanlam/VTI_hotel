package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.ReviewRequest;
import hotel.vti_hotel.service.review.IReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class ReviewController {
    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/findReviews")
    public ResponseEntity<?> findReviews() {
        try{
            return ResponseEntity.ok(reviewService.findReviews());
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findReviewById")
    public ResponseEntity<?> findReviewById(@RequestParam("reviewId") int id) {
        try{
            return ResponseEntity.ok(reviewService.findReviewById(id));
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createReview")
    public ResponseEntity<?> createReview(
            @Valid
            @RequestBody ReviewRequest reviewRequest) {
        try{
            return ResponseEntity.ok(reviewService.createReview(reviewRequest));
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateReview")
    public ResponseEntity<?> updateReview(@RequestParam("reviewId") int id, @RequestBody ReviewRequest reviewRequest) {
        try{
            return ResponseEntity.ok(reviewService.updateReview(id, reviewRequest));
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findReviewByRoom")
    public ResponseEntity<?> findReviewByRoom(@RequestParam("roomId") int roomId) {
        try{
            return ResponseEntity.ok(reviewService.findReviewsByRoomId(roomId));
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
