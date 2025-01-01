package hotel.vti_hotel.support;

import hotel.vti_hotel.modal.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Calculate {
    public static double calculatePriceHourly(Room room, float time){
        return room.getPriceFirstHour() + time*room.getPriceNextHour();
    }

    public static double calculatePriceDay(Room room, int day){
        return room.getPriceDay()*day;
    }

    public static double calculatePriceNight(Room room){
        return room.getPriceNight();
    }

    public static String calculateFileHash(MultipartFile file) throws IOException , NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(file.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
