package app.Controller.Strategies;

import app.Controller.ControllerStrategy;
import app.Model.*;
import app.View.Components.NotificationView;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

public class NotificationController implements ControllerStrategy {
    private final NotificationView notificationView;
    private final MatchNotifier matchNotifier;
    private final Map<String, JTextArea> userNotificationAreas;
    private final List<UserSubscriber> subscribers;
    private ScheduledExecutorService scheduler;
    private MatchUpdater matchUpdater;
    private FootballAPIService footballAPIService;

    public NotificationController(NotificationView notificationView) {
        this.notificationView = notificationView;
        this.matchNotifier = new MatchNotifier();
        this.userNotificationAreas = new HashMap<>();
        this.subscribers = new ArrayList<>();
        this.footballAPIService = new FootballAPIService();
        this.matchUpdater = new MatchUpdater(footballAPIService, matchNotifier);

        // Configurar el botón "Volver"
        notificationView.addBackButtonListener(e -> stop());
    }
    @Override
    public void execute() {
        // Mostrar la vista de notificaciones
        notificationView.show();

        // Configurar el área de notificaciones para el único usuario
        JTextArea notificationArea = notificationView.getNotificationArea();
        userNotificationAreas.put("Usuario", notificationArea);

        // Crear el suscriptor y añadirlo al observador
        UserSubscriber subscriber = new UserSubscriber("Usuario", userNotificationAreas) {
            @Override
            public void update(Match match) {
                super.update(match);
            }
        };
        subscribers.clear();
        subscribers.add(subscriber);
        matchNotifier.addObserver(subscriber);

        // Iniciar la actualización periódica
        run();
    }

    public void run() {
        // Inicializar el scheduler para actualizar los resultados de los partidos cada minuto
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                matchUpdater.updateMatchResults();
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        notificationView.getPanel(),
                        "Error al actualizar los resultados: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                ));
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        // Reiniciar el área de notificaciones antes de ocultar la vista
        notificationView.resetNotificationArea();
        notificationView.hide();
    }
}