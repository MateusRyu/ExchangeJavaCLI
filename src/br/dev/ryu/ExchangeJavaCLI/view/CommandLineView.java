package br.dev.ryu.ExchangeJavaCLI.view;

import br.dev.ryu.ExchangeJavaCLI.model.ApiConfig;
import br.dev.ryu.ExchangeJavaCLI.model.Currency;
import br.dev.ryu.ExchangeJavaCLI.presenter.CurrencyPresenter;
import br.dev.ryu.ExchangeJavaCLI.presenter.Day;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CommandLineView implements View {
    private CurrencyPresenter presenter;
    private final Scanner SCANNER = new Scanner(System.in);

    public void setPresenter(CurrencyPresenter presenter) {
        this.presenter = presenter;
    }

    private void  printHorizontalBar(String character) {
        System.out.println(character.repeat(80));
    }


    public void displayStartMenu() {
        printHorizontalBar("-");
        System.out.println("Enter the number corresponding to the desired option and then press the [ENTER] button:");
        System.out.println("(1) United States Dollar (USD) to Argentine Peso (ARS)");
        System.out.println("(2) Argentine Peso (ARS) to United States Dollar (USD)");
        System.out.println("(3) United States Dollar (USD) to Brazilian Real (BRL)");
        System.out.println("(4) Brazilian Real (BRL) to United States Dollar (USD)");
        System.out.println("(5) United States Dollar (USD) to Colombian Peso (COP)");
        System.out.println("(6) Colombian Peso (COP) to United States Dollar (USD)");
        System.out.println("(7) Custom conversion");
        System.out.println("(8) See quota usage");
        System.out.println("(0) Exit");
        printHorizontalBar("_");

        int option = SCANNER.nextInt();

        if (option > 0 && option < 8) {
            System.out.println("What amount do you want to convert?");
            double amount = SCANNER.nextDouble();
            this.presenter.handleStartMenu(option, amount);
        } else if (option == 0) {
            printHorizontalBar("~");
            System.out.println("Thank you for using ExchangeJava! \nClosing the app...");
        } else if (option == 8){
            this.presenter.handleStartMenu(option, 0);
        } else {
            printHorizontalBar("|");
            System.out.println(option + " is not a valid option!!!");
            printHorizontalBar("|");
        }
    }

    public boolean displayConversionResult(double amount, Currency fromCurrency, Currency toCurrency, double result) {
        System.out.println("Conversion completed!");
        System.out.println("Converted from: " + amount + " " + fromCurrency.code() + " to " + result + " " + toCurrency.code());
        return waitUser();
    }

    public boolean waitUser() {
        System.out.println("\nEnter the number corresponding to the desired option and then press the [ENTER] button:");
        System.out.println("(1) Return to main menu");
        System.out.println("(2) Exit");
        return ( SCANNER.nextInt() == 1);
    }

    public boolean displayRetryMenu(String menuMessage) {
        System.out.println(menuMessage);
        System.out.println("Reply with 'Yes' or 'Not'.");
        String answer = SCANNER.nextLine();
        answer = answer.toLowerCase();
        return answer.equals("yes");
    }

    public void displayError(String error) {
        System.out.println("Error: " + error);
    }

    public List<String> requestConversion() {
        System.out.println("What is the currency base code?");
        List<String> currenciesCode = new LinkedList<>();
        currenciesCode.add(SCANNER.nextLine());
        System.out.println("What is the currency target code?");
        currenciesCode.add(SCANNER.nextLine());
        return currenciesCode;
    }

    public void displayQuota(int planQuota, int requestsRemaining, Day refreshDayOfMonth) {
        System.out.println("Your current plan allows for " + planQuota + " requests per month.");
        System.out.println("You have " + requestsRemaining + " requests remaining this month.");
        System.out.println("Your quota will refresh on the " + refreshDayOfMonth + " day of the next month.");
    }

    public void displayWelcome() {
        System.out.println("Be welcome to ExchangeJavaCLI!!!");
    }

    public ApiConfig requestConfig() {
        System.out.println("App need be configured. Please, type...");
        System.out.println("The ExchangeRate API key:");
        String key = SCANNER.nextLine();
        System.out.println("The ExchangeRate API version:");
        String version = SCANNER.nextLine();

        return new ApiConfig(key, version);
    }
}
