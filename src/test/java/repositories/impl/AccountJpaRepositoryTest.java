
package repositories.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entity.Account;
import entity.Currency;
import repositories.exceptions.AccountIsAlreadyExistException;
import repositories.exceptions.AccountNotFoundExceptions;
import repositories.exceptions.NoAccountHasBeenUpdated;
import repositories.exceptions.NullAccountIBANException;

public class AccountJpaRepositoryTest {

	private final String UNAVAILABLE_IBAN = "s996SBa187";
	private final String NEW_ACCOUNT_IBAN = "RO49AAAA1B31007593840000577555";
	private final String PERSISTENCE_UNIT_NAME = "payment-system-repositories";
	private final String AVAILABLE_ACCOUNT_IBAN = "AZ21NABZ00000000137010001944";
	private final String DELETE_QUERY = "DELETE FROM Account acc where acc.iban='" + NEW_ACCOUNT_IBAN + "'";

	private AccountJpaRepository accountJpaRepository;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, prepareDBProperties());
		entityManager = entityManagerFactory.createEntityManager();
		accountJpaRepository = new AccountJpaRepository(entityManager);
	}

	@After
	public void clearResource() {
		entityManager.getTransaction().begin();
		entityManager.createQuery(DELETE_QUERY).executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenAccountRepositrey_CallingLoadAccountByIBAN_PassingNullIBANCode_ShouldThrowNullAccountIBAN() {
		accountJpaRepository.loadAccountByIBAN(null);
	}

	@Test(expected = NullAccountIBANException.class)
	public void givenAccountRepositrey_CallingLoadAccountByIBAN_PassingEmptyIBANCode_ShouldThrowNullAccountIBAN() {
		accountJpaRepository.loadAccountByIBAN("");
	}

	@Test(expected = AccountNotFoundExceptions.class)
	public void givenAccountRepositrey_CallingLoadAccountByIBAN_PassingUnavailableIBANCode_ShouldThrowIbanNotExistException() {
		accountJpaRepository.loadAccountByIBAN(UNAVAILABLE_IBAN);
	}

	@Test
	public void givenAccountJpaRepositry_CallingLoadAccountByIBAN_PassingAvailableIBANCode_ShouldReturnAccount() {
		accountJpaRepository.loadAccountByIBAN(AVAILABLE_ACCOUNT_IBAN);
	}

	@Test
	public void givenAccountJpaRepositry_CallingUpdateAcount_PassingExistingAccount_ThenCallingLoadAccountByIBAN_ShouldReturnUpdatedAccount() {
		Account account = accountJpaRepository.loadAccountByIBAN(AVAILABLE_ACCOUNT_IBAN);
		Currency currency = new Currency();
		currency.setCode("JOD");
		account.setCurrency(currency);
		accountJpaRepository.updateAccount(account);
		Assert.assertNotEquals("USD",
				accountJpaRepository.loadAccountByIBAN(AVAILABLE_ACCOUNT_IBAN).getCurrency().getCode());
	}

	@Test
	public void givenAccountJpaRepositry_CallingloadAccounts_ShouldReturnAccountsCollecton() {
		Collection<Account> loadAccounts = accountJpaRepository.loadAccounts();
		Assert.assertNotNull(loadAccounts);
	}

	@Test
	public void givenAccountJpaRepositry_CallingCreateAccount_ShouldCreateAccount() {
		Account account = populateNewAccount();
		accountJpaRepository.createAccount(account);
		assertEquals(account.getIban(), NEW_ACCOUNT_IBAN);
	}

	@Test(expected = AccountIsAlreadyExistException.class)
	public void givenAccountGateway_CallingCreateAccountWithPredifnedAccount_ShouldThrowAccountIsAlreadyExistException() {
		Account accountByIBAN = accountJpaRepository.loadAccountByIBAN(AVAILABLE_ACCOUNT_IBAN);
		accountJpaRepository.createAccount(accountByIBAN);
	}

	@Test(expected = NoAccountHasBeenUpdated.class)
	public void givenAccountJpaRepositry_CallingUpdateAccount_ShouldThrowNoAccountHasBeenUpdated() {
		Account account = accountJpaRepository.loadAccountByIBAN(AVAILABLE_ACCOUNT_IBAN);
		account.setStatus(null);
		accountJpaRepository.updateAccount(account);
	}

	private Account populateNewAccount() {
		Account insertAccount = new Account();
		Currency currency = new Currency();
		currency.setCode("JOD");
		insertAccount.setIban(NEW_ACCOUNT_IBAN);
		insertAccount.setBalance(new BigDecimal(500));
		insertAccount.setCurrency(currency);
		insertAccount.setType("investment");
		insertAccount.setStatus("ACTIVE");
		insertAccount.setRule("THIS_MONTH");
		return insertAccount;
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
