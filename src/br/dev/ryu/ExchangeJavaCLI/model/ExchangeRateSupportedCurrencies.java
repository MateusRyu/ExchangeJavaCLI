package br.dev.ryu.ExchangeJavaCLI.model;

import java.util.List;

public record ExchangeRateSupportedCurrencies(List<List<String>> supportedCodes) {
}
