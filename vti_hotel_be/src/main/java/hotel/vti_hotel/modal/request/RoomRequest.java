package hotel.vti_hotel.modal.request;

import hotel.vti_hotel.modal.entity.Room;
import lombok.Data;

@Data
public class RoomRequest {
    private String roomName;
    private String imageRoom;
    private String description;
    private int quantity;
    private String priceDay;
    private String priceNight;
    private String priceFirstHour;
    private String priceNextHour;

    public Room createRoom() {
        Room room = new Room();
        populate(room);
        return room;
    }

    public void updateRoom(Room room) {
        populate(room);
    }

    public void changeQuantity(Room room) {
        room.setQuantity(quantity);
    }

    public void changePrice(Room room){
        room.setPriceDay(Double.parseDouble(priceDay));
        room.setPriceNight(Double.parseDouble(priceNight));
        room.setPriceFirstHour(Double.parseDouble(priceFirstHour));
    }

    private void populate(Room room) {
        room.setName(roomName);
        room.setImageRoom(imageRoom);
        room.setDescription(description);
        room.setQuantity(quantity);
        room.setPriceDay(Double.parseDouble(priceDay));
        room.setPriceNight(Double.parseDouble(priceNight));
        room.setPriceFirstHour(Double.parseDouble(priceFirstHour));
        room.setPriceNextHour(Double.parseDouble(priceNextHour));
    }
}
