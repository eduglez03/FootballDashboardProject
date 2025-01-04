package app.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;

/**
 * Implementation of the APIService interface for fetching football match results from a public API.
 */
public class FootballAPIService implements APIService {
  private static final String API_URL = "https://v3.football.api-sports.io/fixtures?live=all"; // API URL
  private static final String API_KEY = "186dc55fb4942acfd2b06fbf59e11995"; // API Key
  private Map<String, Map<String, List<String>>> matchLineups = new HashMap<>(); // Store lineups by matchId

  /**
   * Fetches the ongoing football match results from the API.
   *
   * @return A map containing the match results.
   */
  @Override
  public Map<String, Match> fetchMatchResults() {
    try {
      URL url = new URL(API_URL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("x-rapidapi-key", API_KEY);
      connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
      connection.setRequestMethod("GET");

      // Check if the response code is 200 (OK)
      int responseCode = connection.getResponseCode();
      if (responseCode != 200) {
        System.out.println("Error al obtener los resultados. Código de error: " + responseCode);
        return new HashMap<>();
      }

      Scanner scanner = new Scanner(connection.getInputStream());
      StringBuilder result = new StringBuilder();
      while (scanner.hasNext()) {
        result.append(scanner.nextLine());
      }
      scanner.close();

      // Parse the JSON response to a map of match results
      return parseMatchResults(result.toString());
    } catch (IOException e) {
      e.printStackTrace();
      return new HashMap<>();
    }
  }

  /**
   * Parses the JSON response from the API to a map of match results.
   *
   * @param json The JSON response from the API.
   * @return A map containing the match results.
   */
  @Override
  public Map<String, Match> parseMatchResults(String json) {
    System.out.println("JSON Response: " + json);

    JsonObject response = JsonParser.parseString(json).getAsJsonObject();
    JsonArray fixtures = response.getAsJsonArray("response");

    Map<String, Match> ongoingMatches = new HashMap<>(); // Map to store the ongoing matches

    // Iterate over the fixtures and create a Match object for each ongoing match
    for (var fixtureElement : fixtures) {
      JsonObject fixture = fixtureElement.getAsJsonObject();
      JsonObject teams = fixture.getAsJsonObject("teams");
      JsonObject goals = fixture.getAsJsonObject("goals");
      JsonObject status = fixture.has("status") ? fixture.getAsJsonObject("status") : null;
      JsonObject fixtureStatus = fixture.getAsJsonObject("fixture").getAsJsonObject("status");
      JsonObject fixtureDetails = fixture.getAsJsonObject("fixture");

      String homeTeam = teams.has("home") && teams.getAsJsonObject("home").has("name")
              ? teams.getAsJsonObject("home").get("name").getAsString() : "Unknown"; // Get the home team name

      String awayTeam = teams.has("away") && teams.getAsJsonObject("away").has("name")
              ? teams.getAsJsonObject("away").get("name").getAsString() : "Unknown"; // Get the away team name

      String matchId = fixtureDetails.has("id") ? fixtureDetails.get("id").getAsString() : "Unknown"; // Get the match ID

      int timeElapsed = fixtureStatus.has("elapsed") && !fixtureStatus.get("elapsed").isJsonNull()
              ? fixtureStatus.get("elapsed").getAsInt()
              : 0; // Get the time elapsed in the match

      boolean isFinished = status != null && status.has("long") && status.get("long").getAsString().equals("Finished"); // Check if the match is finished

      int homeGoals = getGoals(goals, "home"); // Get the home team goals
      int awayGoals = getGoals(goals, "away"); // Get the away team goals

      Match match = new Match(homeTeam, awayTeam, homeGoals, awayGoals, matchId, timeElapsed, isFinished); // Create a Match object
      ongoingMatches.put(matchId, match); // Add the match to the map
    }
    return ongoingMatches;
  }

  /**
   * Gets the goals scored by a team from the JSON object.
   *
   * @param goals    The JSON object containing the goals.
   * @param teamType The type of team (home or away).
   * @return The number of goals scored by the team.
   */
  private int getGoals(JsonObject goals, String teamType) {
    try {
      if (goals.has(teamType) && !goals.get(teamType).isJsonNull()) {
        return goals.getAsJsonPrimitive(teamType).getAsInt();
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Error al procesar los goles para el equipo " + teamType);
      return 0;
    }
  }
}