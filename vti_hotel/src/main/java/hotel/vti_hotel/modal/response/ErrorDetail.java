package hotel.vti_hotel.modal.response;

import lombok.Data;

@Data
public class ErrorDetail {
    private int code;
    private String message;
    private String detail;
    public ErrorDetail(int code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
}
