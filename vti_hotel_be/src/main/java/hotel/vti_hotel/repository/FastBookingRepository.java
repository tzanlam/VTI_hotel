package hotel.vti_hotel.repository;

import hotel.vti_hotel.modal.entity.FastBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FastBookingRepository extends JpaRepository<FastBooking, Integer> {
    @Query(value = "SELECT * from fast_booking  where full_name = :fullName", nativeQuery = true)
    List<FastBooking> findByFullName(@Param("fullName") String fullName);

    @Query(value = "select * from fast_booking where phone_number = :phoneNumber", nativeQuery = true)
    List<FastBooking> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
