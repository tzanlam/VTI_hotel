package hotel.vti_hotel.service.account;

import hotel.vti_hotel.modal.request.AccountRequest;
import hotel.vti_hotel.modal.response.dto.AccountDTO;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface IAccountService {
    // find
    List<AccountDTO> findAccounts();
    AccountDTO findAccountById(int id);
    AccountDTO findAccountByEmail(String email);

    // create
    AccountDTO createAccount(AccountRequest request) throws Exception;
    AccountDTO registerAccount(AccountRequest request) throws MessagingException;
    AccountDTO confirmAccount(String email, String password, String confirmationCode);

    // update
    AccountDTO updateAccount(int id, AccountRequest request) throws Exception;
}
