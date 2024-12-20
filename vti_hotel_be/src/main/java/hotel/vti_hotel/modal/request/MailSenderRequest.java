package hotel.vti_hotel.modal.request;

import lombok.Data;

@Data
public class MailSenderRequest {
    private String to;
    private String subject;
    private String body;
}
