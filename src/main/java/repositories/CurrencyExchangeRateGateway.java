package repositories;

import com.progressoft.jip.datastructures.CurrencyExchangeRateDataStructure;

public interface CurrencyExchangeRateGateway {

	CurrencyExchangeRateDataStructure loadCurrencyExchangeRate(String codeFrom, String codeTo);

}