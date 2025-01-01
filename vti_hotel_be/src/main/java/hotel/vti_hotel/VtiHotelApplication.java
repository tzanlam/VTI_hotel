package hotel.vti_hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {hotel.vti_hotel.VtiHotelApplication.class})
public class VtiHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(VtiHotelApplication.class, args);
	}

}
