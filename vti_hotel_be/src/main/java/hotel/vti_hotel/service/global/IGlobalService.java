package hotel.vti_hotel.service.global;

import hotel.vti_hotel.modal.request.LoginRequest;
import hotel.vti_hotel.modal.request.UploadRequest;
import hotel.vti_hotel.modal.response.AuthResponse;
import hotel.vti_hotel.modal.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface IGlobalService {
    // login
    AuthResponse login(LoginRequest request);

    // upload
    UploadResponse upload(UploadRequest request) throws IOException, NoSuchAlgorithmException;
}
