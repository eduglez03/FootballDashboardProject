package app.Controller.Strategies;

import app.Controller.ControllerStrategy;
import app.Model.FootballAPIService;
import app.Model.Match;
import app.Model.MatchNotifier;
import app.Model.MatchUpdater;
import app.View.Components.LineUpView;
import javax.swing.*;
import java.util.Map;

public class LineUpController implements ControllerStrategy {
    private LineUpView lineupView;
    private Map<String, Match> liveMatches;
    private FootballAPIService footballAPIService = new FootballAPIService(); // Simulaci�n del servicio
    private MatchNotifier matchNotifier = new MatchNotifier(); // Simulaci�n del servicio
    private MatchUpdater matchUpdater = new MatchUpdater(footballAPIService, matchNotifier); // Simulaci�n del servicio

    public LineUpController(LineUpView lineupView) {
        this.lineupView = lineupView;
    }