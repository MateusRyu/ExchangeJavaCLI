package br.dev.ryu.ExchangeJavaCLI.model;

public record ExchangeRateQuota(int planQuota, int requestsRemaining, int refreshDayOfMonth) {
}