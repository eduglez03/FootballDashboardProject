package app.Controller.Strategies;

import app.Controller.ControllerStrategy;
import app.Model.FootballAPIService;
import app.Model.Match;
import app.Model.MatchNotifier;
import app.Model.MatchUpdater;
import app.View.Components.LiveMatchesView;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * LiveMatchesController
 */
public class LiveMatchesController implements ControllerStrategy {
  private final LiveMatchesView liveMatchesView; // Live matches view
  private final MatchNotifier matchNotifier; // Match notifier
  private ScheduledExecutorService scheduler; // Scheduler for periodic updates
  private MatchUpdater matchUpdater; // Match updater

  /**
   * Constructor
   *
   * @param liveMatchesView Live matches view
   */
  public LiveMatchesController(LiveMatchesView liveMatchesView) {
    this.liveMatchesView = liveMatchesView;
    this.matchNotifier = new MatchNotifier();
    FootballAPIService footballAPIService = new FootballAPIService();
    this.matchUpdater = new MatchUpdater(footballAPIService, matchNotifier);

    // Set the back button listener
    liveMatchesView.addBackButtonListener(e -> stop());
  }

  /**
   * Set the match updater and execute the strategy
   *
   * @param matchUpdater Match updater
   */
  @Override
  public void execute() {
    liveMatchesView.show(); // Show the live matches view
    run(); // Start the periodic updates
  }

  /**
   * Start the scheduler to update the live matches every minute
   */
  public void run() {
    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(() -> {
      try {
        List<Object[]> liveMatches = getLiveMatches();
        SwingUtilities.invokeLater(() -> liveMatchesView.updateMatches(liveMatches));
      } catch (Exception e) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                liveMatchesView.getPanel(),
                "Error al actualizar los partidos en directo: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        ));
      }
    }, 0, 1, TimeUnit.MINUTES);
  }

  /**
   * Stop the scheduler and hide the live matches view
   */
  public void stop() {
    if (scheduler != null && !scheduler.isShutdown()) { scheduler.shutdown(); }
    liveMatchesView.hide();
  }

  /**
   * Get the live matches
   *
   * @return List of live matches
   */
  public List<Object[]> getLiveMatches() {
    if (matchUpdater == null) {
      return List.of(); // Return an empty list if the match updater is null
    }

    Map<String, Match> liveMatches = matchUpdater.getLiveMatches(); // Get the live matches

    // Map the live matches to a list of objects
    return liveMatches.values().stream()
            .map(match -> new Object[]{
                    match.getHomeTeam(),
                    match.getHomeGoals(),
                    match.getTime(),
                    match.getAwayGoals(),
                    match.getAwayTeam()
            })
            .collect(Collectors.toList());
  }
}
