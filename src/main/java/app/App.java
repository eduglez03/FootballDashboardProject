package app;

import app.View.MainView;
import javax.swing.*;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      MainView mainView = new MainView();
      mainView.setVisible(true);
    });
  }
}