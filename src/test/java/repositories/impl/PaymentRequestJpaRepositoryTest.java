package repositories.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entity.Currency;
import entity.PaymentPurpose;
import entity.PaymentRequest;
import repositories.exceptions.NoneExistingPaymentRequestException;

public class PaymentRequestJpaRepositoryTest {

	private final String PERSISTENCE_UNIT_NAME = "payment-system-repositories";
	private PaymentRequestJpaRepository paymentRequestJpaRepository;

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, prepareDBProperties());
		entityManager = entityManagerFactory.createEntityManager();
		paymentRequestJpaRepository = new PaymentRequestJpaRepository(entityManager);

	}

	@After
	public void afterTest() {
		entityManager.getTransaction().begin();
		entityManager.merge(payReq());
		entityManager.getTransaction().commit();
	}

	@Test
	public void givenPaymentRequestRepository_CallingLoadPaymentRequestById_PassingExistingID_ShouldReturnThePaymentRequest() {
		Assert.assertEquals(2, paymentRequestJpaRepository.loadPaymentRequestById(2).getId());
	}

	@Test(expected = NoneExistingPaymentRequestException.class)
	public void givenPaymentRequestRepostiory_CallingLoadPaymentRequestById_PassingNoneExistingID_ShouldThrowNoneExistingPaymentRequest() {
		paymentRequestJpaRepository.loadPaymentRequestById(77).getId();
	}

	@Test(expected = NoneExistingPaymentRequestException.class)
	public void givenPaymentRequestRepositorey_CallingDeletePaymentRequestById_PassingNonExistingPaymentRequest_ShouldThrowNoneExistingPaymentRequst() {
		paymentRequestJpaRepository.deletePaymentRequestById(0);
	}

	@Test
	public void givenPaymentRequestRepositorey_CallingDeletePaymentRequestById_PassingExistingPaymentRequest_ShouldDeletePaymentRequest() {
		paymentRequestJpaRepository.deletePaymentRequestById(0);
	}

	@Test
	public void givenPaymentRequestRepositorey_CallingInsertPaymentRequest_ThenCallingLoadPaymentRequestById_ShouldReturnThePaymentRequest() {
		PaymentPurpose paymentPurpose = populatePaymentPurpose();
		Currency currency = populateCurrency();

		PaymentRequest request = new PaymentRequest();
		request.setAmount(new BigDecimal(2454.147));
		request.setAmountInWords("Samer Million");
		request.setBenefIban("DK5000400440116243");
		request.setBenefName("Mhraaaaaaaan");
		request.setCurrencyCode(currency.getCode());
		request.setOrdIban("EE382200221020145685");
		request.setPaymentDate(new Date(2017, 07, 5));
		request.setPaymentPurpose(paymentPurpose);
		paymentRequestJpaRepository.insertPaymentRequest(request);
	}

	@Test
	public void givenPaymentRequestRepositorey_callingloadPaymentRequests_sholdReturnCollectionOfPaymentRequests() {
		Collection<PaymentRequest> paymentRequests = paymentRequestJpaRepository.loadPaymentRequests();
		Assert.assertNotNull(paymentRequests);
	}

	private PaymentRequest payReq() {
		PaymentPurpose paymentPurpose = populatePaymentPurpose();
		Currency currency = populateCurrency();

		PaymentRequest request = new PaymentRequest();
		request.setId(2);
		request.setAmount(new BigDecimal(154.147));
		request.setAmountInWords("One Million");
		request.setBenefIban("DK5000400440116243");
		request.setBenefName("Abusair");
		request.setCurrencyCode(currency.getCode());
		request.setOrdIban("EE382200221020145685");
		request.setPaymentDate(new Date(2017, 05, 16));
		request.setPaymentPurpose(paymentPurpose);
		return request;
	}

	private Currency populateCurrency() {
		Currency currency = new Currency();
		currency.setCode("JOD");
		currency.setName("Jordan");
		return currency;
	}

	private PaymentPurpose populatePaymentPurpose() {
		PaymentPurpose paymentPurpose = new PaymentPurpose();
		paymentPurpose.setCode("SALA");
		paymentPurpose.setName("Java");
		return paymentPurpose;
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
