package app.Controller;

/**
 * MainController
 */
public class MainController {
  private ControllerStrategy strategy; // Strategy pattern to execute the correct controller

  /**
   * Constructor
   */
  public MainController() {}

  /**
   * Set the strategy
   *
   * @param strategy
   */
  public void setStrategy(ControllerStrategy strategy) {
    this.strategy = strategy;
  }

  /**
   * Execute the strategy
   */
  public void executeStrategy() {
    if (strategy != null) { strategy.execute(); }
  }
}
