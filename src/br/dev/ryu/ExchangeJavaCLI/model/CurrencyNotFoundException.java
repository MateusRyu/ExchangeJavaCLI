package br.dev.ryu.ExchangeJavaCLI.model;

public class CurrencyNotFoundException extends RuntimeException {
    private final String MESSAGE;

    public CurrencyNotFoundException(Currency currency) {
        this.MESSAGE = "Currency not found: " + currency;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
