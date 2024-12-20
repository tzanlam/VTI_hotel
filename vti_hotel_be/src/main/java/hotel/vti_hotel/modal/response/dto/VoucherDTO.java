package hotel.vti_hotel.modal.response.dto;

import hotel.vti_hotel.modal.entity.Voucher;
import lombok.Data;

@Data
public class VoucherDTO {
    private String voucherId;
    private String voucherName;
    private String discount;
    private String expiry;
    private String quantity;
    private String voucherStatus;

    public VoucherDTO(Voucher voucher) {
        this.voucherId = String.valueOf(voucher.getId());
        this.voucherName = voucher.getName();
        this.discount = String.valueOf(voucher.getDiscount());
        this.expiry = String.valueOf(voucher.getExpiry());
        this.quantity = String.valueOf(voucher.getQuantity());
        this.voucherStatus = String.valueOf(voucher.getStatus());
    }
}
