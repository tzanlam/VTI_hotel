package hotel.vti_hotel.repository;

import hotel.vti_hotel.modal.entity.Booking;
import hotel.vti_hotel.modal.response.dto.BookingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByAccount_Username(String name);
}
