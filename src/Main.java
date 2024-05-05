import br.dev.ryu.ExchangeJavaCLI.presenter.ConfigPresenter;
import br.dev.ryu.ExchangeJavaCLI.presenter.CurrencyPresenter;
import br.dev.ryu.ExchangeJavaCLI.presenter.PresenterRouter;
import br.dev.ryu.ExchangeJavaCLI.view.CommandLineView;

public class Main {
    public static void main(String[] args) {
        CommandLineView view = new CommandLineView();
        ConfigPresenter configPresenter = new ConfigPresenter("config.json", view);
        CurrencyPresenter currencyPresenter = new CurrencyPresenter(view);
        view.setPresenter(currencyPresenter);
        PresenterRouter router = new PresenterRouter(configPresenter, currencyPresenter);

        try {
            router.run();
        } catch (RuntimeException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}