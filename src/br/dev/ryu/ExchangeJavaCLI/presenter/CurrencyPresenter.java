package br.dev.ryu.ExchangeJavaCLI.presenter;

import br.dev.ryu.ExchangeJavaCLI.model.*;
import br.dev.ryu.ExchangeJavaCLI.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CurrencyPresenter {
    private final View view;
    private final CurrencyConverter currencyConverter;
    private HashMap<String, Currency> supportedCurrencies;

    public CurrencyPresenter(View view, CurrencyConverter currencyConverter) {
        this.view = view;
        this.currencyConverter = currencyConverter;

        try {
            this.supportedCurrencies = this.currencyConverter.getCurrencies();
        } catch (IOException | InterruptedException e) {
            this.view.displayError("Process interrupted unexpectedly during currency conversion\n" + e.getMessage());
        } catch (Exception e) {
            this.view.displayError("An unexpected error occurred during the process.\n" + e.getMessage());
        }
    }

    public void run() {
        try {
            this.view.displayStartMenu();
        } catch (Exception e) {
            this.view.displayError(e.getMessage());
        }
    }

    public void handleStartMenu(int option, double amount) {
        switch (option) {
            case 0:
                System.out.println("Exiting app...");
                break;
            case 1:
                handleConversion(amount, "USD", "ARS");
                break;
            case 2:
                handleConversion(amount, "ARS", "USD");
                break;
            case 3:
                handleConversion(amount, "USD", "BRL");
                break;
            case 4:
                handleConversion(amount, "BRL", "USD");
                break;
            case 5:
                handleConversion(amount, "USD", "COP");
                break;
            case 6:
                handleConversion(amount, "COP", "USD");
                break;
            case 7:
                List<String> currencies = this.view.requestConversion();
                handleConversion(amount, currencies.get(0), currencies.get(1));
                this.view.displayStartMenu();
                break;
            case 8:
                displayQuota();
                break;
            default:
                this.view.displayError("Opção inválida");
        }
    }

    private Currency getCurrency(String currencyCode) {
        return this.supportedCurrencies.get(currencyCode);
    }

    private void handleConversion(double amount, String fromCurrencyCode, String toCurrencyCode) {
        int attempt = 0;
        final int maxAttempts = 3;
        boolean retry = true;
        while (retry && attempt < maxAttempts) {
            retry = false;
            attempt++;
            try {
                Currency fromCurrency = getCurrency(fromCurrencyCode);
                Currency toCurrency = getCurrency(toCurrencyCode);
                double rate = this.currencyConverter.convert(amount, fromCurrency, toCurrency);
                double conversion = amount * rate;
                view.displayConversionResult(amount, fromCurrency, toCurrency, conversion);
            } catch (CurrencyNotFoundException e) {
                this.view.displayError(e.getMessage());
            } catch (ConversionRateNotFoundException e) {
                this.view.displayError(e.getMessage());
            } catch (IOException | InterruptedException e) {
                this.view.displayError("Process interrupted unexpectedly during currency conversion\n" + e.getMessage());
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                retry = this.view.displayRetryMenu("Want to try the same conversion again? (return to main menu if not)");
            } catch (Exception e) {
                this.view.displayError("An unexpected error occurred during the process.\n" + e.getMessage());
            }
        }
        this.view.displayStartMenu();
    }

    private void displayQuota() {
        try {
            ExchangeRateQuota quota = this.currencyConverter.getQuota();
            Day refreshDayOfMonth = new Day( quota.refreshDayOfMonth() );
            this.view.displayQuota( quota.planQuota(), quota.requestsRemaining(), refreshDayOfMonth );
        } catch (IOException e) {
            this.view.displayError("Process interrupted unexpectedly during currency conversion\n" + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.view.displayError("Process interrupted unexpectedly during currency conversion\n" + e.getMessage());
        }
    }
}
