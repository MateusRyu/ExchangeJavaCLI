package br.dev.ryu.ExchangeJavaCLI.presenter;

import br.dev.ryu.ExchangeJavaCLI.model.ApiConfig;
import br.dev.ryu.ExchangeJavaCLI.model.ConfigurationManager;
import br.dev.ryu.ExchangeJavaCLI.view.View;

public class ConfigPresenter {
    private ConfigurationManager config;

    public ConfigPresenter(String configFilePath, View view) {
        try {
            this.config = new ConfigurationManager(configFilePath);
        } catch (Exception e){
            ApiConfig apiConfig = view.requestConfig();
            this.config = new ConfigurationManager(configFilePath, apiConfig);
        }
    }

    public ApiConfig getApiConfig() {
        return config.getApiConfig();
    }
}
