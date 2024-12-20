package hotel.vti_hotel.repository;

import hotel.vti_hotel.modal.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    @Query(value = "SELECT * FROM voucher WHERE point = :point", nativeQuery = true)
    List<Voucher> findByPoint(@Param("point") int point);
}
