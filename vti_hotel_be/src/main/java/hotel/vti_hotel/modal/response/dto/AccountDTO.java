package hotel.vti_hotel.modal.response.dto;

import hotel.vti_hotel.modal.entity.Account;
import lombok.Data;

@Data
public class AccountDTO {
    private String accountId;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String imageCard;
    private String gender;
    private String accountLevel;
    private String amountSpent;
    private String cumulativePoint;
    private String accountStatus;
    private String role;

    public AccountDTO(Account account) {
        this.accountId = String.valueOf(account.getId());
        this.fullName = account.getFullName() != null ? account.getFullName() : null;
        this.username = account.getUsername() != null ? account.getUsername() : null;
        this.email = account.getEmail() != null ? account.getEmail() : null;
        this.phoneNumber = account.getPhoneNumber() != null ? account.getPhoneNumber() : null;
        this.birthDate = account.getBirthDate() != null ? String.valueOf(account.getBirthDate()) : null;
        this.imageCard = account.getImageCard() != null ? account.getImageCard() : null;
        this.gender = account.getGender() != null ? account.getGender().toString() : null;
        this.accountLevel = account.getLevel() != null ? String.valueOf(account.getLevel()) : null;
        this.amountSpent = String.valueOf(account.getAmountSpent());
        this.cumulativePoint = String.valueOf(account.getCumulativePoints());
        this.accountStatus = account.getStatus() != null ? String.valueOf(account.getStatus()) : null;
        this.role = account.getRole() != null ? String.valueOf(account.getRole()) : null;
    }

}
