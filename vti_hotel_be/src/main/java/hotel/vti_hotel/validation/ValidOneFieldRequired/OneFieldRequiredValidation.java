package hotel.vti_hotel.validation.ValidOneFieldRequired;

import hotel.vti_hotel.modal.request.ReviewRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneFieldRequiredValidation implements ConstraintValidator<OneFieldRequired, ReviewRequest> {
    @Override
    public boolean isValid(ReviewRequest request, ConstraintValidatorContext constraintValidatorContext) {
        Integer bookingId = request.getBookingId();
        Integer fastBookingId = request.getFastBookingId();

        boolean hasBookingId = bookingId != null && bookingId != 0;
        boolean hasFastBookingId = fastBookingId != null && fastBookingId != 0;

        return hasBookingId ^ hasFastBookingId;
    }
}
