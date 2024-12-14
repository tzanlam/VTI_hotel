package hotel.vti_hotel.service.voucher;

import hotel.vti_hotel.modal.entity.Voucher;
import hotel.vti_hotel.modal.request.VoucherRequest;
import hotel.vti_hotel.modal.response.dto.VoucherDTO;
import hotel.vti_hotel.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VoucherService implements IVoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Override
    public List<VoucherDTO> getVouchers() {
        return voucherRepository.findAll().stream()
                .map(VoucherDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public VoucherDTO getVoucherById(int id) {
        return new VoucherDTO(voucherRepository.findById(id).orElseThrow(()-> new NullPointerException("Voucher not found")));
    }

    @Override
    public List<VoucherDTO> getByPoint(int point) {
        return voucherRepository.findByPoint(point).stream()
                .map(VoucherDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public VoucherDTO createNewVoucher(VoucherRequest request) {
        Voucher voucher = request.createVoucher();
        voucherRepository.save(voucher);
        return new VoucherDTO(voucher);
    }

    @Override
    public VoucherDTO updateVoucher(int id, VoucherRequest request) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(()-> new NullPointerException("Voucher not found"));
        if (Objects.nonNull(voucher)){
            request.updateVoucher(voucher);
            voucherRepository.save(voucher);
            return new VoucherDTO(voucher);
        }
        return null;
    }

    @Override
    public VoucherDTO changeStatusVoucher(int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(()-> new NullPointerException("Voucher not found"));
        if (Objects.nonNull(voucher)){
            switch (voucher.getStatus()){
                case ACTIVE -> voucher.setStatus(Voucher.StatusVoucher.INACTIVE);
                case INACTIVE -> voucher.setStatus(Voucher.StatusVoucher.ACTIVE);
            }
            voucherRepository.save(voucher);
            return new VoucherDTO(voucher);
        }
        return null;
    }
}
