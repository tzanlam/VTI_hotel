package hotel.vti_hotel.modal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "payment")
public class Payment extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "fast_booking_id", referencedColumnName = "id")
    private FastBooking fastBooking;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "price")
    private double price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    @Column(name = "vnp_response_code")
    private String vnpResponseCode;

    @Column(name = "vnp_transaction_status")
    private String vnpTransactionStatus;

    @Column(name = "payment_url")
    private String paymentUrl;

    public enum StatusPayment{
        SUCCESS, FAILED, PENDING
    }
}
