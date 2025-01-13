package app.Controller.Strategies;

import app.Controller.ControllerStrategy;
import app.Model.*;
import app.View.Components.NotificationView;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * NotificationController
 */
public class NotificationController implements ControllerStrategy {
    private final NotificationView notificationView; // NotificationView
    private final MatchNotifier matchNotifier; // MatchNotifier
    private final Map<String, JTextArea> userNotificationAreas; // Map of the user's notification areas
    private final List<UserSubscriber> subscribers; // List of subscribers
    private ScheduledExecutorService scheduler; // ScheduledExecutorService
    private MatchUpdater matchUpdater; // MatchUpdater
    private FootballAPIService footballAPIService; // FootballAPIService

    /**
     * Constructor
     * @param notificationView NotificationView
     */
    public NotificationController(NotificationView notificationView) {
        this.notificationView = notificationView;
        this.matchNotifier = new MatchNotifier();
        this.userNotificationAreas = new HashMap<>();
        this.subscribers = new ArrayList<>();
        this.footballAPIService = new FootballAPIService();
        this.matchUpdater = new MatchUpdater(footballAPIService, matchNotifier);

        // Set the back button listener
        notificationView.addBackButtonListener(e -> stop());
    }

    /**
     * Execute
     */
    @Override
    public void execute() {
        notificationView.show(); // Show the notification view

        // Set the notification area for the user
        JTextArea notificationArea = notificationView.getNotificationArea();
        userNotificationAreas.put("Usuario", notificationArea);

        // Create a subscriber for the user
        UserSubscriber subscriber = new UserSubscriber("Usuario", userNotificationAreas) {
            @Override
            public void update(Match match) {
                super.update(match);
            }
        };
        subscribers.clear();
        subscribers.add(subscriber);
        matchNotifier.addObserver(subscriber);

        // Start the scheduler
        run();
    }

    /**
     * Run the scheduler
     */
    public void run() {
        // Run the scheduler to update the match results every minute
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

    /**
     * Stop the scheduler
     */
    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        // Reset the notification area
        notificationView.resetNotificationArea();
        notificationView.hide();
    }
}