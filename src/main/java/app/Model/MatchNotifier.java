package app.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to notify changes to observers
 */
public class MatchNotifier implements Subject {
  private List<UserSubscriber> observers = new ArrayList<>(); // Observers list

  /**
   * Method to add observers
   * @param observer Observer to add
   */
  @Override
  public void addObserver(UserSubscriber observer) { observers.add(observer); }

  /**
   * Method to remove observers
   * @param observer Observer to remove
   */
  @Override
  public void removeObserver(UserSubscriber observer) { observers.remove(observer); }

  /**
   * Method to notify changes to observers
   * @param match Match to notify
   */
  @Override
  public void notifyChange(Match match) {
    for (UserSubscriber observer : observers) {
      observer.update(match);
    }
  }
}