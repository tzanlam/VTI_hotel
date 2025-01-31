package hotel.vti_hotel.repository;

import hotel.vti_hotel.modal.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.username = ?1 OR a.email = ?1")
    Optional<Account> findByIdentifier(String identifier);

    @Query("SELECT COUNT(a) > 0 FROM Account a WHERE a.email = :email AND a.status = :status")
    boolean existsByEmail(String email, Account.StatusAccount status);
}
