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

public class LeagueController implements ControllerStrategy {
  private final LeagueView leagueView;
  private final FootballAPIService apiService;

  public LeagueController(LeagueView leagueView) {
    this.leagueView = leagueView;
    this.apiService = new FootballAPIService();
  }

  @Override
  public void execute() {
    // Mostrar la vista de ligas
    leagueView.show();

    // Obtener las ligas desde la API
    List<League> leagues = apiService.getLeagues();
    leagueView.getLeagueListModel().clear();
    leagues.forEach(league -> leagueView.getLeagueListModel().addElement(league.getName()));

    // Configurar el botón "Ver Liga"
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

    // Configurar el botón "Cerrar"
    leagueView.addCloseButtonListener(e -> leagueView.hide());
  }

  private void showTeamsAndStats(League selectedLeague) {
    // Obtener los equipos de la liga seleccionada
    List<Team> teams = apiService.getTeamFromLeague(selectedLeague.getId(), 2022); // Ejemplo con temporada 2022
    apiService.getTeamsStatistics(teams, selectedLeague.getId(), 2022); // Obtener las estadísticas de los equipos

    leagueView.showStats(teams);

    leagueView.addBackButtonListener(e -> {
      leagueView.clear();
      execute();
    });
  }
}
