package app.View.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * LiveMatchesView class
 */
public class LiveMatchesView {
  private final JPanel panel;
  private final DefaultTableModel tableModel;
  private final JTable matchesTable;
  private final JButton backButton;

  /**
   * Constructor method
   */
  public LiveMatchesView() {
    panel = new JPanel();
    panel.setLayout(new BorderLayout(10, 10));

    // Header
    JLabel titleLabel = new JLabel("Partidos en Directo", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    panel.add(titleLabel, BorderLayout.NORTH);

    // Create the table with the matches
    String[] columnNames = {"Equipo Local", "Goles Local", "Tiempo Juego", "Goles Visitante", "Equipo Visitante"};
    tableModel = new DefaultTableModel(columnNames, 0);
    matchesTable = new JTable(tableModel);

    // set table properties
    matchesTable.setDefaultEditor(Object.class, null);
    matchesTable.getTableHeader().setReorderingAllowed(false);
    matchesTable.setAutoCreateRowSorter(true);
    matchesTable.setShowGrid(true);
    matchesTable.setGridColor(Color.BLACK);
    matchesTable.setRowHeight(30);
    matchesTable.setFont(new Font("Arial", Font.PLAIN, 14));

    JTableHeader header = matchesTable.getTableHeader();
    header.setBackground(new Color(220, 220, 220));
    header.setFont(new Font("Arial", Font.BOLD, 14));
    header.setPreferredSize(new Dimension(0, 40));

    for (int i = 0; i < matchesTable.getColumnCount(); i++) {
      matchesTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          setHorizontalAlignment(SwingConstants.CENTER);
          if (column == 2 && value != null) {
            setText(value + " ''");
          }
          return c;
        }
      });
    }

    JScrollPane scrollPane = new JScrollPane(matchesTable);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Back button
    backButton = new JButton("Cerrar");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(backButton);
    panel.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Update the matches table
   *
   * @param matches List of matches
   */
  public void updateMatches(List<Object[]> matches) {
    tableModel.setRowCount(0); // Clear the table
    for (Object[] row : matches) {
      tableModel.addRow(row);
    }
  }

  /**
   * Show the view
   */
  public void show() { panel.setVisible(true); }

  /**
   * Hide the view
   */
  public void hide() { panel.setVisible(false); }

  /**
   * Get the panel
   * @return
   */
  public JPanel getPanel() { return panel; }

  /**
   * Add a back button listener
   * @param listener
   */
  public void addBackButtonListener(ActionListener listener) { backButton.addActionListener(listener); }
}
