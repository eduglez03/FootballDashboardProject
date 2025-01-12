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

public class MainView extends JFrame {
  private final MainController controller;
  private final LiveMatchesView liveMatchesView;
  private final LineUpView lineUpView;
  private final NotificationView NotificationView;
  private final LeagueView leagueView;

  public MainView() {
    this.controller = new MainController(); // Inicializa el controlador y sus servicios
    this.liveMatchesView = new LiveMatchesView();
    this.lineUpView = new LineUpView(); // Inicializa la vista de alineaciones
    this.NotificationView = new NotificationView(); // Inicializa la vista de notificaciones
    this.leagueView = new LeagueView();  // Inicializa la vista de estadísticas de liga

    setTitle("Dashboard de Fútbol");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Crear un panel para el menú lateral y las notificaciones
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout(5, 5)); // Layout para el menú y las notificaciones

    // Crear el menú lateral
    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new GridLayout(6, 1)); // Ahora hay 6 opciones en el menú
    JButton liveMatchesButton = new JButton("Partidos en Directo");
    JButton lineupsButton = new JButton("Mostrar Alineaciones"); // Nuevo botón para alineaciones
    JButton NotificationsButton = new JButton("Notificaciones en Directo");
    JButton LeagueStatsButton = new JButton("Estadísticas de Liga");
    JButton exitButton = new JButton("Salir");

    menuPanel.add(liveMatchesButton);
    menuPanel.add(lineupsButton);
    menuPanel.add(NotificationsButton);
    menuPanel.add(LeagueStatsButton);
    menuPanel.add(exitButton);

    // Ajustar el tamaño preferido del menú lateral
    menuPanel.setPreferredSize(new Dimension(300, 300)); // 150px de ancho para el menú
    JLabel menuLabel = new JLabel("Menú", JLabel.CENTER);
    menuLabel.setFont(new Font("Arial", Font.BOLD, 30));
    menuPanel.add(menuLabel, 0); // Añadir el JLabel al principio del GridLayout

    // Añadir el menú al panel izquierdo
    leftPanel.add(menuPanel, BorderLayout.NORTH);

    // Ajustar la vista de notificaciones para que ocupe la mitad de la pantalla
    JPanel notificationPanel = new JPanel();
    notificationPanel.setLayout(new BorderLayout());
    notificationPanel.add(NotificationView.getPanel(), BorderLayout.CENTER);

    // Establecer la altura del panel de notificaciones al 50% de la altura de la ventana
    notificationPanel.setPreferredSize(new Dimension(300, 500)); // 50% de la altura

    // Añadir las notificaciones al panel izquierdo
    leftPanel.add(notificationPanel, BorderLayout.SOUTH);

    // Añadir el panel izquierdo al diseño principal
    add(leftPanel, BorderLayout.WEST);

    // Crear un panel contenedor para los partidos en directo y las estadísticas de liga
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Organizar de manera vertical

    // Añadir las vistas al panel contenedor
    centerPanel.add(liveMatchesView.getPanel());  // Vista de partidos en directo
    centerPanel.add(leagueView.getPanel());  // Vista de estadísticas de liga

    // Añadir el panel contenedor al diseño principal
    add(centerPanel, BorderLayout.CENTER);

    // Añadir la vista de alineaciones como está
    add(lineUpView.getPanel(), BorderLayout.EAST); // Añadir la vista de alineaciones como JPanel

    // Acción para el botón de "Partidos en Directo"
    liveMatchesButton.addActionListener(e -> {
      controller.setStrategy(new LiveMatchesController(liveMatchesView));
      controller.executeStrategy();
    });

    // Acción para el botón de "Mostrar Alineaciones"
    lineupsButton.addActionListener(e -> {
      controller.setStrategy(new LineUpController(lineUpView));
      controller.executeStrategy();  // Llama al método execute() de LineUpController
    });

    // Acción para el botón de "Notificaciones en Directo"
    NotificationsButton.addActionListener(e -> {
      controller.setStrategy(new NotificationController(NotificationView));
      controller.executeStrategy();
    });

    // Acción para el botón de "Estadísticas"
    LeagueStatsButton.addActionListener(e -> {
      controller.setStrategy(new LeagueController(leagueView));
      controller.executeStrategy();
    });

    // Acción para el botón de "Salir"
    exitButton.addActionListener(e -> System.exit(0));
  }
}
