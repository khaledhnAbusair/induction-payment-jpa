package repositories.impl;

import static repositories.sqlconstants.SqlConstants.UPDATE_PAYMENT_PURPOSE;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.PaymentPurpose;
import repositories.PaymentPurposeGateway;
import repositories.exceptions.EmptyPaymentPurposeCodeException;
import repositories.exceptions.NoPaymentPurposeHassBeenUpdated;
import repositories.exceptions.NoneExistingPaymentPurposeException;
import repositories.exceptions.NullPaymentPurposeCodeException;
import repositories.exceptions.PaymentPurposeIsAlreadyExistException;
import repositories.exceptions.PaymentPurposeNotFoundException;
import repositories.loader.EntityManagerLoader;

public class PaymentPurposeJpaRepository implements PaymentPurposeGateway {

	private static final String PAYMENT_PURPOSE_CODE = "code";
	private static final String PAYMENT_PURPOSE_NAME = "name";
	private static final String EMPTY_QUTES = "";
	private EntityManager entityManager;

	public PaymentPurposeJpaRepository() {
		entityManager = EntityManagerLoader.getEntityManger();
	}

	public PaymentPurposeJpaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public PaymentPurpose loadPaymentPurposeByCode(String code) {
		isEmptyPurposeCode(code);
		isNullPurposeCode(code);
		PaymentPurpose purposeCode = entityManager.find(PaymentPurpose.class, code);
		isNullPaymentPurpose(purposeCode);
		return purposeCode;
	}

	@Override
	public void insertPaymentPurpose(PaymentPurpose paymentPurpose) {
		PaymentPurpose purpose = populateNewPaymentPurpose(paymentPurpose);
		entityManager.getTransaction().begin();
		PaymentPurpose find = entityManager.find(PaymentPurpose.class, purpose.getCode());
		if (Objects.nonNull(find)) {
			entityManager.getTransaction().rollback();
			throw new PaymentPurposeIsAlreadyExistException();
		}
		entityManager.persist(paymentPurpose);
		entityManager.getTransaction().commit();

	}

	@Override
	public Collection<PaymentPurpose> loadPaymentPurposes() {
		TypedQuery<PaymentPurpose> listOfPaymentPurpose = entityManager.createNamedQuery("PaymentPurpose.findAll",
				PaymentPurpose.class);
		if (Objects.isNull(listOfPaymentPurpose))
			throw new PaymentPurposeNotFoundException();
		return listOfPaymentPurpose.getResultList();
	}

	@Override
	public void deletePaymentPurposeByCode(String code) {
		isEmptyPaymentCode(code);
		isNullPaymentCode(code);
		PaymentPurpose paymentPurposeForDelete = entityManager.find(PaymentPurpose.class, code);
		isNullPaymentPurposeForDelete(paymentPurposeForDelete);
		entityManager.getTransaction().begin();
		entityManager.remove(paymentPurposeForDelete);
		entityManager.getTransaction().commit();

	}

	@Override
	public void updatePaymentPurposeName(PaymentPurpose paymentPurpose) {

		Query query = entityManager.createQuery(UPDATE_PAYMENT_PURPOSE);
		query.setParameter(PAYMENT_PURPOSE_NAME, paymentPurpose.getName());
		query.setParameter(PAYMENT_PURPOSE_CODE, paymentPurpose.getCode());
		try {
			entityManager.getTransaction().begin();
			query.executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new NoPaymentPurposeHassBeenUpdated();
		}

	}

	private void isNullPaymentPurpose(PaymentPurpose purposeCode) {
		if (Objects.isNull(purposeCode)) {
			throw new PaymentPurposeNotFoundException();
		}
	}

	private void isNullPurposeCode(String code) {
		if (Objects.isNull(code))
			throw new NullPaymentPurposeCodeException();
	}

	private void isEmptyPurposeCode(String code) {
		isEmptyPaymentCode(code);
	}

	private void isNullPaymentPurposeForDelete(PaymentPurpose paymentPurposeForDelete) {
		if (Objects.isNull(paymentPurposeForDelete))
			throw new NoneExistingPaymentPurposeException();
	}

	private void isNullPaymentCode(String code) {
		if (Objects.isNull(code) || "".equals(code))
			throw new NullPaymentPurposeCodeException();
	}

	private void isEmptyPaymentCode(String code) {
		if (EMPTY_QUTES.equals(code))
			throw new EmptyPaymentPurposeCodeException();
	}

	private PaymentPurpose populateNewPaymentPurpose(PaymentPurpose paymentPurpose) {
		PaymentPurpose purpose = new PaymentPurpose();
		purpose.setCode(paymentPurpose.getCode());
		purpose.setName(paymentPurpose.getName());
		return purpose;
	}
}
