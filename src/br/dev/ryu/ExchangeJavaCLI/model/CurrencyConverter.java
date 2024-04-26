package br.dev.ryu.ExchangeJavaCLI.model;

import java.io.IOException;
import java.util.HashMap;

public interface CurrencyConverter {
    double convert(double amount, Currency fromCurrency, Currency toCurrency) throws IOException, InterruptedException;
    HashMap<String, Currency> getCurrencies() throws IOException, InterruptedException;
}
