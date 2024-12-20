package hotel.vti_hotel.service.account;

import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.modal.request.AccountRequest;
import hotel.vti_hotel.modal.response.dto.AccountDTO;
import hotel.vti_hotel.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

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
    public AccountDTO createAccount(AccountRequest request) {
        Account account = request.createAccountByAdmin();
        accountRepository.save(account);
        return new AccountDTO(account);
    }

    @Override
    public AccountDTO registerAccount(AccountRequest request) {
        Account account = request.register();
        accountRepository.save(account);
        return new AccountDTO(account);
    }

    @Override
    public AccountDTO updateAccount(int id, AccountRequest request) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new NullPointerException("Account not found"));
        if (Objects.nonNull(account)) {
            request.updateAccount(account);
            accountRepository.save(account);
            return new AccountDTO(account);
        }
        return null;
    }
}
