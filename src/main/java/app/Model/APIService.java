package app.Model;

import java.util.Map;

/**
 * APIService interface
 */
public interface APIService {
  Map<String, Match> fetchMatchResults(); // fetchMatchResults method
  Map<String, Match> parseMatchResults(String json); // parseMatchResults method
}