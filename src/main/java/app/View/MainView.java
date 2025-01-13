package app.View;

import app.Controller.Strategies.LeagueController;
import app.Controller.Strategies.NotificationController;
import app.Controller.Strategies.LineUpController;
import app.View.Components.LeagueView;
import app.View.Components.LineUpView;
import app.View.Components.LiveMatchesView;
import app.Controller.MainController;
import app.Controller.Strategies.LiveMatchesController;
import app.View.Components.NotificationView;

import javax.swing.*;
import java.awt.*;

/**
 * MainView class that extends JFrame and creates the main window of the application.
 */
public class MainView extends JFrame {
  private final MainController controller; // Main controller
  private final LiveMatchesView liveMatchesView; // Live matches view
  private final LineUpView lineUpView; // Line up view
  private final NotificationView NotificationView; // Notification view
  private final LeagueView leagueView; // League view

  /**
   * Constructor that initializes the controller and its services, as well as the views.
   */
  public MainView() {
    this.controller = new MainController(); // Set the controller
    this.liveMatchesView = new LiveMatchesView(); // Set the live matches view
    this.lineUpView = new LineUpView(); // Set the line up view
    this.NotificationView = new NotificationView(); // Set the notification view
    this.leagueView = new LeagueView();  // Set the league view

    setTitle("Dashboard de Fútbol");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Create a panel for the left side of the window
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout(5, 5)); // Layout with 5px of padding

    // Create the main menu panel
    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new GridLayout(6, 1)); // 6 rows, 1 column
    JButton liveMatchesButton = new JButton("Partidos en Directo"); // Live matches button
    JButton lineupsButton = new JButton("Mostrar Alineaciones"); // Line ups button
    JButton NotificationsButton = new JButton("Notificaciones en Directo"); // Live notifications button
    JButton LeagueStatsButton = new JButton("Estadísticas de Liga"); // League stats button
    JButton exitButton = new JButton("Salir"); // Exit button

    menuPanel.add(liveMatchesButton);
    menuPanel.add(lineupsButton);
    menuPanel.add(NotificationsButton);
    menuPanel.add(LeagueStatsButton);
    menuPanel.add(exitButton);

    // Set the menu panel size
    menuPanel.setPreferredSize(new Dimension(300, 300));
    JLabel menuLabel = new JLabel("Menú", JLabel.CENTER);
    menuLabel.setFont(new Font("Arial", Font.BOLD, 30));
    menuPanel.add(menuLabel, 0);

    // Add the menu panel to the left panel
    leftPanel.add(menuPanel, BorderLayout.NORTH);

    // Set the notification panel
    JPanel notificationPanel = new JPanel();
    notificationPanel.setLayout(new BorderLayout());
    notificationPanel.add(NotificationView.getPanel(), BorderLayout.CENTER);

    notificationPanel.setPreferredSize(new Dimension(300, 500)); // 50% of the window height

    // Add the notification panel to the left panel
    leftPanel.add(notificationPanel, BorderLayout.SOUTH);

    // Add the left panel to the main layout
    add(leftPanel, BorderLayout.WEST);

    // Create a panel for the center of the window
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    // Add the views to the center panel
    centerPanel.add(liveMatchesView.getPanel());  // Live matches view
    centerPanel.add(leagueView.getPanel());  // League view

    // Add the center panel to the main layout
    add(centerPanel, BorderLayout.CENTER);

    // Add the line up view to the right side of the window
    add(lineUpView.getPanel(), BorderLayout.EAST);

    // Add the action listeners to the buttons of the live matches view
    liveMatchesButton.addActionListener(e -> {
      controller.setStrategy(new LiveMatchesController(liveMatchesView));
      controller.executeStrategy();
    });

    // Add the action listeners to the buttons of the line up view
    lineupsButton.addActionListener(e -> {
      controller.setStrategy(new LineUpController(lineUpView));
      controller.executeStrategy();  // Llama al método execute() de LineUpController
    });

    // Add the action listeners to the buttons of the notification view
    NotificationsButton.addActionListener(e -> {
      controller.setStrategy(new NotificationController(NotificationView));
      controller.executeStrategy();
    });

    // Add the action listeners to the buttons of the league view
    LeagueStatsButton.addActionListener(e -> {
      controller.setStrategy(new LeagueController(leagueView));
      controller.executeStrategy();
    });

    // Add the action listener to the exit button
    exitButton.addActionListener(e -> System.exit(0));
  }
}