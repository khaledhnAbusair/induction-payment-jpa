package repositories;

import java.util.Collection;
import java.util.Date;

import entity.PaymentRequest;

public interface PaymentRequestGateway {

	void deletePaymentRequestById(int id);

	PaymentRequest loadPaymentRequestById(int id);

	void insertPaymentRequest(PaymentRequest paymentRequest);

	Collection<PaymentRequest> loadPaymentRequests();

	Collection<PaymentRequest> loadPaymentRequestByPaymentDate(Date paymentRequestDate);

	Collection<PaymentRequest> loadPaymentRequestsByOrderingAccountIBAN(String iban);

}