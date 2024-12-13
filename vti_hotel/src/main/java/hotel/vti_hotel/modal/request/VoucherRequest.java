package hotel.vti_hotel.modal.request;

import hotel.vti_hotel.modal.entity.Voucher;
import lombok.Data;

import static hotel.vti_hotel.support.ConvertString.convertToEnum;
import static hotel.vti_hotel.support.ConvertString.convertToLocalDate;

@Data
public class VoucherRequest {
    private String name;
    private String discount;
    private String expiry;
    private int quantity;
    private String status;

    public Voucher createVoucher() {
        Voucher voucher = new Voucher();
        populate(voucher);
        voucher.setStatus(Voucher.StatusVoucher.INACTIVE);
        return voucher;
    }

    public Voucher updateVoucher(Voucher voucher) {
        populate(voucher);
        return voucher;
    }

    private void populate(Voucher voucher) {
        voucher.setName(name);
        voucher.setDiscount(Double.parseDouble(discount));
        voucher.setExpiry(convertToLocalDate(expiry));
        voucher.setQuantity(quantity);
        voucher.setStatus(convertToEnum(Voucher.StatusVoucher.class,status));
    }
}
