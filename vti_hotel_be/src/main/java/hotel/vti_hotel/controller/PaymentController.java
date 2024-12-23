package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.entity.Payment;
import hotel.vti_hotel.modal.request.PaymentRequest;
import hotel.vti_hotel.modal.response.dto.PaymentDTO;
import hotel.vti_hotel.service.payment.IPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class PaymentController {
    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/findPayments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> findPayments() {
        try {
            return ResponseEntity.ok(paymentService.getPayments());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/findPaymentById")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> findPaymentById(@RequestParam("paymentId") int id) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/findPaymentByTransactionId")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> findPaymentByTransactionId(@RequestParam("transactionId") String transactionId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentByTransactionId(transactionId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/createPayment")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentDetails) {
        try {
            return ResponseEntity.ok(paymentService.createPayment(paymentDetails));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/vnpay-return")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> vnpParams) {
        try {
            // Gọi service để xử lý phản hồi từ VNPay
            PaymentDTO paymentDTO = paymentService.handleVNPayResponse(vnpParams);

            if (Objects.equals(paymentDTO.getPaymentStatus(), Payment.StatusPayment.SUCCESS.toString())) {
                return ResponseEntity.ok("Payment successful for transaction ID: " + paymentDTO.getTransactionId());
            } else {
                return ResponseEntity.status(400).body("Payment failed for transaction ID: " + paymentDTO.getTransactionId());
            }
        } catch (SecurityException e) {
            return ResponseEntity.status(400).body("Invalid VNPay signature: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing VNPay response: " + e.getMessage());
        }
    }
}