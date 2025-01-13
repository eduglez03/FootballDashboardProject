package app.Model;

/**
 * Team class
 */
public class Team {
  private int id; // Id
  private String name; // Name
  private int wins; // Wins
  private int draws; // Draws
  private int losses; // Losses
  private int goalsFor; // Goals for
  private int goalsAgainst; // Goals against

  /**
   * Constructor
   * @param id Id
   * @param name Name
   */
  public Team(int id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Getter id
   * @return
   */
  public int getId() { return id; }

  /**
   * Getter name
   * @return
   */
  public String getName() { return name; }

  /**
   * Getter wins
   * @return
   */
  public int getWins() { return wins; }

  /**
   * Setter wins
   * @param wins
   */
  public void setWins(int wins) { this.wins = wins; }

  /**
   * Getter draws
   * @return
   */
  public int getDraws() { return draws; }

  /**
   * Setter draws
   * @param draws
   */
  public void setDraws(int draws) { this.draws = draws; }

  /**
   * Getter losses
   * @return
   */
  public int getLosses() { return losses; }

  /**
   * Setter losses
   * @param losses
   */
  public void setLosses(int losses) { this.losses = losses; }

  /**
   * Getter goalsFor
   * @return
   */
  public int getGoalsFor() { return goalsFor; }

  /**
   * Setter goalsFor
   * @param goalsFor
   */
  public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }

  /**
   * Getter goalsAgainst
   * @return
   */
  public int getGoalsAgainst() { return goalsAgainst; }

  /**
   * Setter goalsAgainst
   * @param goalsAgainst
   */
  public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }

  /**
   * ToString method
   * @return
   */
  @Override
  public String toString() {
    return "Equipo: " + name + "\n" +
            "Partidos ganados: " + wins + "\n" +
            "Partidos empatados: " + draws + "\n" +
            "Partidos perdidos: " + losses + "\n" +
            "Goles a favor: " + goalsFor + "\n" +
            "Goles en contra: " + goalsAgainst + "\n";
  }
}
