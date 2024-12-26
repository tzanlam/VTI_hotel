package hotel.vti_hotel.service.global;

import hotel.vti_hotel.modal.request.LoginRequest;
import hotel.vti_hotel.modal.response.AuthResponse;
import hotel.vti_hotel.modal.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IGlobalService {
    // login
    AuthResponse login(LoginRequest request);

    // upload
    UploadResponse upload(MultipartFile path, String folder) throws IOException;
}
