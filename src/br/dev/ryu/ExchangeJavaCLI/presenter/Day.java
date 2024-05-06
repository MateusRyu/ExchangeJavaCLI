package br.dev.ryu.ExchangeJavaCLI.presenter;

public class Day {
    private final int NUMBER;

    public Day(int number) {
        this.NUMBER = number;
    }

    @Override
    public String toString() {
        String suffix = switch (this.NUMBER % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };

        return String.format("%d%s", this.NUMBER, suffix);
    }
}
