package app.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to represent a football match
 */
public class Match {
  private String homeTeam; // Home team name
  private String awayTeam; // Away team name
  private int homeGoals; // Home team goals
  private int awayGoals; // Away team goals
  private String matchId; // Unique match identifier
  private boolean isFinished; // If the match is finished
  private String changeMessage; // Change message
  private int time; // Elapsed time
  private List<String> homeTeamLineup;
  private List<String> awayTeamLineup;

  /**
   * Constructor for the Match class
   *
   * @param homeTeam   Home team name
   * @param awayTeam   Away team name
   * @param homeGoals  Home team goals
   * @param awayGoals  Away team goals
   * @param matchId    Unique match identifier
   * @param time       Elapsed time
   * @param isFinished If the match is finished
   */
  public Match(String homeTeam, String awayTeam, int homeGoals, int awayGoals, String matchId, int time, boolean isFinished) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.homeGoals = homeGoals;
    this.awayGoals = awayGoals;
    this.matchId = matchId;
    this.isFinished = isFinished;
    this.changeMessage = "";
    this.time = time;
    this.homeTeamLineup = new ArrayList<>(); // Inicializa la lista
    this.awayTeamLineup = new ArrayList<>(); // Inicializa la lista
  }

  // Getters
  public boolean isFinished() {
    return isFinished;
  }

  public int getTime() {
    return time;
  }

  public String getHomeTeam() {
    return homeTeam;
  }

  public String getAwayTeam() {
    return awayTeam;
  }

  public int getHomeGoals() {
    return homeGoals;
  }

  public int getAwayGoals() {
    return awayGoals;
  }

  public String getMatchId() {
    return matchId;
  }

  public String getChangeMessage() {
    return changeMessage;
  }

  public List<String> getHomeTeamLineup() {
    return homeTeamLineup;
  }

  public List<String> getAwayTeamLineup() {
    return awayTeamLineup;
  }

  // Setters
  public void setChangeMessage(String changeMessage) {
    this.changeMessage = changeMessage;
  }

  /**
   * Method to return a string representation of the match
   *
   * @param homeGoals Home team goals
   * @param awayGoals Away team goals
   * @param time      Elapsed time
   */
  public String toString() {
    return String.format("%s %d - %d %s", homeTeam, homeGoals, awayGoals, awayTeam);
  }

  public void getLineups() {
    FootballAPIService apiService = new FootballAPIService();
    Map<String, List<String>> lineup = apiService.fetchLineupForMatch(matchId);

    if (lineup != null && lineup.containsKey("home") && lineup.containsKey("away")) {
      homeTeamLineup = lineup.get("home");
      awayTeamLineup = lineup.get("away");
    } else {
      // Maneja el caso en que las alineaciones no están disponibles
      System.out.println("No se pudieron obtener las alineaciones");
    }
  }
}