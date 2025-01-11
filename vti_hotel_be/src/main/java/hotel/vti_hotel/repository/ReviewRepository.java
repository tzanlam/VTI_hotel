package hotel.vti_hotel.repository;

import hotel.vti_hotel.modal.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByRoomId(int roomId);
}
