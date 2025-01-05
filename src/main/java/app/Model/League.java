package app.Model;

import java.util.ArrayList;
import java.util.List;

public class League {
    private int id;
    private String name;
    private List<Integer> teamIds; // Lista de IDs de los equipos
    private List<Team> teams;      // Lista de objetos Team

    public League(int id, String name) {
        this.id = id;
        this.name = name;
        this.teamIds = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getTeamIds() {
        return teamIds;
    }

    public List<Team> getTeams() {
        return teams;
    }

    // Métodos para añadir equipos
    public void addTeamId(int teamId) {
        if (!teamIds.contains(teamId)) {
            teamIds.add(teamId);
        }
    }

    public void addTeam(Team team) {
        if (!teams.contains(team)) {
            teams.add(team);
            addTeamId(team.getId()); // Asegurar que el ID también se agrega
        }
    }

    // Métodos para buscar equipos
    public Team getTeamById(int teamId) {
        return teams.stream()
                .filter(team -> team.getId() == teamId)
                .findFirst()
                .orElse(null);
    }

    // Método para mostrar información de la liga
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