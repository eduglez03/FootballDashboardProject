package app.Model;

public class Team {
    private int id;
    private String name;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

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
