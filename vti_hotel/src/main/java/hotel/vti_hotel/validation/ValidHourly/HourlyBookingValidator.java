package hotel.vti_hotel.validation.ValidHourly;

import hotel.vti_hotel.modal.request.FastBookingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;
import java.time.LocalDateTime;

import static hotel.vti_hotel.support.ConvertString.buildLocalDateTime;

public class HourlyBookingValidator implements ConstraintValidator<HourlyBooking, FastBookingRequest> {
    @Override
    public boolean isValid(FastBookingRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if (!"HOURLY".equalsIgnoreCase(request.getTypeBooking())) {
            return true;
        }

        try {
            LocalDateTime checkIn = buildLocalDateTime(request.getCheckInDate(), request.getCheckInTime());
            LocalDateTime checkOut = buildLocalDateTime(request.getCheckOutDate(), request.getCheckOutTime());

            return checkOut.isAfter(checkIn) && Duration.between(checkIn, checkOut).toHours() <= 8;
        } catch (Exception e) {
            return false;
        }
    }
}
