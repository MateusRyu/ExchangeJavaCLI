package br.dev.ryu.ExchangeJavaCLI.model;

import java.util.List;

public record ExchangeRateSupportedCurrencies(String result, List<Currency> supportedCodes) {
}
