package br.dev.ryu.ExchangeJavaCLI.model;

public class CurrencyRate {
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private double rate;
    private int timeLastUpdateUnix;
    private int timeNextUpdateUnix;

    public CurrencyRate(Currency fromCurrency, Currency toCurrency, double rate, int timeLastUpdateUnix, int timeNextUpdateUnix) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        updateRate(rate, timeLastUpdateUnix, timeNextUpdateUnix);
    }

    public void updateRate(double newRate, int timeLastUpdateUnix, int timeNextUpdateUnix) {
        this.rate = newRate;
        this.timeLastUpdateUnix = timeLastUpdateUnix;
        this.timeNextUpdateUnix = timeNextUpdateUnix;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public double getRate() {
        return rate;
    }

    public int getTimeLastUpdateUnix() {
        return timeLastUpdateUnix;
    }

    public int getTimeNextUpdateUnix() {
        return timeNextUpdateUnix;
    }
}
