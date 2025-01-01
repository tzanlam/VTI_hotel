package hotel.vti_hotel.repository;

import hotel.vti_hotel.modal.entity.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageUploadRepository extends JpaRepository<ImageUpload, Integer> {
    ImageUpload findByHash(String hash);
}
