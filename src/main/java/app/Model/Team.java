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
}
