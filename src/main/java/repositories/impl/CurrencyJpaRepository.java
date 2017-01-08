package repositories.impl;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import entity.Currency;
import repositories.CurrencyGateway;
import repositories.exceptions.CurrenciesNotFoundException;
import repositories.exceptions.InvalidCurrencyCodeExpection;
import repositories.exceptions.NullCurrencyCodeException;
import repositories.loader.EntityManagerLoader;

public class CurrencyJpaRepository implements CurrencyGateway {

	private Currency currency;
	private EntityManager entityManager;

	public CurrencyJpaRepository() {
		entityManager = EntityManagerLoader.getEntityManger();
	}

	public CurrencyJpaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Collection<Currency> loadCurrencies() {
		TypedQuery<Currency> currencies = entityManager.createNamedQuery("Currency.findAll", Currency.class);
		if (Objects.isNull(currencies.getResultList()))
			throw new CurrenciesNotFoundException();
		return currencies.getResultList();
	}

	@Override
	public Currency loadCurrencyByCode(String currencyCode) {
		try {
			currency = loadCurrency(currencyCode);
		} catch (NoResultException e) {
			throw new InvalidCurrencyCodeExpection(e);
		}
		return currency;
	}

	private Currency loadCurrency(String currencyCode) {

		if (Objects.isNull(currencyCode))
			throw new NullCurrencyCodeException();
		Currency find = entityManager.find(Currency.class, currencyCode);
		if (Objects.isNull(find))
			throw new CurrenciesNotFoundException();

		return find;
	}

}
