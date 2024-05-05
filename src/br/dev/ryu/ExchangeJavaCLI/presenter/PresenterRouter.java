package br.dev.ryu.ExchangeJavaCLI.presenter;

import br.dev.ryu.ExchangeJavaCLI.model.ApiConfig;

public class PresenterRouter {
    ConfigPresenter configPresenter;
    CurrencyPresenter currencyPresenter;

    public PresenterRouter(ConfigPresenter configPresenter, CurrencyPresenter currencyPresenter) {
        this.configPresenter = configPresenter;
        this.currencyPresenter = currencyPresenter;
    }

    public void run() {
        this.currencyPresenter.buildCurrencyConverter(getApiConfig());
        this.currencyPresenter.updateCurrencies();
        currencyPresenter.displayWelcome();
        currencyPresenter.displayStartMenu();
    }

    public ApiConfig getApiConfig() {
         return configPresenter.getApiConfig();
    }
}
