package hotel.vti_hotel.modal.request;

import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.validation.ValidUniqueEmail.UniqueEmail;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static hotel.vti_hotel.support.ConvertString.convertToEnum;
import static hotel.vti_hotel.support.ConvertString.convertToLocalDate;

@UniqueEmail
@Data
public class AccountRequest {
    private String fullName;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String imageCard;
    private String gender;
    private String level;
    private String amountSpending;
    private String cumulativePoint;
    private String role;
    private String status;

    public Account register(){
        Account account = new Account();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setFullName(fullName);
        account.setGender(null);
        account.setImageCard(null);
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);
        account.setBirthDate(null);
        account.setLevel(Account.Level.NEW_CUSTOMER);
        account.setAmountSpent(0);
        account.setCumulativePoints(0);
        account.setStatus(Account.StatusAccount.PENDING);
        account.setRole(Account.Role.USER);
        return account;
    }

    public Account createAccountByAdmin() throws Exception {
        Account account = new Account();
        account.setUsername(username);
        account.setLevel(convertToEnum(Account.Level.class, level));
        account.setAmountSpent(Double.parseDouble(amountSpending));
        account.setCumulativePoints(Integer.parseInt(cumulativePoint));
        account.setRole(convertToEnum(Account.Role.class, role));
        account.setStatus(convertToEnum(Account.StatusAccount.class, status));
        populateAccount(account);
        return account;
    }

    public void updateAccount(Account account) throws Exception {
        account.setFullName(fullName);
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);
        account.setBirthDate(convertToLocalDate(birthDate));
        account.setImageCard(imageCard);
        account.setGender(convertToEnum(Account.Gender.class, gender));
    }
    private void populateAccount(Account account) throws Exception {
        account.setFullName(fullName);
        account.setPassword(password);
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);
        account.setBirthDate(convertToLocalDate(birthDate));
        account.setImageCard(imageCard);
        account.setGender(convertToEnum(Account.Gender.class, gender));
    }
}
