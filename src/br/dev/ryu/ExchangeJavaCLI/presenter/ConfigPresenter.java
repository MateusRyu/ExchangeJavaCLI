package br.dev.ryu.ExchangeJavaCLI.presenter;

import br.dev.ryu.ExchangeJavaCLI.model.ApiConfig;
import br.dev.ryu.ExchangeJavaCLI.model.ConfigurationManager;
import br.dev.ryu.ExchangeJavaCLI.view.View;

public class ConfigPresenter {
    private final ConfigurationManager config;

    public ConfigPresenter(String configFilePath, View view) {
        this.config = new ConfigurationManager(configFilePath);
        if (!this.config.configExists()) {
            ApiConfig apiConfig = view.requestConfig();
            this.config.setApiConfig(apiConfig);
        }
    }

    public ApiConfig getApiConfig() {
        return config.getApiConfig();
    }
}
