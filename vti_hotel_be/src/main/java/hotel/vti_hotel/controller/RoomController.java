package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.RoomRequest;
import hotel.vti_hotel.service.room.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
@CrossOrigin("*")
public class RoomController {
    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/findRooms")
    public ResponseEntity<?> getRooms() {
        try {
            return new ResponseEntity<>(roomService.findRooms(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findRoomById")
    public ResponseEntity<?> getRoomById(@RequestParam("roomId") int id) {
        try{
            return ResponseEntity.ok(roomService.findId(id));
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createRooms")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createRooms(@RequestBody RoomRequest request){
        try{
            return ResponseEntity.ok(roomService.createRoom(request));
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateRoom")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateRoom(@RequestParam("roomId") int id, @RequestBody RoomRequest request){
        try{
            return ResponseEntity.ok(roomService.updateRoom(id, request));
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/changeRoomQuantity")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> changeRoomQuantity(@RequestParam("roomId") int id, @RequestParam RoomRequest request){
        try{
            return ResponseEntity.ok(roomService.changeQuantity(id, request));
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/changeRoomPrice")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> changeRoomPrice(@RequestParam("roomId") int id, @RequestParam RoomRequest request){
        try{
            return ResponseEntity.ok(roomService.changePrice(id, request));
        }catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
