package app.Model;

import java.util.Map;
import javax.swing.JTextArea;

/**
 * Abstract class that represents a user subscriber to match notifications.
 */
public abstract class UserSubscriber implements Observer {
  private String username; // Username of the subscriber
  private Map<String, JTextArea> userNotificationAreas; // Map of notification areas for each user

  /**
   * Constructor that initializes the user subscriber with a username and a map of notification areas.
   * @param username Username of the subscriber
   * @param userNotificationAreas Map of notification areas for each user
   */
  public UserSubscriber(String username, Map<String, JTextArea> userNotificationAreas) {
    this.username = username;
    this.userNotificationAreas = userNotificationAreas;
  }

  /**
   * Method to update the user subscriber with a new match.
   * @param match Match to update the user subscriber with
   */
  @Override
  public void update(Match match) {
    // Get the notification area for the user
    JTextArea notificationArea = userNotificationAreas.get(username);
    if (notificationArea != null) {
      // Append the change message to the notification area
      notificationArea.append(match.getChangeMessage() + "\n");
    } else {
      System.err.println("No se encontró el área de notificaciones para el usuario: " + username);
    }
  }

  /**
   * Getter for the username of the user subscriber.
   * @return Username of the user subscriber
   */
  public String getUsername() { return username; }
}


