package repositories.impl;

import static repositories.sqlconstants.SqlConstants.SELECT_PAYMENT_REQUEST_FOR_ORDARING_IBAN;
import static repositories.sqlconstants.SqlConstants.SELECT_PAYMENT_REQUEST_FOR_PAYMENT_DATE;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.BeanUtils;

import entity.Currency;
import entity.PaymentPurpose;
import entity.PaymentRequest;
import repositories.PaymentRequestGateway;
import repositories.exceptions.NoneExistingPaymentRequestException;
import repositories.exceptions.PaymentRequestIsAlreadyExistException;
import repositories.exceptions.PopulateEntityException;
import repositories.loader.EntityManagerLoader;

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

		PaymentRequest request = populateNewPaymentRequest(paymentRequest);
		entityManager.getTransaction().begin();

		boolean commited = false;
		try {
			PaymentRequest find = entityManager.find(PaymentRequest.class, request.getId());
			if (!Objects.isNull(find))
				throw new PaymentRequestIsAlreadyExistException();
			entityManager.persist(request);
			entityManager.getTransaction().commit();
			commited = true;
		} finally {
			if (!commited)
				entityManager.getTransaction().rollback();
		}
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequests() {

		TypedQuery<PaymentRequest> typedQuery = entityManager.createNamedQuery("PaymentRequest.findAll",
				PaymentRequest.class);
		return typedQuery.getResultList();
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequestsByOrderingAccountIBAN(String iban) {

		TypedQuery<PaymentRequest> paymentRequestsForIban = entityManager
				.createQuery(SELECT_PAYMENT_REQUEST_FOR_ORDARING_IBAN, PaymentRequest.class);
		paymentRequestsForIban.setParameter(ORD_IBAN, iban);
		return paymentRequestsForIban.getResultList();
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequestByPaymentDate(Date paymentRequestDate) {

		TypedQuery<PaymentRequest> paymentRequestsForDate = entityManager
				.createQuery(SELECT_PAYMENT_REQUEST_FOR_PAYMENT_DATE, PaymentRequest.class);
		paymentRequestsForDate.setParameter(PAYMENT_DATE, paymentRequestDate);
		return paymentRequestsForDate.getResultList();
	}

	private PaymentRequest populateNewPaymentRequest(PaymentRequest paymentRequest) {
		PaymentPurpose purposeCode = entityManager.find(PaymentPurpose.class,
				paymentRequest.getPaymentPurpose().getCode());
		Currency currencyCode = entityManager.find(Currency.class, paymentRequest.getCurrencyCode());
		PaymentRequest request = new PaymentRequest();
		try {
			BeanUtils.copyProperties(request, paymentRequest);
			request.setCurrencyCode(currencyCode.getCode());
			request.setPaymentPurpose(purposeCode);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new PopulateEntityException(e);
		}
		return request;
	}

	private void isNullPaymentRequest(PaymentRequest findById) {
		if (Objects.isNull(findById)) {
			throw new NoneExistingPaymentRequestException();
		}
	}

	@Override
	public void updatePaymentRequest(PaymentRequest paymentRequest) {
     
	}

}
