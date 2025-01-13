package app.Controller.Strategies;

import app.Controller.ControllerStrategy;
import app.Model.FootballAPIService;
import app.Model.Match;
import app.Model.MatchNotifier;
import app.Model.MatchUpdater;
import app.View.Components.LineUpView;
import javax.swing.*;
import java.util.Map;

/**
 * LineUpController
 */
public class LineUpController implements ControllerStrategy {
    private LineUpView lineupView; // LineUpView
    private Map<String, Match> liveMatches; // Live matches
    private FootballAPIService footballAPIService = new FootballAPIService(); // API service
    private MatchNotifier matchNotifier = new MatchNotifier(); // Match notifier
    private MatchUpdater matchUpdater = new MatchUpdater(footballAPIService, matchNotifier); // Match updater

    /**
     * Constructor
     * @param lineupView LineUpView
     */
    public LineUpController(LineUpView lineupView) { this.lineupView = lineupView; }

    /**
     * Execute
     */
    @Override
    public void execute() {
        System.out.println("Fetching live matches and displaying lineups...");

        // Set the live matches
        this.liveMatches = matchUpdater.getLiveMatches();

        lineupView.clear();
        lineupView.setMatchList(liveMatches);
        lineupView.setVisible(true);

        // Set the action listeners for the buttons in the view
        lineupView.getViewLineupButton().addActionListener(e -> showSelectedMatchLineups());

        // Reset the view
        lineupView.getBackButton().addActionListener(e -> {
            lineupView.clear();
            execute(); // Re-execute the controller
        });
    }

    /**
     * Show selected match lineups
     */
    private void showSelectedMatchLineups() {
        int selectedIndex = lineupView.getSelectedMatchIndex();
        if (selectedIndex != -1) {
            Match selectedMatch = (Match) liveMatches.values().toArray()[selectedIndex];
            lineupView.showLineups(selectedMatch); // Update the view with the lineups
        } else {
            JOptionPane.showMessageDialog(lineupView.getPanel(), "Por favor, selecciona un partido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}