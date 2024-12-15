package hotel.vti_hotel.support;

import hotel.vti_hotel.modal.entity.Room;

public class Calculate {
    public static double calculatePriceHourly(Room room, float time){
        return room.getPriceFirstHour() + time*room.getPriceNextHour();
    }

    public static double calculatePriceDay(Room room, int day){
        return room.getPriceDay();
    }

    public static double calculatePriceNight(Room room){
        return room.getPriceNight();
    }
}
