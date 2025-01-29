package hotel.vti_hotel.validation.ValidOneFieldRequired;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OneFieldRequiredValidation.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneFieldRequired {
    String message() default "Chỉ được một giá trị tồn tại";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
