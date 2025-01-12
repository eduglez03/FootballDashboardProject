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

public class LiveMatchesController implements ControllerStrategy {
  private final LiveMatchesView liveMatchesView;
  private final MatchNotifier matchNotifier;
  private ScheduledExecutorService scheduler;
  private MatchUpdater matchUpdater;

  public LiveMatchesController(LiveMatchesView liveMatchesView) {
    this.liveMatchesView = liveMatchesView;
    this.matchNotifier = new MatchNotifier();
    FootballAPIService footballAPIService = new FootballAPIService();
    this.matchUpdater = new MatchUpdater(footballAPIService, matchNotifier);

    // Configurar el botón "Volver"
    liveMatchesView.addBackButtonListener(e -> stop());
  }

  @Override
  public void execute() {
    // Mostrar la vista de partidos en directo
    liveMatchesView.show();

    // Iniciar la actualización periódica
    run();
  }

  public void run() {
    // Inicializar el scheduler para actualizar los partidos en vivo cada minuto
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

  public void stop() {
    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
    }
    liveMatchesView.hide();
  }

  public List<Object[]> getLiveMatches() {
    if (matchUpdater == null) {
      return List.of(); // Retorna una lista vacía si no se ha inicializado
    }

    Map<String, Match> liveMatches = matchUpdater.getLiveMatches();

    // Convertir el mapa a una lista de arreglos de objetos
    return liveMatches.values().stream()
            .map(match -> new Object[]{
                    match.getHomeTeam(),
                    match.getHomeGoals(),
                    match.getTime(),
                    match.getAwayGoals(),
                    match.getAwayTeam()
            })
            .collect(Collectors.toList()); // Usar Collectors.toList() para compatibilidad
  }
}
