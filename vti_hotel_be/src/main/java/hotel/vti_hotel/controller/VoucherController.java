package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.VoucherRequest;
import hotel.vti_hotel.service.voucher.IVoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/hotel")
public class VoucherController {
    private final IVoucherService voucherService;

    public VoucherController(IVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/findVouchers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> findVouchers() {
        try{
            return ResponseEntity.ok(voucherService.getVouchers());
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findVoucherById")
    public ResponseEntity<?> findVoucherById(@RequestParam("voucherId") int id) {
        try{
            return ResponseEntity.ok(voucherService.getVoucherById(id));
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findVoucherByPoint")
    public ResponseEntity<?> findVoucherByPoint(@RequestParam("voucherPoint") int point) {
        try{
            return ResponseEntity.ok(voucherService.getByPoint(point));
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findVoucherActive")
    public ResponseEntity<?> findVoucherActive() {
        try{
            return ResponseEntity.ok(voucherService.getVoucherActive());
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createVoucher")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createVoucher(@RequestBody VoucherRequest request){
        try{
            return ResponseEntity.ok(voucherService.createNewVoucher(request));
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateVoucher")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateVoucher(@RequestParam("voucherId") int id, @RequestBody VoucherRequest request){
        try{
            return ResponseEntity.ok(voucherService.updateVoucher(id, request));
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/changeStatusVoucher")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> changeStatusVoucher(@RequestParam("voucherId") int id) {
        try{
            return ResponseEntity.ok(voucherService.changeStatusVoucher(id));
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
