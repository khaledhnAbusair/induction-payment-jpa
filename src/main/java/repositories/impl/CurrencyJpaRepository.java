package repositories.impl;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Currency;
import repositories.CurrencyGateway;
import repositories.exceptions.InvalidCurrencyCodeExpection;
import repositories.exceptions.NullCurrencyCodeException;
import repositories.loader.EntityManagerLoader;

public class CurrencyJpaRepository implements CurrencyGateway {

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
		return currencies.getResultList();
	}

	@Override
	public Currency loadCurrencyByCode(String currencyCode) {

		if (Objects.isNull(currencyCode))
			throw new NullCurrencyCodeException();
		Currency find = entityManager.find(Currency.class, currencyCode);
		if (Objects.isNull(find))
			throw new InvalidCurrencyCodeExpection();
		return find;
	}
}
