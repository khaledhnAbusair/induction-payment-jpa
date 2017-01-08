package repositories.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Currency;
import entity.PaymentPurpose;
import entity.PaymentRequest;
import repositories.PaymentRequestGateway;
import repositories.exceptions.AccountHasNoPaymentRequestsDueThisDayException;
import repositories.exceptions.AccountHasNoPaymentRequestsException;
import repositories.exceptions.NoneExistingPaymentRequestException;
import repositories.exceptions.NullPaymentRequestDateException;
import repositories.exceptions.PaymentPurposeNotFoundException;
import repositories.exceptions.PaymentRequestIsAlreadyExistException;
import repositories.loader.EntityManagerLoader;
import static repositories.sqlconstants.SqlConstants.*;

public class PaymentRequestJpaRepository implements PaymentRequestGateway {

	private static final String PAYMENT_DATE = "paymentDate";
	private static final String ORD_IBAN = "ordIban";
	private EntityManager entityManager;

	public PaymentRequestJpaRepository() {
		entityManager = EntityManagerLoader.getEntityManger();
	}

	public PaymentRequestJpaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void deletePaymentRequestById(int id) {
		PaymentRequest findByIdForDelete = entityManager.find(PaymentRequest.class, id);
		isNullPaymentRequest(findByIdForDelete);
		entityManager.getTransaction().begin();
		entityManager.remove(findByIdForDelete);
		entityManager.getTransaction().commit();
	}

	@Override
	public PaymentRequest loadPaymentRequestById(int id) {
		PaymentRequest findByIdForLoad = entityManager.find(PaymentRequest.class, id);
		isNullPaymentRequest(findByIdForLoad);
		return findByIdForLoad;

	}

	@Override
	public void insertPaymentRequest(PaymentRequest paymentRequest) {

		entityManager.getTransaction().begin();
		PaymentRequest request = populateNewPaymentRequest(paymentRequest);
		PaymentRequest find = entityManager.find(PaymentRequest.class, request.getId());
		if (Objects.nonNull(find)) {
			entityManager.getTransaction().rollback();
			throw new PaymentRequestIsAlreadyExistException();
		}
		entityManager.persist(paymentRequest);
		entityManager.getTransaction().commit();
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequests() {
		TypedQuery<PaymentRequest> typedQuery = entityManager.createNamedQuery("PaymentRequest.findAll",
				PaymentRequest.class);
		if (Objects.isNull(typedQuery))
			throw new PaymentPurposeNotFoundException();
		return typedQuery.getResultList();
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequestsByOrderingAccountIBAN(String iban) {

		if (Objects.isNull(iban))
			throw new PaymentPurposeNotFoundException();
		TypedQuery<PaymentRequest> paymentRequestsForIban = entityManager
				.createQuery(SELECT_PAYMENT_REQUEST_FOR_ORDARING_IBAN, PaymentRequest.class);
		paymentRequestsForIban.setParameter(ORD_IBAN, iban);
		if (Objects.isNull(paymentRequestsForIban))
			throw new AccountHasNoPaymentRequestsException();
		return paymentRequestsForIban.getResultList();
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequestByPaymentDate(Date paymentRequestDate) {
		if (Objects.isNull(paymentRequestDate))
			throw new NullPaymentRequestDateException();
		TypedQuery<PaymentRequest> paymentRequestsForDate = entityManager
				.createQuery(SELECT_PAYMENT_REQUEST_FOR_PAYMENT_DATE, PaymentRequest.class);
		paymentRequestsForDate.setParameter(PAYMENT_DATE, paymentRequestDate);
		if (Objects.isNull(paymentRequestsForDate))
			throw new AccountHasNoPaymentRequestsDueThisDayException();
		return paymentRequestsForDate.getResultList();
	}

	private PaymentRequest populateNewPaymentRequest(PaymentRequest paymentRequest) {
		PaymentPurpose purposeCode = entityManager.find(PaymentPurpose.class,
				paymentRequest.getPaymentPurpose().getCode());

		Currency currencyCode = entityManager.find(Currency.class, paymentRequest.getCurrencyCode());

		PaymentRequest request = new PaymentRequest();
		request.setAmount(paymentRequest.getAmount());
		request.setAmountInWords(paymentRequest.getAmountInWords());
		request.setBenefIban(paymentRequest.getBenefIban());
		request.setBenefName(paymentRequest.getBenefName());
		request.setCurrencyCode(currencyCode.getCode());
		request.setOrdIban(paymentRequest.getOrdIban());
		request.setPaymentDate(paymentRequest.getPaymentDate());
		request.setPaymentPurpose(purposeCode);
		return request;
	}

	private void isNullPaymentRequest(PaymentRequest findById) {
		if (Objects.isNull(findById)) {
			throw new NoneExistingPaymentRequestException();
		}
	}

}
