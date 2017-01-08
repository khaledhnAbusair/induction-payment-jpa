package repositories;

import java.util.Collection;

import entity.PaymentPurpose;

public interface PaymentPurposeGateway {

	PaymentPurpose loadPaymentPurposeByCode(String code);

	void insertPaymentPurpose(PaymentPurpose paymentPurpose);

	Collection<PaymentPurpose> loadPaymentPurposes();

	void deletePaymentPurposeByCode(String code);

	void updatePaymentPurposeName(PaymentPurpose paymentPurpose);

}