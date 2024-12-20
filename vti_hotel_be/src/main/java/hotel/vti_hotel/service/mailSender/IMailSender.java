package hotel.vti_hotel.service.mailSender;

import hotel.vti_hotel.modal.request.MailSenderRequest;

public interface IMailSender {
    String mailSendCodeConfirm(MailSenderRequest request);
    String mailSendForgotPassword(MailSenderRequest request);
    String mailInformationBooking(int bookingId, MailSenderRequest request);
    String mailCanceledBooking(int bookingId, MailSenderRequest request);
    String mailInformationFastBooking(int fastBookingId, MailSenderRequest request);
    String mailCanceledFastBooking(int fastBookingId, MailSenderRequest request);
}
