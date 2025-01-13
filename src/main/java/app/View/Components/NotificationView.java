package app.View.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * NotificationView class.
 */
public class NotificationView {
  private final JPanel panel;
  private final JTextArea notificationArea;
  private final JButton backButton;

  /**
   * Constructor method.
   */
  public NotificationView() {
    panel = new JPanel();
    panel.setLayout(new BorderLayout(10, 10));

    JLabel titleLabel = new JLabel("Notificaciones en Directo", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

    notificationArea = new JTextArea();
    notificationArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(notificationArea);

    backButton = new JButton("Cerrar");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(backButton);

    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Method to reset the notification area.
   */
  public void resetNotificationArea() { notificationArea.setText(""); }

  /**
   * Method to show the panel.
   */
  public void show() { panel.setVisible(true); }

  /**
   * Method to hide the panel.
   */
  public void hide() { panel.setVisible(false); }

  /**
   * Method to get the notification area.
   * @return JTextArea
   */
  public JTextArea getNotificationArea() { return notificationArea; }

  /**
   * Method to get the panel.
   * @return JPanel
   */
  public JPanel getPanel() { return panel; }

  /**
   * Method to add a back button listener.
   * @param listener ActionListener
   */
  public void addBackButtonListener(ActionListener listener) { backButton.addActionListener(listener); }
}
