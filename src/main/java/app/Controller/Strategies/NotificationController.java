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