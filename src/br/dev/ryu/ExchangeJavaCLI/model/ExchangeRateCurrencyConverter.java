package br.dev.ryu.ExchangeJavaCLI.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class ExchangeRateCurrencyConverter extends WebApiConnector implements CurrencyConverter {
    private final String API_KEY;
    private final HashMap<String, CurrencyRates> CURRENCY_RATES = new HashMap<>();
    private final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public ExchangeRateCurrencyConverter(String apiKey, String version) {
        setBaseUrl("https://v6.exchangerate-api.com/"       + version + "/");
        this.API_KEY = apiKey;

    }

    private void updateCurrencyRates(Currency currency) throws IOException, InterruptedException {
        URI endpoint = URI.create(this.baseUri + this.API_KEY + "/latest/" + currency.code());
        HttpResponse<String> response = requestUri(endpoint, Duration.ofSeconds(60));
        ExchangeRateConversionRates data = GSON.fromJson(response.body(), ExchangeRateConversionRates.class);

        CurrencyRates currencyRates = new CurrencyRates(
                currency,
                data.conversionRates(),
                data.timeLastUpdateUnix(),
                data.timeNextUpdateUnix()
        );
        this.CURRENCY_RATES.put(currency.code(), currencyRates);
    }

    public double convert(double amount, Currency fromCurrency, Currency toCurrency) throws IOException, InterruptedException {
        if (!this.CURRENCY_RATES.containsKey(fromCurrency.code())) {
            updateCurrencyRates(fromCurrency);
        }
        CurrencyRates currencyRates = this.CURRENCY_RATES.get(fromCurrency.code());
        if (System.currentTimeMillis() > currencyRates.getTimeNextUpdateUnix()) {
            updateCurrencyRates(fromCurrency);
            currencyRates = this.CURRENCY_RATES.get(fromCurrency.code());
        }
        return amount * currencyRates.getRate(toCurrency);
    }

    public HashMap<String, Currency> getCurrencies() throws IOException, InterruptedException {
        URI endpoint = URI.create(this.baseUri + this.API_KEY + "/codes");
        HttpResponse<String> response = requestUri(endpoint, Duration.ofSeconds(20));
        HashMap<String, Currency> currencies = new HashMap<>();
        ExchangeRateSupportedCurrencies data = GSON.fromJson(response.body(), ExchangeRateSupportedCurrencies.class);
        for (int i = 0; i < data.supportedCodes().size(); i++) {
            List<String> ApiCurrency = data.supportedCodes().get(i);
            Currency currency = new Currency( ApiCurrency.getFirst(), ApiCurrency.getLast());
            currencies.put(currency.code(), currency);
        }
        return currencies;
    }

    public ExchangeRateQuota getQuota() throws IOException, InterruptedException {
        URI endpoint = URI.create(this.baseUri + this.API_KEY + "/quota");
        HttpResponse<String> response = requestUri(endpoint, Duration.ofSeconds(20));
        return GSON.fromJson(response.body(), ExchangeRateQuota.class);
    }

}
