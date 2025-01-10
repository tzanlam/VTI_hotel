package hotel.vti_hotel.modal.response.dto;

import hotel.vti_hotel.modal.entity.FastBooking;
import lombok.Data;

@Data
public class FastBookingDTO {
    private String fastBookingId;
    private String fullName;
    private String phoneNumber;
    private String room;
    private String typeBooking;
    private String checkin;
    private String checkout;
    private String totalPrice;
    private String fastBookingStatus;

    public FastBookingDTO(FastBooking fastBook) {
        this.fastBookingId = String.valueOf(fastBook.getId());
        this.fullName = fastBook.getFullName();
        this.phoneNumber = fastBook.getPhoneNumber();
        this.room = new RoomDTO(fastBook.getRoom()).getRoomName();
        this.typeBooking = fastBook.getType().toString();
        this.checkin = fastBook.getCheckIn().toString();
        this.checkout = fastBook.getCheckOut().toString();
        this.totalPrice = String.valueOf(fastBook.getTotalPrice());
        this.fastBookingStatus = fastBook.getStatus().toString();
    }
}
