package app.View.Components;

import app.Model.Match;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LineUpView {
    private JList<String> matchList;
    private DefaultListModel<String> matchListModel;
    private JButton viewLineupButton;
    private JButton closeButton;
    private JPanel panel; // Este será el panel que se añadirá al MainView
    private Map<String, Match> liveMatches; // Agregado para almacenar los partidos

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
            setVisible(false);  // Oculta el panel
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setVisible(boolean visible) {
        panel.setVisible(visible);
    }

    // Método para establecer la lista de partidos
    public void setMatchList(Map<String, Match> liveMatches) {
        this.liveMatches = liveMatches; // Almacena los partidos en la vista
        matchListModel.clear();
        liveMatches.forEach((id, match) -> matchListModel.addElement(match.getHomeTeam() + " vs " + match.getAwayTeam()));
    }

    public int getSelectedMatchIndex() {
        return matchList.getSelectedIndex();
    }

    public JButton getViewLineupButton() {
        return viewLineupButton;
    }

    public JButton getBackButton() {
        return closeButton;
    }

    public void clear() {
        matchListModel.clear(); // Limpia la lista de partidos
        matchList.clearSelection(); // Elimina cualquier selección previa
        panel.removeAll(); // Limpia los componentes del panel
        setupUI(); // Reconfigura la interfaz inicial
        panel.revalidate(); // Revalida el panel para reflejar los cambios
        panel.repaint(); // Repinta el panel
    }

    private void setupUI() {
        // Configura nuevamente los componentes iniciales
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

    // Nuevo método para mostrar las alineaciones
    public void showLineups(Match match) {
        match.getLineups();
        JTextArea lineupArea = new JTextArea();
        lineupArea.setEditable(false);

        StringBuilder lineupText = new StringBuilder();
        lineupText.append("Alineación del equipo local (" + match.getHomeTeam() + "):\n");
        for (String player : match.getHomeTeamLineup()) {
            lineupText.append(" - " + player + "\n");
        }

        lineupText.append("\nAlineación del equipo visitante (" + match.getAwayTeam() + "):\n");
        for (String player : match.getAwayTeamLineup()) {
            lineupText.append(" - " + player + "\n");
        }

        lineupArea.setText(lineupText.toString());

        JScrollPane scrollPane = new JScrollPane(lineupArea);
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            // Volver a la vista anterior
            clear();
            setMatchList(liveMatches); // Aquí pasamos liveMatches de nuevo
            setVisible(true);
        });

        JPanel lineUpPanel = new JPanel(new BorderLayout());
        lineUpPanel.add(scrollPane, BorderLayout.CENTER);
        lineUpPanel.add(backButton, BorderLayout.SOUTH);

        panel.removeAll(); // Limpia el panel antes de agregar la nueva información
        panel.add(lineUpPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}