package br.dev.ryu.ExchangeJavaCLI.model;

import java.util.HashMap;

public class CurrencyRates {
    private final Currency currency;
    private HashMap<String, Double> conversionRates;
    private int timeLastUpdateUnix;
    private int timeNextUpdateUnix;

    public CurrencyRates(Currency currency, HashMap<String, Double> conversionRates, int timeLastUpdateUnix, int timeNextUpdateUnix) {
        this.currency = currency;
        updateRates(conversionRates, timeLastUpdateUnix, timeNextUpdateUnix);
    }

    public void updateRates(HashMap<String, Double> conversionRates, int timeLastUpdateUnix, int timeNextUpdateUnix) {
        this.conversionRates = conversionRates;
        this.timeLastUpdateUnix = timeLastUpdateUnix;
        this.timeNextUpdateUnix = timeNextUpdateUnix;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public double getRate(Currency currency) {
        if ( this.conversionRates.containsKey( currency.code() ) ) return this.conversionRates.get(currency.code());
        try {
            throw new CurrencyNotFoundException(currency);
        } catch (CurrencyNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTimeLastUpdateUnix() {
        return timeLastUpdateUnix;
    }

    public int getTimeNextUpdateUnix() {
        return timeNextUpdateUnix;
    }
}
