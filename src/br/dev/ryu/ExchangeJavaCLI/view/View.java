package br.dev.ryu.ExchangeJavaCLI.view;

import br.dev.ryu.ExchangeJavaCLI.model.ApiConfig;
import br.dev.ryu.ExchangeJavaCLI.model.Currency;
import br.dev.ryu.ExchangeJavaCLI.presenter.CurrencyPresenter;
import br.dev.ryu.ExchangeJavaCLI.presenter.Day;

import java.util.List;

public interface View {
    void setPresenter(CurrencyPresenter presenter);
    void displayStartMenu();
    void displayConversionResult(double amount, Currency fromCurrency, Currency toCurrency, double result);
    boolean displayRetryMenu(String menuMessage);
    void displayError(String message);
    List<String> requestConversion();
    void displayQuota(int planQuota, int requestsRemaining, Day refreshDayOfMonth);
    ApiConfig requestConfig();
    void displayWelcome();
}
