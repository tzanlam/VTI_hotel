package hotel.vti_hotel.support;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConvertString {
    public static LocalDate convertToLocalDate(String dateString) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("định dạng thời gian không hợp lệ");
            return null;
        }
    }

    public static LocalTime convertToLocalTime(String timeString) {
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return LocalTime.parse(timeString, timeFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("định dạng thời gian không hợp lệ");
            return null;
        }
    }

    public static LocalDateTime convertToLocalDateTime(String dateTimeString) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("định dạng thời gian không hợp lệ");
            return null;
        }
    }

    public static <E extends Enum<E>> E convertToEnum(Class<E> enumClass, String value) {
        if (enumClass == null || value == null) {
            throw new IllegalArgumentException("Enum class and value must not be null");
        }
        try {
            return Enum.valueOf(enumClass, value.toUpperCase()); // Chuyển chuỗi sang viết hoa nếu cần
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Value '" + value + "' is not valid for enum class " + enumClass.getSimpleName());
        }
    }

    public static LocalDateTime buildLocalDateTime(String date, String time) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalDateTime.of(LocalDate.parse(date, dateFormatter), LocalTime.parse(time, timeFormatter));
    }
}
