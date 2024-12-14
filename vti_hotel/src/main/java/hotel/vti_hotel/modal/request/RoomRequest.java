package hotel.vti_hotel.modal.request;

import hotel.vti_hotel.modal.entity.Room;
import lombok.Data;

@Data
public class RoomRequest {
    private String name;
    private String imageRoom;
    private String description;
    private int quantity;
    private String priceDay;
    private String priceNight;
    private String priceFirstHour;

    public Room createRoom() {
        Room room = new Room();
        populate(room);
        return room;
    }

    public Room updateRoom(Room room) {
        populate(room);
        return room;
    }

    public Room changeQuantity(Room room) {
        room.setQuantity(quantity);
        return room;
    }

    public Room changePrice(Room room){
        room.setPriceDay(Double.parseDouble(priceDay));
        room.setPriceNight(Double.parseDouble(priceNight));
        room.setPriceFirstHour(Double.parseDouble(priceFirstHour));
        return room;
    }

    private void populate(Room room) {
        room.setName(name);
        room.setImageRoom(imageRoom);
        room.setDescription(description);
        room.setQuantity(quantity);
        room.setPriceDay(Double.parseDouble(priceDay));
        room.setPriceNight(Double.parseDouble(priceNight));
        room.setPriceFirstHour(Double.parseDouble(priceFirstHour));
    }
}
