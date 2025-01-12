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

    @Override
    public void execute() {
        System.out.println("Fetching live matches and displaying lineups...");

        // Inicializa los servicios relacionados con la API
        this.liveMatches = matchUpdater.getLiveMatches();

        // Limpia y muestra la vista inicial
        lineupView.clear();
        lineupView.setMatchList(liveMatches); // Pasar los partidos a la vista
        lineupView.setVisible(true);

        // Configurar acción para ver las alineaciones del partido seleccionado
        lineupView.getViewLineupButton().addActionListener(e -> showSelectedMatchLineups());

        // Reiniciar la funcionalidad al pulsar "Volver"
        lineupView.getBackButton().addActionListener(e -> {
            lineupView.clear(); // Limpia el estado actual de la vista
            execute(); // Reinicia el proceso desde el principio
        });
    }

    private void showSelectedMatchLineups() {
        int selectedIndex = lineupView.getSelectedMatchIndex();
        if (selectedIndex != -1) {
            Match selectedMatch = (Match) liveMatches.values().toArray()[selectedIndex];
            lineupView.showLineups(selectedMatch); // Usar el método de la vista para mostrar alineaciones
        } else {
            JOptionPane.showMessageDialog(lineupView.getPanel(), "Por favor, selecciona un partido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}