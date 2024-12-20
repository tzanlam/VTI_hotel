package hotel.vti_hotel.service.room;

import hotel.vti_hotel.modal.entity.Room;
import hotel.vti_hotel.modal.request.RoomRequest;
import hotel.vti_hotel.modal.response.dto.RoomDTO;
import hotel.vti_hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<RoomDTO> findRooms() {
        return roomRepository.findAll().stream()
                .map(RoomDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO findId(int id) {
        return new RoomDTO(roomRepository.findById(id).orElseThrow(()-> new NullPointerException("Room not found")));
    }

    @Override
    public RoomDTO createRoom(RoomRequest request) {
        Room room = request.createRoom();
        roomRepository.save(room);
        return new RoomDTO(room);
    }

    @Override
    public RoomDTO updateRoom(int id, RoomRequest request) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new NullPointerException("Room not found"));
        if (Objects.nonNull(room)){
            request.updateRoom(room);
            roomRepository.save(room);
            return new RoomDTO(room);
        }
        return null;
    }

    @Override
    public RoomDTO changeQuantity(int id, RoomRequest request) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new NullPointerException("Room not found"));
        if (Objects.nonNull(room)){
            request.changeQuantity(room);
            roomRepository.save(room);
            return new RoomDTO(room);
        }
        return null;
    }

    @Override
    public RoomDTO changePrice(int id, RoomRequest request) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new NullPointerException("Room not found"));
        if (Objects.nonNull(room)){
            request.changePrice(room);
            roomRepository.save(room);
            return new RoomDTO(room);
        }
        return null;
    }
}
