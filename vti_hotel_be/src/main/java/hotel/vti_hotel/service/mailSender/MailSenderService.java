package hotel.vti_hotel.service.mailSender;

import hotel.vti_hotel.modal.request.MailSenderRequest;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService implements IMailSender{
    private final MailSender mailSender;

    public MailSenderService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String mailSendCodeConfirm(MailSenderRequest request) {
        return "";
    }

    @Override
    public String mailSendForgotPassword(MailSenderRequest request) {
        return "";
    }

    @Override
    public String mailInformationBooking(int bookingId, MailSenderRequest request) {
        return "";
    }

    @Override
    public String mailCanceledBooking(int bookingId, MailSenderRequest request) {
        return "";
    }

    @Override
    public String mailInformationFastBooking(int fastBookingId, MailSenderRequest request) {
        return "";
    }

    @Override
    public String mailCanceledFastBooking(int fastBookingId, MailSenderRequest request) {
        return "";
    }
}
