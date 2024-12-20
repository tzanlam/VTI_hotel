package hotel.vti_hotel.service.customUserDetails;

import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.repository.AccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetails implements UserDetailsService {
    private final AccountRepository accountRepository;

    public CustomUserDetails(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (Objects.nonNull(account)) {
            return new User(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole().name()));
        }
        throw new UsernameNotFoundException(username);
    }
}
