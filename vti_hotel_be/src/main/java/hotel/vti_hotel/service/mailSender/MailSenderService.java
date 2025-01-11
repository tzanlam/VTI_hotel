package hotel.vti_hotel.service.mailSender;

import hotel.vti_hotel.modal.request.MailSenderRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService implements IMailSender {
    @Value("${spring.mail.username}")
    private String emailAdmin;
    private final JavaMailSender javaMailSender;

    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void mailSendCodeConfirm(MailSenderRequest request, String codeConfirm) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(request.getTo());
        helper.setTo(emailAdmin);
        helper.setSubject(codeConfirm);
    }

    @Override
    public String mailSendForgotPassword(MailSenderRequest request) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(request.getTo());
        helper.setTo(emailAdmin);
        helper.setSubject("");
        return "Mail đã được gửi thành công";
    }

    @Override
    public String mailInformationBooking(int bookingId, MailSenderRequest request) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailAdmin);
        helper.setTo(request.getTo());
        helper.setSubject(request.getSubject());
        helper.setText(request.getBody(), true);
        javaMailSender.send(message);
        return "Mail đã được gửi thành công";
    }

    @Override
    public String mailCanceledBooking(int bookingId, MailSenderRequest request) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(request.getTo());
        helper.setTo("tzanlam@gmail.com");
        helper.setSubject("");
        return "Mail đã được gửi thành công";
    }

    @Override
    public String mailInformationFastBooking(int fastBookingId, MailSenderRequest request) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(request.getTo());
        helper.setTo("tzanlam@gmail.com");
        helper.setSubject("");
        return "Mail đã được gửi thành công";
    }

    @Override
    public String mailCanceledFastBooking(int fastBookingId, MailSenderRequest request) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(request.getTo());
        helper.setTo("tzanlam@gmail.com");
        helper.setSubject("");
        return "Mail đã được gửi thành công";
    }

    @Override
    public String mailUserComplain(String email, String subject, String body) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(email);
            helper.setSubject(subject);
            helper.setText("<html><body>" + body + "</body></html>", true);
            helper.setTo(emailAdmin);
            javaMailSender.send(message);
            return "Đã gửi mail thành công";
        }catch (Exception e){
            throw new MessagingException("Gửi mail thất bại");
        }
    }
}