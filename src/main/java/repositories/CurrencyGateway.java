package repositories;

import java.util.Collection;

import entity.Currency;

public interface CurrencyGateway {

    Collection<Currency> loadCurrencies();

    Currency loadCurrencyByCode(String currencyCode);

}
