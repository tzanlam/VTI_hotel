package hotel.vti_hotel.controller;

import hotel.vti_hotel.service.fastBooking.IFastBooking;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class FastBookingController {
    private final IFastBooking fastBooking;

    public FastBookingController(IFastBooking fastBooking) {
        this.fastBooking = fastBooking;
    }


}
