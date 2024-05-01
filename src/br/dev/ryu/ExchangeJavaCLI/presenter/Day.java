package br.dev.ryu.ExchangeJavaCLI.presenter;

public class Day {
    private final int number;

    public Day(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        String suffix = switch (this.number % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };

        return String.format("%d%s", this.number, suffix);
    }
}
