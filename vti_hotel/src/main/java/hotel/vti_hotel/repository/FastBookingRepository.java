package hotel.vti_hotel.repository;

import hotel.vti_hotel.modal.entity.FastBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FastBookingRepository extends JpaRepository<FastBooking, Integer> {
}
