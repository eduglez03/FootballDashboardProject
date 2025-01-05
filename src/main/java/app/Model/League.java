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
