package hotel.vti_hotel.modal.response;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetail {
    private Date time;
    private String message;
    private String detail;
    public ErrorDetail(Date time, String message, String detail) {
        this.time = time;
        this.message = message;
        this.detail = detail;
    }
}
