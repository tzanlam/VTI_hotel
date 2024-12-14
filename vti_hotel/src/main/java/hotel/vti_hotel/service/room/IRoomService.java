package hotel.vti_hotel.service.room;

import hotel.vti_hotel.modal.request.RoomRequest;
import hotel.vti_hotel.modal.response.dto.RoomDTO;

import java.util.List;

public interface IRoomService {
    // find
    List<RoomDTO> findRooms();
    RoomDTO findId(int id);

    // create
    RoomDTO createRoom(RoomRequest request);

    // update
    RoomDTO updateRoom(int id, RoomRequest request);

    // change quantity room
    RoomDTO changeQuantity(int id, RoomRequest request);

    // change all price in room
    RoomDTO changePrice(int id, RoomRequest request);
}
