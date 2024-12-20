package hotel.vti_hotel.validation.ValidHourly;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = HourlyBookingValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HourlyBooking {
    String message() default "Hourly booking must not exceed 8 hours and check-out must be after check-in.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
