package app.View.Components;

import app.Model.Match;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * LineUpView class
 */
public class LineUpView {
  private JList<String> matchList;
  private DefaultListModel<String> matchListModel;
  private JButton viewLineupButton;
  private JButton closeButton;
  private JPanel panel;
  private Map<String, Match> liveMatches;

  /**
   * Constructor
   */
  public LineUpView() {
    panel = new JPanel();
    panel.setLayout(new BorderLayout(10, 10));

    // Header
    JLabel titleLabel = new JLabel("Alineaciones", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    panel.add(titleLabel, BorderLayout.NORTH);

    matchListModel = new DefaultListModel<>();
    matchList = new JList<>(matchListModel);
    matchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JScrollPane scrollPane = new JScrollPane(matchList);
    viewLineupButton = new JButton("Ver Alineaciones");
    closeButton = new JButton("Cerrar");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(viewLineupButton);
    buttonPanel.add(closeButton);

    closeButton.addActionListener(e -> {
      setVisible(false);  // Hide the view
    });

    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Getter for panel
   * @return
   */
  public JPanel getPanel() { return panel; }

  /**
   * Setter for visibility
   * @param visible
   */
  public void setVisible(boolean visible) { panel.setVisible(visible); }

  /**
   * Setter for match list
   * @param liveMatches
   */
  public void setMatchList(Map<String, Match> liveMatches) {
    this.liveMatches = liveMatches; // Store the live matches
    matchListModel.clear();
    liveMatches.forEach((id, match) -> matchListModel.addElement(match.getHomeTeam() + " vs " + match.getAwayTeam()));
  }

  /**
   * Getter for selected match index
   * @return
   */
  public int getSelectedMatchIndex() { return matchList.getSelectedIndex(); }

  /**
   * Getter for view lineup button
   * @return
   */
  public JButton getViewLineupButton() { return viewLineupButton; }

  /**
   * Getter for back button
   * @return
   */
  public JButton getBackButton() { return closeButton; }

  /**
   * Clear the view
   */
  public void clear() {
    matchListModel.clear(); // Clear the match list
    matchList.clearSelection(); // Delete the selection
    panel.removeAll(); // Remove all components from the panel
    setupUI(); // Set up the initial UI
    panel.revalidate(); // Revalidates the panel
    panel.repaint(); // Repaints the panel
  }

  /**
   * Set up the initial UI
   */
  private void setupUI() {
    // Set up the initial UI
    panel.setLayout(new BorderLayout(10, 10));

    JLabel titleLabel = new JLabel("Alineaciones", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    panel.add(titleLabel, BorderLayout.NORTH);

    JScrollPane scrollPane = new JScrollPane(matchList);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(viewLineupButton);
    buttonPanel.add(closeButton);

    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Show lineups
   * @param match
   */
  public void showLineups(Match match) {
    match.getLineups();
    JTextArea lineupArea = new JTextArea();
    lineupArea.setEditable(false);

    StringBuilder lineupText = new StringBuilder();
    lineupText.append("Alineaci�n del equipo local (" + match.getHomeTeam() + "):\n");
    for (String player : match.getHomeTeamLineup()) {
      lineupText.append(" - " + player + "\n");
    }

    lineupText.append("\nAlineaci�n del equipo visitante (" + match.getAwayTeam() + "):\n");
    for (String player : match.getAwayTeamLineup()) {
      lineupText.append(" - " + player + "\n");
    }

    lineupArea.setText(lineupText.toString());

    JScrollPane scrollPane = new JScrollPane(lineupArea);
    JButton backButton = new JButton("Volver");
    backButton.addActionListener(e -> {
      // back button action
      clear();
      setMatchList(liveMatches);
      setVisible(true);
    });

    JPanel lineUpPanel = new JPanel(new BorderLayout());
    lineUpPanel.add(scrollPane, BorderLayout.CENTER);
    lineUpPanel.add(backButton, BorderLayout.SOUTH);

    panel.removeAll();
    panel.add(lineUpPanel, BorderLayout.CENTER);
    panel.revalidate();
    panel.repaint();
  }
}