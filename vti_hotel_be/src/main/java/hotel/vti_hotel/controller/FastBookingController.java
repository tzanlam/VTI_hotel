package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.FastBookingRequest;
import hotel.vti_hotel.service.fastBooking.IFastBooking;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class FastBookingController {
    private final IFastBooking fastBooking;

    public FastBookingController(IFastBooking fastBooking) {
        this.fastBooking = fastBooking;
    }

    @GetMapping("/getFastBookings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getFastBookings() {
        try{
            return ResponseEntity.ok(fastBooking.getFastBookings());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/getFastBookingById")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getFastBookingById(@RequestParam("fastBookingId") int id) {
        try{
            return ResponseEntity.ok(fastBooking.getFastBookingById(id));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/getFastBookingByName")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getFastBookingByName(@Param("fastBookingFullName") String name) {
        try{
            return ResponseEntity.ok(fastBooking.getFastBookingByName(name));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/getFastBookingByPhoneNumber")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getFastBookingByPhoneNumber(@RequestParam("fastBookingPhoneNumber") String phoneNumber) {
        try{
            return ResponseEntity.ok(fastBooking.getByPhoneNumber(phoneNumber));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e.getMessage());
        }
    }

    @PostMapping("/createFastBooking")
    public ResponseEntity<?> createFastBooking(@RequestBody FastBookingRequest fastBookingDetails) {
        try{
            return ResponseEntity.ok(fastBooking.createFastBook(fastBookingDetails));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e.getMessage());
        }
    }

    @PostMapping("/changeStatusFastBooking")
    public ResponseEntity<?> changeStatusFastBooking(@RequestParam("fastBookingId") int id, @RequestParam("status") String status) {
        try{
            return ResponseEntity.ok(fastBooking.changeStatusFastBook(id, status));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: "+e.getMessage());
        }
    }


}
