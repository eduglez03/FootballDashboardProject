package app.Controller.Strategies;

import app.Controller.ControllerStrategy;
import app.Model.League;
import app.Model.FootballAPIService;
import app.Model.Team;
import app.View.Components.LeagueView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * LeagueController Class.
 */
public class LeagueController implements ControllerStrategy {
  private final LeagueView leagueView; // LeagueView
  private final FootballAPIService apiService; // FootballAPIService

    /**
     * Constructor.
     *
     * @param leagueView LeagueView
     */
  public LeagueController(LeagueView leagueView) {
    this.leagueView = leagueView;
    this.apiService = new FootballAPIService();
  }

  /**
   * Execute the strategy.
   */
  @Override
  public void execute() {
    leagueView.show(); // Show the LeagueView

    // Get the leagues from the API
    List<League> leagues = apiService.getLeagues();
    leagueView.getLeagueListModel().clear();
    leagues.forEach(league -> leagueView.getLeagueListModel().addElement(league.getName()));

    // Set the "Ver equipos y estadísticas" button
    leagueView.addViewLeagueButtonListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedIndex = leagueView.getLeagueList().getSelectedIndex();
        if (selectedIndex != -1) {
          League selectedLeague = leagues.get(selectedIndex);
          showTeamsAndStats(selectedLeague);
        } else {
          JOptionPane.showMessageDialog(leagueView.getPanel(), "Por favor, selecciona una liga.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    // Set the "Cerrar" button
    leagueView.addCloseButtonListener(e -> leagueView.hide());
  }

  /**
   * Show the teams and stats of the selected league.
   *
   * @param selectedLeague League
   */
  private void showTeamsAndStats(League selectedLeague) {
    // Get the teams from the selected league
    List<Team> teams = apiService.getTeamFromLeague(selectedLeague.getId(), 2022); // Using 2022 as the season parameter
    apiService.getTeamsStatistics(teams, selectedLeague.getId(), 2022); // Get the statistics of the teams

    leagueView.showStats(teams);

    leagueView.addBackButtonListener(e -> {
      leagueView.clear();
      execute();
    });
  }
}
