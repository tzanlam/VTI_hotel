package hotel.vti_hotel.modal.response.dto;

import lombok.Data;
import hotel.vti_hotel.modal.entity.Payment;

@Data
public class PaymentDTO {
    private String paymentId;
    private String account;
    private String booking;
    private String fastBooking;
    private String transactionId;
    private String paymentPrice;
    private String paymentStatus;

    public PaymentDTO(Payment payment) {
        this.paymentId = String.valueOf(payment.getId());
        this.account = String.valueOf(new AccountDTO(payment.getAccount()));
        this.booking = String.valueOf(new BookingDTO(payment.getBooking()));
        this.fastBooking = String.valueOf(new FastBookingDTO(payment.getFastBooking()));
        this.transactionId = String.valueOf(payment.getTransactionId());
        this.paymentPrice = String.valueOf(payment.getPrice());
        this.paymentStatus = String.valueOf(payment.getStatus());
    }
}