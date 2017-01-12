package repositories.impl;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entity.PaymentPurpose;
import repositories.exceptions.EmptyPaymentPurposeCodeException;
import repositories.exceptions.NoneExistingPaymentPurposeException;
import repositories.exceptions.NullPaymentPurposeCodeException;
import repositories.exceptions.PaymentPurposeIsAlreadyExistException;
import repositories.exceptions.PaymentPurposeNotFoundException;

public class PaymentPurposeJpaRepositoryTest {

	private EntityManager entityManager; 
	private EntityManagerFactory entityManagerFactory;
	private PaymentPurposeJpaRepository paymentPurposeJpaRepository;
	private final String PERSISTENCE_UNIT_NAME = "payment-system-repositories";

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, prepareDBProperties());
		entityManager = entityManagerFactory.createEntityManager();
		paymentPurposeJpaRepository = new PaymentPurposeJpaRepository(entityManager);
	}

	@After
	public void clearData() {
		entityManager.getTransaction().begin();
		Query createQuery = entityManager.createQuery("DELETE FROM PaymentPurpose pay WHERE pay.code='TREA'");
		createQuery.executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Test(expected = EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingEmptyCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeJpaRepository.loadPaymentPurposeByCode("");
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeJpaRepository.loadPaymentPurposeByCode(null);
	}

	@Test
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingExistingCode_ShouldReturnPaymentPurpose() {
		assertEquals(paymentPurposeJpaRepository.loadPaymentPurposeByCode("GHAD").getCode(), "GHAD");
	}

	@Test(expected = PaymentPurposeNotFoundException.class)
	public void givenPaymentPurposeRepository_CallingLoadPaymentPurposeByCode_PassingNoneExistingCode_ShowThrowPaymentPurposeNotFoundException() {
		paymentPurposeJpaRepository.loadPaymentPurposeByCode("q");
	}

	@Test
	public void givenPaymentPurposRepository_CallingInsertPaymentPurpose_ShouldBeenInsertedPaymentPurpose() {
		PaymentPurpose paymentPurpose = new PaymentPurpose();
		paymentPurpose.setCode("TREA");
		paymentPurpose.setName("TreasuryPayment");
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurpose);
		Assert.assertEquals(paymentPurpose.getCode(), "TREA");
	}

	@Test(expected = PaymentPurposeIsAlreadyExistException.class)
	public void givenPaymentPurposeRepositorey_CallingInsertPaymentPurpose_PassingExistingPaymentPurpose_ShouThrowPaymentPurposeIsAlreadyExistException() {
		PaymentPurpose paymentPurpose = paymentPurposeJpaRepository.loadPaymentPurposeByCode("SALA");
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurpose);
	}

	@Test(expected = PaymentPurposeIsAlreadyExistException.class)
	public void givenPaymentPurposeRepositorey_CallingInsertPaymentPurpose_PassPaymentPurposWithExistingCode_ShouldThrowDuplicatePaymentPurposeCode() {
		PaymentPurpose paymentPurposeEntity = new PaymentPurpose();
		paymentPurposeEntity.setCode("SALA");
		paymentPurposeEntity.setName("GHADEER");
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurposeEntity);
	}

	@Test
	public void givenPaymentPurposeRepositorey_CallingLoadPaymentPurposes_ShouldRetunPaymentPurposes() {
		Collection<PaymentPurpose> paymentPurposes = paymentPurposeJpaRepository.loadPaymentPurposes();
		Assert.assertNotNull(paymentPurposes);
	}

	@Test
	public void givenPaymentPurposeRepositorey_CallingInsertPaymentPurpose_ThenCallingDeletePurpose_ShouldInsertThePurposeThenDeleteIt() {
		PaymentPurpose paymentPurpose = new PaymentPurpose();
		paymentPurpose.setCode("KHwA");
		paymentPurpose.setName("Abusair");
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurpose);
		paymentPurposeJpaRepository.deletePaymentPurposeByCode("KHwA");
	}

	@Test(expected = NoneExistingPaymentPurposeException.class)
	public void givenPaymentPurposeRepositorey_CallingDeletePaymentPurposeByCode_PassingNoneExistingCode_ShouldThrowNoneExistingPaymentPurpose() {
		paymentPurposeJpaRepository.deletePaymentPurposeByCode("CDFVD");
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeRepositorey_CallingDeletePaymentPurposeByCode_PassingNullCode_ShouldThrowNullPaymentPurposeCode() {
		paymentPurposeJpaRepository.deletePaymentPurposeByCode(null);
	}

	@Test(expected = EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeRepositorey_CallingDeletePaymentPurposeByCode_PassingEmptyCode_ShouldThrowEmptyPaymentPurposeCode() {
		paymentPurposeJpaRepository.deletePaymentPurposeByCode("");
	}

	@Test
	public void givenPaymentPurposeRepositorey_CallingUpdatePaymentPurpose_PassingExistingPurposeCodeWithNewPurposeName_ShouldUpdateName() {
		PaymentPurpose paymentPurpose = new PaymentPurpose();
		paymentPurpose.setCode("kha");
		paymentPurpose.setName("Hlaa 3meee");
		
		paymentPurposeJpaRepository.updatePaymentPurposeName(paymentPurpose);
	}

	private Map<String, String> prepareDBProperties() {
		Map<String, String> settingsMap = new HashMap<>();
		settingsMap.put("javax.persistence.jdbc.user", "root");
		settingsMap.put("javax.persistence.jdbc.password", "root");
		settingsMap.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mockdb");
		settingsMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
		return settingsMap;
	}

}
