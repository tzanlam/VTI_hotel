package hotel.vti_hotel.modal.response.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private String paymentId;
    private String account;
    private String booking;
    private String fastBooking;
    private String transactionId;
    private String paymentPrice;
    private String paymentStatus;
}