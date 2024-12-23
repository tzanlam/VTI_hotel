package hotel.vti_hotel.service.payment;

import hotel.vti_hotel.modal.request.PaymentRequest;
import hotel.vti_hotel.modal.response.dto.PaymentDTO;

import java.util.List;
import java.util.Map;

public interface IPaymentService {
    // find
    List<PaymentDTO> getPayments();
    PaymentDTO getPaymentById(int id);
    PaymentDTO getPaymentByTransactionId(String transactionId) throws Exception;

    // create
    PaymentDTO createPayment(PaymentRequest request);
    PaymentDTO handleVNPayResponse(Map<String, String> vnParams);
}
