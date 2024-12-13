package hotel.vti_hotel.modal.response.dto;

import hotel.vti_hotel.modal.entity.Room;
import lombok.Data;

@Data
public class RoomDTO {
    private String roomId;
    private String roomName;
    private String imageRoom;
    private String description;
    private String quantity;
    private String priceDay;
    private String priceNight;

    public RoomDTO(Room room) {
        this.roomId = String.valueOf(room.getId());
        this.roomName = room.getName();
        this.imageRoom = room.getImageRoom();
        this.description = room.getDescription();
        this.quantity = String.valueOf(room.getQuantity());
        this.priceDay = String.valueOf(room.getPriceDay());
        this.priceNight = String.valueOf(room.getPriceNight());
    }
}
