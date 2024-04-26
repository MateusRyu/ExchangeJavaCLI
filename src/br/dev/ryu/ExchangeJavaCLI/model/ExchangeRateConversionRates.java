package br.dev.ryu.ExchangeJavaCLI.model;

import java.util.HashMap;

public record ExchangeRateConversionRates(int timeLastUpdateUnix, int timeNextUpdateUnix, String baseCode, HashMap<String, Double> conversionRates) {
}
