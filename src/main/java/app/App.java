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
    private FootballAPIService footballAPIService = new FootballAPIService(); // Simulación del servicio
    private MatchNotifier matchNotifier = new MatchNotifier(); // Simulación del servicio
    private MatchUpdater matchUpdater = new MatchUpdater(footballAPIService, matchNotifier); // Simulación del servicio

    public LineUpController(LineUpView lineupView) {
        this.lineupView = lineupView;
    }