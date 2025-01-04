package app.Model;

import java.util.Map;

/**
 * Class that updates match results and notifies users of any changes.
 */
public class MatchUpdater {
  private final FootballAPIService apiService; // API service to fetch match results
  private final MatchNotifier matchNotifier; // Notifier to send notifications to users
  private Map<String, Match> ongoingMatches; // Map to store ongoing matches

  /**
   * Constructor to initialize the MatchUpdater with the API service and match notifier.
   *
   * @param apiService     The API service to fetch match results
   * @param matchNotifier  The match notifier to send notifications
   */
  public MatchUpdater(FootballAPIService apiService, MatchNotifier matchNotifier) {
    this.apiService = apiService;
    this.matchNotifier = matchNotifier;
    this.ongoingMatches = apiService.fetchMatchResults();  // Inicializar con los resultados iniciales
  }

  /**
   * Method to update the match results and notify users of any changes.
   */
  public void updateMatchResults() {
    // Fetch new match results
    Map<String, Match> newMatches = apiService.fetchMatchResults();

    // Compare and notify users of any changes
    compareAndNotifyMatches(newMatches);
  }

  /**
   * Method to compare the new match results with the ongoing matches and notify users of any changes.
   *
   * @param newMatches  The new match results to compare
   */
  private void compareAndNotifyMatches(Map<String, Match> newMatches) {
    // Iterate over the new matches and compare with ongoing matches
    for (Map.Entry<String, Match> entry : newMatches.entrySet()) {
      Match newMatch = entry.getValue();
      Match oldMatch = ongoingMatches.get(entry.getKey());

      if (oldMatch == null || hasMatchChanged(newMatch, oldMatch)) { // Check if match has changed
        ongoingMatches.put(entry.getKey(), newMatch);  // Update the ongoing matches

        // Notify users of the match change
        matchNotifier.notifyChange(newMatch);
      }
    }
  }

  /**
   * Method to get the live matches.
   *
   * @return A map of live matches
   */
  public Map<String, Match> getLiveMatches() { return ongoingMatches; }

  /**
   * Method to check if a match has changed and update the match details.
   *
   * @param newMatch  The new match details
   * @param oldMatch  The old match details
   * @return A boolean indicating if the match has changed
   */
  private boolean hasMatchChanged(Match newMatch, Match oldMatch) {
    boolean changed = false;
    StringBuilder changeMessage = new StringBuilder();

    // Compare local team goals
    if (newMatch.getHomeGoals() != oldMatch.getHomeGoals()) {
      changeMessage.append("El equipo local ").append(newMatch.getHomeTeam())
              .append(" ha marcado gol en el minuto ").append(newMatch.getTime()).append("\n");
      changed = true;
    }
    else if (newMatch.getAwayGoals() != oldMatch.getAwayGoals()) {  // Compare away team goals
      changeMessage.append("El equipo visitante ").append(newMatch.getAwayTeam())
              .append(" ha marcado gol en el minuto ").append(newMatch.getTime()).append("\n");
      changed = true;
    }
    else if (newMatch.getTime() == 45 && oldMatch.getTime() != 45) { // Check if first half has ended
      changeMessage.append("Finaliza el primer tiempo entre ")
              .append(newMatch.getHomeTeam()).append(" y ").append(newMatch.getAwayTeam())
              .append("\n");
      changed = true;
    }
    else if (newMatch.isFinished() && !oldMatch.isFinished()) { // Check if match has ended
      changeMessage.append("El partido entre ").append(newMatch.getHomeTeam())
              .append(" y ").append(newMatch.getAwayTeam()).append(" ha finalizado.\n");
      changed = true;
    }
    else if (newMatch.getTime() == 46 && oldMatch.getTime() == 45) { // Check if second half has started
      changeMessage.append("Comienza el segundo tiempo entre ")
              .append(newMatch.getHomeTeam()).append(" y ").append(newMatch.getAwayTeam())
              .append("\n");
      changed = true;
    } else if (newMatch.getTime() == 1) { // Check if match has started
      changeMessage.append("Comienza el partido entre ")
              .append(newMatch.getHomeTeam()).append(" y ").append(newMatch.getAwayTeam())
              .append("\n");
      changed = true;
    }

    // If match has changed, update the change message
    if (changed) { newMatch.setChangeMessage(changeMessage.toString()); }

    return changed;
  }
}