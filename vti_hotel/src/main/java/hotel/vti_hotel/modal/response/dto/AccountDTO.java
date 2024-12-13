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

    public AccountDTO(Account account) {
        this.accountId = String.valueOf(account.getId());
        this.fullName = account.getFullName();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.phoneNumber = account.getPhoneNumber();
        this.birthDate = String.valueOf(account.getBirthDate());
        this.imageCard = account.getImageCard();
        this.gender = account.getGender().toString();
        this.accountLevel = account.getLevel();
        this.amountSpent = String.valueOf(account.getAmountSpent());
        this.cumulativePoint = String.valueOf(account.getCumulativePoints());
        this.accountStatus = String.valueOf(account.getStatus());
    }
}
