package repositories;

import java.util.Collection;

import entity.Account;

public interface AccountGateway {

	public Account loadAccountByIBAN(String iban);

	public Collection<Account> loadAccounts();

	void updateAccount(Account account);

	void createAccount(Account newAccount);

}