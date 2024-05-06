package br.dev.ryu.ExchangeJavaCLI.model;

public class ConversionRateNotFoundException extends RuntimeException {
    private final String MESSAGE;

    public ConversionRateNotFoundException(Currency currency) {
        this.MESSAGE = "Conversion rate not found to : " + currency;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

