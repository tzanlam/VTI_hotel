package hotel.vti_hotel.modal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(callSuper=true)
@Data
@Table(name = "booking")
public class Booking extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @Column(name = "type_booking")
    @Enumerated(EnumType.STRING)
    private TypeBooking typeBooking;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusBooking statusBooking;

    public enum TypeBooking{
        HOURLY, NIGHTLY, DAILY
    }

    public enum StatusBooking{
        SUCCESS, CANCELED, PENDING
    }
}
