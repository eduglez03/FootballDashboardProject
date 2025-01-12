package app.View.Components;

import app.Model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class LeagueView {
  private final JPanel panel;
  private final JList<String> leagueList;
  private final JButton viewLeagueButton;
  private final JButton closeButton; // Cambiado de backButton a closeButton
  private  JButton backButton;
  private final DefaultListModel<String> leagueListModel;

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

  public void show() {
    panel.setVisible(true);
  }

  public void hide() {
    panel.setVisible(false);
  }

  public void clear() {
    leagueListModel.clear(); // Limpia la lista de ligas
    leagueList.clearSelection(); // Elimina cualquier selección previa
    panel.removeAll(); // Limpia los componentes del panel
    setupUI(); // Reconfigura la interfaz inicial
    panel.revalidate(); // Revalida el panel para reflejar los cambios
    panel.repaint(); // Repinta el panel
  }

  private void setupUI() {
    // Configura nuevamente los componentes iniciales
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

  public JList<String> getLeagueList() {
    return leagueList;
  }

  public JPanel getPanel() {
    return panel;
  }

  public DefaultListModel<String> getLeagueListModel() {
    return leagueListModel;
  }

  public void addCloseButtonListener(ActionListener listener) {
    closeButton.addActionListener(listener);
  }

  public void addViewLeagueButtonListener(ActionListener listener) {
    viewLeagueButton.addActionListener(listener);
  }

  public void showStats(List<Team> teams) {
    // Crear el modelo de la tabla
    String[] columnNames = {"Equipo", "Goles a Favor", "Goles en Contra", "Victorias", "Empates", "Derrotas"};
    Object[][] data = new Object[teams.size()][6];

    // Llenar los datos de la tabla con la información de los equipos
    for (int i = 0; i < teams.size(); i++) {
      Team team = teams.get(i);
      data[i][0] = team.getName();              // Nombre del equipo
      data[i][1] = team.getGoalsFor();          // Goles a favor
      data[i][2] = team.getGoalsAgainst();      // Goles en contra
      data[i][3] = team.getWins();              // Victorias
      data[i][4] = team.getDraws();             // Empates
      data[i][5] = team.getLosses();            // Derrotas
    }

    // Crear la tabla
    JTable teamsTable = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(teamsTable);

    // Crear el botón "Volver"
    backButton = new JButton("Volver");

    // Agregar la tabla y el botón "Volver" a la vista
    JPanel statsPanel = new JPanel(new BorderLayout());
    statsPanel.add(scrollPane, BorderLayout.CENTER);
    statsPanel.add(backButton, BorderLayout.SOUTH);

    panel.removeAll();
    panel.add(statsPanel, BorderLayout.CENTER);
    panel.revalidate();
    panel.repaint();
  }

  public void addBackButtonListener(ActionListener listener) {
    backButton.addActionListener(listener);
  }
}
