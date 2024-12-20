package hotel.vti_hotel.service.account;

import hotel.vti_hotel.modal.request.AccountRequest;
import hotel.vti_hotel.modal.response.dto.AccountDTO;

import java.util.List;

public interface IAccountService {
    // find
    List<AccountDTO> findAccounts();
    AccountDTO findAccountById(int id);
    AccountDTO findAccountByEmail(String email);

    // create
    AccountDTO createAccount(AccountRequest request);
    AccountDTO registerAccount(AccountRequest request);

    // update
    AccountDTO updateAccount(int id, AccountRequest request);
}
