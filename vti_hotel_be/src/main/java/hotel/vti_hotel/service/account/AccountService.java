package hotel.vti_hotel.service.account;

import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.modal.request.AccountRequest;
import hotel.vti_hotel.modal.request.MailSenderRequest;
import hotel.vti_hotel.modal.response.dto.AccountDTO;
import hotel.vti_hotel.repository.AccountRepository;
import hotel.vti_hotel.service.mailSender.IMailSender;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static hotel.vti_hotel.support.Random.randomString;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AccountDTO> findAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO findAccountById(int id) {
        return new AccountDTO(accountRepository.findById(id).orElseThrow(()-> new NullPointerException("Account not found")));
    }

    @Override
    public AccountDTO findAccountByEmail(String email) {
        return new AccountDTO(accountRepository.findByEmail(email));
    }

    @Override
    public AccountDTO createAccount(AccountRequest request) throws Exception {
        Account account = request.createAccountByAdmin();
        accountRepository.save(account);
        return new AccountDTO(account);
    }

    @Override
    public AccountDTO registerAccount(AccountRequest request) throws MessagingException {
        Account account = request.register();
        String confirmCode = randomString(5);
        account.setConfirmCode(confirmCode);
        accountRepository.save(account);
        MailSenderRequest mailSenderRequest = new MailSenderRequest();
        mailSenderRequest.setTo(account.getEmail());
        mailSenderRequest.setSubject("Cảm ơn bạn vì đã đăng kí tài khoản bên khách sạn chúng tôi.");
        mailSenderRequest.setBody("Mã xác nhận email của bạn: " + confirmCode);
        mailSender.mailSendCodeConfirm(mailSenderRequest, confirmCode);
        return new AccountDTO(account);
    }

    @Override
    public AccountDTO confirmAccount(String email, String password, String confirmationCode) {
            Account account = accountRepository.findByEmail(email);
            if (account == null) {
                throw new IllegalArgumentException("Email không tồn tại");
            }
            if (!passwordEncoder.matches(password, account.getPassword())) {
                throw new IllegalArgumentException("Mật khẩu không đúng");
            }
            if (!Objects.equals(confirmationCode, account.getConfirmCode())) {
                throw new IllegalArgumentException("Mã xác nhận không đúng");
            }
            account.setConfirmCode(null);
            account.setStatus(Account.StatusAccount.ACTIVE);
            accountRepository.save(account);
            return new AccountDTO(account);
        }

    @Override
    public AccountDTO updateAccount(int id, AccountRequest request) throws Exception {
        Account account = accountRepository.findById(id).orElseThrow(()-> new NullPointerException("Account not found"));
        if (Objects.nonNull(account)) {
            request.updateAccount(account);
            accountRepository.save(account);
            return new AccountDTO(account);
        }
        return null;
    }
}
