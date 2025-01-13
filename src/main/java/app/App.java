package app;

import app.View.MainView;
import javax.swing.*;

/**
 * Main class of the application.
 */
public class App {
  /**
   * Main method of the application.
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> { // Run the GUI code on the Event-Dispatch Thread
      MainView mainView = new MainView(); // Create the main view
      mainView.setVisible(true); // Show the main view
    });
  }
}