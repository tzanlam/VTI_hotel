package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.BookingRequest;
import hotel.vti_hotel.service.booking.IBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class BookingController {
    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/findBookings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> findBookings() {
        try{
            return ResponseEntity.ok(bookingService.readBookings());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findBookingById")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> findBookingId(@RequestParam("bookingId") int id) {
        try{
            return ResponseEntity.ok(bookingService.readBooking(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/findBookingsByName")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> findBookingsByName(@RequestParam("username") String name) {
        try{
            return ResponseEntity.ok(bookingService.readBookingsByName(name));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/createBooking")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingDetails) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(bookingDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateBooking")
    public ResponseEntity<?> updateBooking(@RequestParam("bookingId") int id, @RequestBody BookingRequest bookingDetails) {
        try {
            return ResponseEntity.ok(bookingService.updateBooking(id, bookingDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
