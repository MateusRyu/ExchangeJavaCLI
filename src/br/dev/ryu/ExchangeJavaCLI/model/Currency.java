package br.dev.ryu.ExchangeJavaCLI.model;

public record Currency(String code, String name) {
    @Override
    public String toString() {
        return String.format("%s (%s) ", this.name, this.code);
    }
}
