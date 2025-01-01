package hotel.vti_hotel.service.voucher;

import hotel.vti_hotel.modal.request.VoucherRequest;
import hotel.vti_hotel.modal.response.dto.VoucherDTO;

import java.util.List;

public interface IVoucherService {
    // find
    List<VoucherDTO> getVouchers();
    VoucherDTO getVoucherById(int id);
    List<VoucherDTO> getByPoint(int point);
    List<VoucherDTO> getVoucherActive();

    // create
    VoucherDTO createNewVoucher(VoucherRequest request) throws Exception;

    // update
    VoucherDTO updateVoucher(int id, VoucherRequest request) throws Exception;

    // change status
    VoucherDTO changeStatusVoucher(int id);
}