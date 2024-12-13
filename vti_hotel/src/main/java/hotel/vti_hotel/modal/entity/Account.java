package hotel.vti_hotel.modal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "account")
@EqualsAndHashCode(callSuper = true)
public class Account extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "image_card")
    private String imageCard;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "level")
    private String level;

    @Column(name = "amount_spent")
    private double amountSpent;

    @Column(name = "cumulative_points")
    private double cumulativePoints;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAccount status;

    public enum Role {
        ADMIN, USER
    }

    public enum StatusAccount{
        ACTIVE, INACTIVE, BLOCKED, PENDING
    }

    public enum Gender{
        MALE, FEMALE
    }
}
