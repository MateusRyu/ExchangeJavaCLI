import br.dev.ryu.ExchangeJavaCLI.model.ApiConfig;
import br.dev.ryu.ExchangeJavaCLI.model.ConfigurationManager;
import br.dev.ryu.ExchangeJavaCLI.model.CurrencyConverter;
import br.dev.ryu.ExchangeJavaCLI.model.ExchangeRateCurrencyConverter;
import br.dev.ryu.ExchangeJavaCLI.presenter.CurrencyPresenter;
import br.dev.ryu.ExchangeJavaCLI.view.CommandLineView;

public class Main {
    public static void main(String[] args) {
        CommandLineView view = new CommandLineView();
        ConfigurationManager config;
        String filePath = "config.json";
        try {
            config = new ConfigurationManager(filePath);
        } catch (Exception e){
            ApiConfig apiConfig = view.requestConfig();
            config = new ConfigurationManager(filePath, apiConfig);
        }

        ApiConfig configApi = config.getApiConfig();
        view.displayWelcome();


        CurrencyConverter currencyConverter = new ExchangeRateCurrencyConverter(configApi.key(), configApi.version());
        CurrencyPresenter presenter = new CurrencyPresenter(view, currencyConverter);
        view.setPresenter(presenter);

        try {
            presenter.run();
        } catch (RuntimeException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}