package hotel.vti_hotel.modal.response.dto;

import hotel.vti_hotel.modal.entity.Booking;
import lombok.Data;

@Data
public class BookingDTO {
    private String bookingId;
    private String account;
    private String room;
    private String typeBooking;
    private String checkIn;
    private String checkOut;
    private String voucher;
    private String totalPrice;
    private String bookingStatus;

    public BookingDTO(Booking booking) {
        this.bookingId = String.valueOf(booking.getId());
        this.account = new AccountDTO(booking.getAccount()).getFullName();
        this.room = new RoomDTO(booking.getRoom()).getRoomName();
        this.typeBooking = booking.getTypeBooking().toString();
        this.checkIn = String.valueOf(booking.getCheckIn());
        this.checkOut = String.valueOf(booking.getCheckOut());
        this.voucher = (booking.getVoucher() != null) ? String.valueOf(new VoucherDTO(booking.getVoucher())) : null;
        this.totalPrice = String.valueOf(booking.getTotalPrice());
        this.bookingStatus = booking.getStatus().toString();
    }

}
