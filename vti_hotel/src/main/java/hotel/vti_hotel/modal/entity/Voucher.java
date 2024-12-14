package hotel.vti_hotel.modal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "voucher")
public class Voucher extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "discount")
    private double discount;

    @Column(name = "expiry")
    private LocalDate expiry;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "point")
    private int point;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusVoucher status;

    public enum StatusVoucher{
        ACTIVE, INACTIVE
    }
}
