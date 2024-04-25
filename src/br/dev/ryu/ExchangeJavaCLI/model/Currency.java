package br.dev.ryu.ExchangeJavaCLI.model;

public record Currency(String code, String formalName) {
    @Override
    public String toString() {
        return String.format("%s (%s) ", this.formalName, this.code);
    }
}
