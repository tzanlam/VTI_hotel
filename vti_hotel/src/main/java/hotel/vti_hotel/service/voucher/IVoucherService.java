package hotel.vti_hotel.service.voucher;

import hotel.vti_hotel.modal.request.VoucherRequest;
import hotel.vti_hotel.modal.response.dto.VoucherDTO;

import java.util.List;

public interface IVoucherService {
    // find
    List<VoucherDTO> getVouchers();
    VoucherDTO getVoucherById(int id);
    List<VoucherDTO> getByPoint(int point);

    // create
    VoucherDTO createNewVoucher(VoucherRequest request);

    // update
    VoucherDTO updateVoucher(int id, VoucherRequest request);

    // change status
    VoucherDTO changeStatusVoucher(int id);
}