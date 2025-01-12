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