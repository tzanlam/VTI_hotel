package hotel.vti_hotel.modal.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private int accountId;
    private int bookingId;
    private int fastBookingId;
}
