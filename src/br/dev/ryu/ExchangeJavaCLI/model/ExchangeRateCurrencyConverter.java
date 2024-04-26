package br.dev.ryu.ExchangeJavaCLI.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;

public class ExchangeRateCurrencyConverter extends WebApiConnector implements CurrencyConverter {
    private final String apiKey;
    private final HashMap<String, CurrencyRates> currencyRates = new HashMap<>();
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public ExchangeRateCurrencyConverter(String apiKey, String version) {
        setBaseUrl("https://v6.exchangerate-api.com/"       + version + "/");
        this.apiKey = apiKey;

    }

    private void updateCurrencyRates(Currency currency) throws IOException, InterruptedException {
        URI endpoint = URI.create(this.baseUri + this.apiKey + "/" + currency.code());
        HttpResponse<String> response = requestUri(endpoint, Duration.ofSeconds(20));
        ExchangeRateConversionRates data = this.gson.fromJson(response.body(), ExchangeRateConversionRates.class);
        CurrencyRates currencyRates = new CurrencyRates(
                currency,
                data.conversionRates(),
                data.timeLastUpdateUnix(),
                data.timeNextUpdateUnix()
        );
        this.currencyRates.put(currency.code(), currencyRates);
    }

    public double convert(double amount, Currency fromCurrency, Currency toCurrency) throws IOException, InterruptedException {
        if (!this.currencyRates.containsKey(fromCurrency.code())) {
            updateCurrencyRates(fromCurrency);
        }
        CurrencyRates currencyRates = this.currencyRates.get(fromCurrency.code());
        if (System.currentTimeMillis() > currencyRates.getTimeNextUpdateUnix()) {
            updateCurrencyRates(fromCurrency);
            currencyRates = this.currencyRates.get(fromCurrency.code());
        }
        return amount * currencyRates.getRate(toCurrency);
    }

    public HashMap<String, Currency> getCurrencies() throws IOException, InterruptedException {
        URI endpoint = URI.create(this.baseUri + this.apiKey + "/codes");
        HttpResponse<String> response = requestUri(endpoint, Duration.ofSeconds(20));
        HashMap<String, Currency> currencies = new HashMap<>();
        ExchangeRateSupportedCurrencies data = this.gson.fromJson(response.body(), ExchangeRateSupportedCurrencies.class);
        for (Currency i : data.supportedCodes()) {
            Currency currency = new Currency( i.code(), i.name());
            currencies.put(currency.code(), currency);
        }
        return currencies;
    }

    public ExchangeRateQuota getQuota() throws IOException, InterruptedException {
        URI endpoint = URI.create(this.baseUri + this.apiKey + "/quota");
        HttpResponse<String> response = requestUri(endpoint, Duration.ofSeconds(20));
        return this.gson.fromJson(response.body(), ExchangeRateQuota.class);
    }

}
