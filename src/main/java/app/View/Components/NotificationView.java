package app.View.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NotificationView {
    private final JPanel panel;
    private final JTextArea notificationArea;
    private final JButton backButton;

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

    public void resetNotificationArea() { notificationArea.setText(""); }

    public void show() {
        panel.setVisible(true);
    }

    public void hide() {
        panel.setVisible(false);
    }

    public JTextArea getNotificationArea() {
        return notificationArea;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
