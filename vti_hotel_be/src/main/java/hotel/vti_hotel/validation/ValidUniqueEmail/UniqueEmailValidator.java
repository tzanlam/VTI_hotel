package hotel.vti_hotel.validation.ValidUniqueEmail;

import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.modal.request.AccountRequest;
import hotel.vti_hotel.repository.AccountRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, AccountRequest> {
    private final AccountRepository accountRepository;

    public UniqueEmailValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean isValid(AccountRequest s, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(s.getEmail())) {
            return true;
        }

        if (accountRepository.existsByEmail(s.getEmail(), Account.StatusAccount.ACTIVE)){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid email address").addConstraintViolation();
            return false;
        }
        return true;
    }
}
