package app.Model;

/**
 * Subject interface for the Observer pattern.
 */
public interface Subject {
  void addObserver(UserSubscriber observer); // Add an observer
  void removeObserver(UserSubscriber observer); // Remove an observer
  void notifyChange(Match match); // Notify all observers of a change
}