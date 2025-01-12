package app.Controller;

public class MainController {
  private ControllerStrategy strategy;

  // Constructor que inicializa todos los servicios relacionados con la API
  public MainController() {}

  public void setStrategy(ControllerStrategy strategy) {
    this.strategy = strategy;
  }

  public void executeStrategy() {
    if (strategy != null) {
      strategy.execute();
    }
  }
}
