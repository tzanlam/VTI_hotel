package hotel.vti_hotel.modal.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String identifier;
    private String password;
}