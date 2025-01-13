package app.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * League class represents a sports league.
 */
public class League {
  private int id; // ID
  private String name; // Name of the league
  private List<Integer> teamIds; // List of team IDs
  private List<Team> teams; // List of teams

  /**
   * Constructor
   * @param id
   * @param name
   */
  public League(int id, String name) {
    this.id = id;
    this.name = name;
    this.teamIds = new ArrayList<>();
    this.teams = new ArrayList<>();
  }

  /**
   * Getter ID
   * @return
   */
  public int getId() { return id; }

  /**
   * Getter Name
   * @return
   */
  public String getName() { return name; }

  /**
   * Setter Name
   * @param name
   */
  public List<Integer> getTeamIds() { return teamIds; }

  /**
   * Getter Teams
   * @return
   */
  public List<Team> getTeams() { return teams; }

  /**
   * Add team ID
   * @param teamId
   */
  public void addTeamId(int teamId) {
    if (!teamIds.contains(teamId)) { teamIds.add(teamId); }
  }

  /**
   * Add team
   * @param team
   */
  public void addTeam(Team team) {
    if (!teams.contains(team)) {
      teams.add(team);
      addTeamId(team.getId()); // Add team ID
    }
  }

  /**
   * Get team by ID
   * @param teamId
   * @return
   */
  public Team getTeamById(int teamId) {
    return teams.stream()
            .filter(team -> team.getId() == teamId)
            .findFirst()
            .orElse(null);
  }

  /**
   * To String method
   * @return
   */
  @Override
  public String toString() {
    return "League{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", teamIds=" + teamIds +
            ", teams=" + teams +
            '}';
  }
}