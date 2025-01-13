package app.View.Components;

import app.Model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * LeagueView is a class that represents the view of the league statistics.
 */
public class LeagueView {
  private final JPanel panel;
  private final JList<String> leagueList;
  private final JButton viewLeagueButton;
  private final JButton closeButton;
  private  JButton backButton;
  private final DefaultListModel<String> leagueListModel;

  /**
   * Constructor method of the LeagueView class.
   */
  public LeagueView() {
    panel = new JPanel();
    panel.setLayout(new BorderLayout(10, 10));

    JLabel titleLabel = new JLabel("Estadísticas de Liga", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

    leagueListModel = new DefaultListModel<>();
    leagueList = new JList<>(leagueListModel);
    leagueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JScrollPane scrollPane = new JScrollPane(leagueList);

    viewLeagueButton = new JButton("Ver Liga");
    closeButton = new JButton("Cerrar"); // Actualizado

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(viewLeagueButton);
    buttonPanel.add(closeButton);

    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Method to show the view.
   */
  public void show() { panel.setVisible(true); }

  /**
   * Method to hide the view.
   */
  public void hide() { panel.setVisible(false); }

  /**
   * Method to clear the view. It clears the list of leagues, removes any previous selection, and clears the components of the panel.
   */
  public void clear() {
    leagueListModel.clear(); // Clear the list of leagues
    leagueList.clearSelection(); // Desselect any previous selection
    panel.removeAll(); // Clear the panel
    setupUI(); // Re-setup the UI
    panel.revalidate(); // Revalidates the panel
    panel.repaint(); // Repaints the panel
  }

  /**
   * Method to set up the UI of the view.
   */
  private void setupUI() {
    // Set the layout of the panel
    panel.setLayout(new BorderLayout(10, 10));

    JLabel titleLabel = new JLabel("Estadísticas de Liga", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

    JScrollPane scrollPane = new JScrollPane(leagueList);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(viewLeagueButton);
    buttonPanel.add(closeButton);

    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Get the list of leagues.
   * @return
   */
  public JList<String> getLeagueList() { return leagueList; }

  /**
   * Get the panel.
   * @return
   */
  public JPanel getPanel() { return panel; }

  /**
   * Get the list model of the leagues.
   * @return
   */
  public DefaultListModel<String> getLeagueListModel() { return leagueListModel; }

  /**
   * Add a listener to the close button.
   * @param listener
   */
  public void addCloseButtonListener(ActionListener listener) { closeButton.addActionListener(listener); }

  /**
   * Add a listener to the view league button.
   * @param listener
   */
  public void addViewLeagueButtonListener(ActionListener listener) { viewLeagueButton.addActionListener(listener); }

  /**
   * Method to show the statistics of the league.
   * @param teams
   */
  public void showStats(List<Team> teams) {
    // Create the column names and the data for the table
    String[] columnNames = {"Equipo", "Goles a Favor", "Goles en Contra", "Victorias", "Empates", "Derrotas"};
    Object[][] data = new Object[teams.size()][6];

    // Fill the data with the statistics of each team
    for (int i = 0; i < teams.size(); i++) {
      Team team = teams.get(i);
      data[i][0] = team.getName();              // Nombre del equipo
      data[i][1] = team.getGoalsFor();          // Goles a favor
      data[i][2] = team.getGoalsAgainst();      // Goles en contra
      data[i][3] = team.getWins();              // Victorias
      data[i][4] = team.getDraws();             // Empates
      data[i][5] = team.getLosses();            // Derrotas
    }

    // Create the table and the scroll pane
    JTable teamsTable = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(teamsTable);

    // Create the "Volver" button
    backButton = new JButton("Volver");

    // Add the table and the "Volver" button to the panel
    JPanel statsPanel = new JPanel(new BorderLayout());
    statsPanel.add(scrollPane, BorderLayout.CENTER);
    statsPanel.add(backButton, BorderLayout.SOUTH);

    panel.removeAll();
    panel.add(statsPanel, BorderLayout.CENTER);
    panel.revalidate();
    panel.repaint();
  }

  /**
   * Add a listener to the back button.
   * @param listener
   */
  public void addBackButtonListener(ActionListener listener) { backButton.addActionListener(listener); }
}
