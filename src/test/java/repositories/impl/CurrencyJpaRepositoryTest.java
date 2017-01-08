package repositories.impl;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entity.Currency;
import repositories.exceptions.InvalidCurrencyCodeExpection;

public class CurrencyJpaRepositoryTest {

	private EntityManager entityManager;
	private final String CURRENCYCODE = "JOD";
	private final String WRONGCURRENCYCODE = "BSF";
	private final String PERSISTENCE_UNIT_NAME = "payment-system-repositories";

	private EntityManagerFactory entityManagerFactory;
	private CurrencyJpaRepository currencyJpaRepository;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, prepareDBProperties());
		entityManager = entityManagerFactory.createEntityManager();
		currencyJpaRepository = new CurrencyJpaRepository(entityManager);
	}

	@Test
	public void givenCurrencyJpaRepository_WhenCallingloadCurrencies_willReturnListOfCurrencies() {
		Collection<Currency> loadedCurrencies = currencyJpaRepository.loadCurrencies();
		assertTrue(loadedCurrencies.iterator().hasNext());
	}

	@Test
	public void givenCurrencyJpaRepository_WhenCallingloadCurrencyByCode_ThenPassingCurrencyCode_itWillReturnCurrency() {
		Currency loadCurrencyByCode = currencyJpaRepository.loadCurrencyByCode(CURRENCYCODE);
		Assert.assertEquals(loadCurrencyByCode.getCode(), CURRENCYCODE);
	}

	@Test(expected = InvalidCurrencyCodeExpection.class)
	public void givenCurrencyJpaRepository_WhenCallingloadCurrencyByCode_ThenPassingWrongCurrencyCode_itWillThrowInvalidCurrencyCodeExpection() {
		currencyJpaRepository.loadCurrencyByCode(WRONGCURRENCYCODE);
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
