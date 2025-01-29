package hotel.vti_hotel.service.mailSender;

import hotel.vti_hotel.modal.request.MailSenderRequest;
import jakarta.mail.MessagingException;

public interface IMailSender {
    void mailSendCodeConfirm(MailSenderRequest request, String codeConfirm) throws MessagingException;
    String mailSendForgotPassword(MailSenderRequest request) throws MessagingException;
    void mailInformationBooking(int bookingId, MailSenderRequest request) throws MessagingException;
    String mailCanceledBooking(int bookingId, MailSenderRequest request) throws MessagingException;
    String mailInformationFastBooking(int fastBookingId, MailSenderRequest request) throws MessagingException;
    String mailCanceledFastBooking(int fastBookingId, MailSenderRequest request) throws MessagingException;
    String mailUserComplain(String email, String subject, String body) throws MessagingException;
}
