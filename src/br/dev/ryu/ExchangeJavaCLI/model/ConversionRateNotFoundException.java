package br.dev.ryu.ExchangeJavaCLI.model;

public class ConversionRateNotFoundException extends RuntimeException {
    private final String message;

    public ConversionRateNotFoundException(Currency currency) {
        this.message = "Conversion rate not found to : " + currency;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

