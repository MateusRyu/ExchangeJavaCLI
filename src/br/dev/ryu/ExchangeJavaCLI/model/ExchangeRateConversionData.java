package br.dev.ryu.ExchangeJavaCLI.model;

public record ExchangeRateConversionData(int timeLastUpdateUnix, int timeNextUpdateUnix, String baseCode, String targetCode, double conversionRate) {
}
