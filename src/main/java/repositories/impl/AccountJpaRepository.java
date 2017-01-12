
package repositories.impl;

import static repositories.sqlconstants.SqlConstants.FIND_ALL_ACCOUNTS_QUERY;
import static repositories.sqlconstants.SqlConstants.UPDATE_ACCOUNT_QUERY;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.BeanUtils;

import entity.Account;
import repositories.AccountGateway;
import repositories.exceptions.AccountIsAlreadyExistException;
import repositories.exceptions.AccountNotFoundExceptions;
import repositories.exceptions.NoAccountHasBeenUpdated;
import repositories.exceptions.NullAccountIBANException;
import repositories.exceptions.PopulateEntityException;
import repositories.loader.EntityManagerLoader;

public class AccountJpaRepository implements AccountGateway {
	private static final String ACCOUNT_CURRENCY_CODE = "currencyCode";
	private static final String ACCOUNT_BALANCE = "balance";
	private static final String ACCOUNT_STATUS = "status";
	private static final String ACCOUNT_IBAN = "iban";
	private static final String ACCOUNT_RULE = "rule";
	private static final String ACCOUNT_TYPE = "type";
	private static final String EMPTY_QUTES = "";
	private EntityManager entityManager;

	public AccountJpaRepository() {
		entityManager = EntityManagerLoader.getEntityManger();
	}

	public AccountJpaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Account loadAccountByIBAN(String iban) {
		isNullIban(iban);
		Account account = entityManager.find(Account.class, iban);
		isNullAcount(account);
		return account;
	}

	@Override
	public Collection<Account> loadAccounts() {
		TypedQuery<Account> accounts = entityManager.createNamedQuery(FIND_ALL_ACCOUNTS_QUERY, Account.class);
		return accounts.getResultList();
	}

	@Override
	public void updateAccount(Account account) {
		boolean accountCommit = false;
		Query query = entityManager.createQuery(UPDATE_ACCOUNT_QUERY);
		populateAccountUpdateQuery(account, query);
		try {
			entityManager.getTransaction().begin();
			int effectedRowUpdated = query.executeUpdate();
			if (effectedRowUpdated == 0)
				throw new NoAccountHasBeenUpdated();
			entityManager.getTransaction().commit();
			accountCommit = true;
		} finally {
			if (!accountCommit)
				entityManager.getTransaction().rollback();
		}
	}

	@Override
	public void createAccount(Account newAccount) {
		Account account = populateNewAccount(newAccount);
		entityManager.getTransaction().begin();
		boolean commited = false;
		try {
			Account find = entityManager.find(Account.class, account.getIban());
			if (Objects.nonNull(find)) {
				throw new AccountIsAlreadyExistException();
			}
			entityManager.persist(newAccount);
			entityManager.getTransaction().commit();
			commited = true;
		} finally {
			if (!commited)
				entityManager.getTransaction().rollback();
		}
	}

	private void populateAccountUpdateQuery(Account account, Query query) {
		query.setParameter(ACCOUNT_TYPE, account.getType());
		query.setParameter(ACCOUNT_BALANCE, account.getBalance());
		query.setParameter(ACCOUNT_STATUS, account.getStatus());
		query.setParameter(ACCOUNT_CURRENCY_CODE, account.getCurrency());
		query.setParameter(ACCOUNT_RULE, account.getRule());
		query.setParameter(ACCOUNT_IBAN, account.getIban());
	}

	private Account populateNewAccount(Account newAccount) {
		Account account = new Account();
		try {
			BeanUtils.copyProperties(account, newAccount);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new PopulateEntityException(e);
		}
		return account;
	}

	private void isNullIban(String iban) {
		if (Objects.isNull(iban) || EMPTY_QUTES.equals(iban))
			throw new NullAccountIBANException();
	}

	private void isNullAcount(Account account) {
		if (Objects.isNull(account)) {
			throw new AccountNotFoundExceptions();
		}
	}
}
