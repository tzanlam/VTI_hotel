package hotel.vti_hotel.modal.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {
    private MultipartFile file;
    private String folder;
}
