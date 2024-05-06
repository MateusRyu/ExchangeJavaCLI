package br.dev.ryu.ExchangeJavaCLI.presenter;

import br.dev.ryu.ExchangeJavaCLI.model.ApiConfig;
import br.dev.ryu.ExchangeJavaCLI.model.ConfigurationManager;
import br.dev.ryu.ExchangeJavaCLI.view.View;

public class ConfigPresenter {
    private final ConfigurationManager CONFIG;

    public ConfigPresenter(String configFilePath, View view) {
        this.CONFIG = new ConfigurationManager(configFilePath);
        if (!this.CONFIG.configExists()) {
            ApiConfig apiConfig = view.requestConfig();
            this.CONFIG.setApiConfig(apiConfig);
        }
    }

    public ApiConfig getApiConfig() {
        return CONFIG.getApiConfig();
    }
}
