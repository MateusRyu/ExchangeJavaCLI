package br.dev.ryu.ExchangeJavaCLI.model;

public class CurrencyNotFoundException extends RuntimeException {
    private final String message;

    public CurrencyNotFoundException(Currency currency) {
        this.message = "Currency not found: " + currency;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
