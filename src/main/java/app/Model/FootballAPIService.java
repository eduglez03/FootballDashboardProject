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
 *
 * API-Key:
 *   186dc55fb4942acfd2b06fbf59e11995
 *   33986fdfb4636b754f270318bd02c88d
 *   760bb5c7255c71ca019b9aa3b2ed7cb2
 *   dfed2726a7407b4ae16e67c5ae27c435
 */
public class FootballAPIService implements APIService {
  private static final String API_URL = "https://v3.football.api-sports.io/fixtures?live=all"; // API URL
  private static final String API_KEY = "dfed2726a7407b4ae16e67c5ae27c435"; // API Key
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

  /**
   * Fetches the lineups for a specific match.
   *
   * @param matchId The ID of the match.
   * @return A map containing the lineups of both teams.
   */
  public Map<String, List<String>> fetchLineupForMatch(String matchId) {
    try {
      // URL para obtener las alineaciones de un partido
      String lineupUrl = "https://v3.football.api-sports.io/fixtures/lineups?fixture=" + matchId;

      URL url = new URL(lineupUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("x-rapidapi-key", API_KEY);
      connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
      connection.setRequestMethod("GET");

      int responseCode = connection.getResponseCode();
      if (responseCode != 200) {
        System.out.println("Error al obtener las alineaciones. Código de error: " + responseCode);
        return Map.of(); // Return empty map if there's an error
      }

      Scanner scanner = new Scanner(connection.getInputStream());
      StringBuilder result = new StringBuilder();
      while (scanner.hasNext()) {
        result.append(scanner.nextLine());
      }
      scanner.close();

      // Verificar el contenido de la respuesta
      System.out.println("Respuesta de la API: " + result.toString());

      // Parsear el JSON para obtener las alineaciones
      return parseLineup(result.toString());
    } catch (IOException e) {
      e.printStackTrace();
      return Map.of(); // Return empty map if there's an error
    }
  }

  /**
   * Parses the JSON response to extract the lineups for both teams.
   *
   * @param json The JSON response containing the lineups.
   * @return A map containing the lineups for both teams.
   */
  private Map<String, List<String>> parseLineup(String json) {
    JsonObject response = JsonParser.parseString(json).getAsJsonObject();
    JsonArray lineups = response.getAsJsonArray("response");

    Map<String, List<String>> teamLineups = new HashMap<>();

    // Iterar sobre las alineaciones y extraer los jugadores para cada equipo
    for (JsonElement lineupElement : lineups) {
      JsonObject lineup = lineupElement.getAsJsonObject();
      JsonObject team = lineup.getAsJsonObject("team");

      // Crear listas para las alineaciones de los equipos
      List<String> homeTeamLineup = new ArrayList<>();
      List<String> awayTeamLineup = new ArrayList<>();

      // Alineación del equipo local
      JsonArray homePlayers = lineup.getAsJsonArray("startXI");  // Corregido para acceder a la alineación
      for (JsonElement playerElement : homePlayers) {
        JsonObject player = playerElement.getAsJsonObject();
        JsonObject playerDetails = player.getAsJsonObject("player");

        String playerName = playerDetails.get("name").getAsString();
        int playerNumber = playerDetails.get("number").getAsInt();
        String playerPos = playerDetails.get("pos").getAsString();

        // Añadir el jugador con su nombre, número de dorsal y posición
        homeTeamLineup.add(String.format("%s (Dorsal: %d, Posición: %s)", playerName, playerNumber, playerPos));
      }

      // Alineación del equipo visitante
      JsonArray awayPlayers = lineup.getAsJsonArray("startXI");  // Corregido para acceder a la alineación
      for (JsonElement playerElement : awayPlayers) {
        JsonObject player = playerElement.getAsJsonObject();
        JsonObject playerDetails = player.getAsJsonObject("player");

        String playerName = playerDetails.get("name").getAsString();
        int playerNumber = playerDetails.get("number").getAsInt();
        String playerPos = playerDetails.get("pos").getAsString();

        // Añadir el jugador con su nombre, número de dorsal y posición
        awayTeamLineup.add(String.format("%s (Dorsal: %d, Posición: %s)", playerName, playerNumber, playerPos));
      }

      // Guardar las alineaciones de ambos equipos
      teamLineups.put("home", homeTeamLineup);
      teamLineups.put("away", awayTeamLineup);
    }

    return teamLineups;
  }


  /**
   * Obtiene todas las ligas disponibles desde la API.
   *
   * @return Una lista de objetos League.
   */
  public List<League> getLeagues() {
    try {
      // URL para obtener las ligas
      String leaguesUrl = "https://v3.football.api-sports.io/leagues";

      // Configurar conexión
      URL url = new URL(leaguesUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("x-rapidapi-key", API_KEY);
      connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
      connection.setRequestMethod("GET");

      // Comprobar el código de respuesta
      int responseCode = connection.getResponseCode();
      if (responseCode != 200) {
        System.out.println("Error al obtener las ligas. Código de error: " + responseCode);
        return List.of(); // Retornar lista vacía si hay un error
      }

      // Leer la respuesta
      Scanner scanner = new Scanner(connection.getInputStream());
      StringBuilder result = new StringBuilder();
      while (scanner.hasNext()) {
        result.append(scanner.nextLine());
      }
      scanner.close();

      // Procesar y retornar las ligas
      return parseLeagues(result.toString());
    } catch (IOException e) {
      e.printStackTrace();
      return List.of(); // Retornar lista vacía si hay un error
    }
  }

  /**
   * Procesa la respuesta JSON para extraer las ligas.
   *
   * @param jsonResponse La respuesta JSON de la API.
   * @return Una lista de objetos League.
   */
  private List<League> parseLeagues(String jsonResponse) {
    List<League> leagues = new ArrayList<>();
    try {
      JSONObject jsonObject = new JSONObject(jsonResponse);
      JSONArray leaguesArray = jsonObject.getJSONArray("response");

      for (int i = 0; i < leaguesArray.length(); i++) {
        JSONObject leagueJson = leaguesArray.getJSONObject(i).getJSONObject("league");
        int id = leagueJson.getInt("id");
        String name = leagueJson.getString("name");

        // Crear objeto League y agregarlo a la lista
        League league = new League(id, name);
        leagues.add(league);
      }
    } catch (Exception e) {
      System.err.println("Error al procesar las ligas: " + e.getMessage());
      e.printStackTrace();
    }
    return leagues;
  }

  // Método para obtener los equipos de una liga
  public List<Team> getTeamFromLeague(int leagueId, int season) {
    try {
      // Construir la URL para obtener los equipos de la liga
      String apiUrl = "https://v3.football.api-sports.io/teams?league=" + leagueId + "&season=" + season;
      URL url = new URL(apiUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
      connection.setRequestProperty("x-rapidapi-key", API_KEY);
      connection.setRequestMethod("GET");

      // Comprobar el código de respuesta
      int responseCode = connection.getResponseCode();

      // Verificar si la respuesta es correcta (código 200)
      if (responseCode != 200) {
        System.out.println("Error al obtener los equipos. Código de error: " + responseCode);
        return List.of(); // Retornar lista vacía si hay un error
      }

      // Leer la respuesta
      Scanner scanner = new Scanner(connection.getInputStream());
      StringBuilder result = new StringBuilder();
      while (scanner.hasNext()) {
        result.append(scanner.nextLine());
      }
      scanner.close();

      // Imprimir la respuesta para depuración
      System.out.println("Respuesta de la API: " + result);

      // Procesar y retornar los equipos
      return parseTeams(result.toString());
    } catch (Exception e) {
      System.out.println("Excepción al obtener los equipos: " + e.getMessage());
      e.printStackTrace();
      return List.of(); // Retornar lista vacía si hay un error
    }
  }


  // Método para procesar la respuesta JSON y obtener los equipos
  private List<Team> parseTeams(String jsonResponse) {
    List<Team> teams = new ArrayList<>();
    try {
      JsonObject response = JsonParser.parseString(jsonResponse).getAsJsonObject();
      JsonArray teamsArray = response.getAsJsonArray("response"); // Obtener el array de equipos

      // Iterar sobre cada elemento del array y crear los objetos Team
      for (JsonElement element : teamsArray) {
        JsonObject teamObject = element.getAsJsonObject();
        JsonObject teamData = teamObject.getAsJsonObject("team");

        // Obtener los datos del equipo
        int id = teamData.get("id").getAsInt();
        String name = teamData.get("name").getAsString();

        // Crear un objeto Team y agregarlo a la lista
        Team team = new Team(id, name);
        teams.add(team);
      }
    } catch (Exception e) {
      System.err.println("Error al procesar los equipos: " + e.getMessage());
    }
    return teams;
  }

  public void getTeamsStatistics(List<Team> teams, int leagueId, int season) {
    for (Team team : teams) {
      try {
        // Construir la URL para obtener las estadísticas del equipo
        String urlString = String.format(
                "https://v3.football.api-sports.io/teams/statistics?season=%d&team=%d&league=%d",
                season, team.getId(), leagueId
        );
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
        connection.setRequestProperty("x-rapidapi-key", API_KEY);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
          try (Scanner scanner = new Scanner(connection.getInputStream())) {
            StringBuilder result = new StringBuilder();
            while (scanner.hasNext()) {
              result.append(scanner.nextLine());
            }

            // Imprimir la respuesta completa para depuración
            System.out.println("Respuesta JSON para el equipo " + team.getName() + ": " + result);

            // Procesar la respuesta JSON y extraer las estadísticas
            JsonObject jsonResponse = JsonParser.parseString(result.toString()).getAsJsonObject();
            JsonObject response = jsonResponse.getAsJsonObject("response");

            if (response != null) {
              JsonObject fixtures = response.getAsJsonObject("fixtures");
              if (fixtures != null) {
                // Extraer estadísticas del equipo
                int wins = fixtures.getAsJsonObject("wins").get("total").getAsInt();
                int draws = fixtures.getAsJsonObject("draws").get("total").getAsInt();
                int losses = fixtures.getAsJsonObject("loses").get("total").getAsInt();
                int goalsFor = response.getAsJsonObject("goals").getAsJsonObject("for").getAsJsonObject("total").get("total").getAsInt();
                int goalsAgainst = response.getAsJsonObject("goals").getAsJsonObject("against").getAsJsonObject("total").get("total").getAsInt();

                // Asignar las estadísticas al equipo
                team.setWins(wins);
                team.setDraws(draws);
                team.setLosses(losses);
                team.setGoalsFor(goalsFor);
                team.setGoalsAgainst(goalsAgainst);
              } else {
                System.out.println("No se encontraron estadísticas de fixtures para el equipo " + team.getName());
              }
            } else {
              System.out.println("No se encontraron estadísticas para el equipo " + team.getName());
            }
          }
        } else {
          System.out.println("Error al obtener estadísticas para el equipo " + team.getId() + ": " + responseCode);
        }
      } catch (IOException e) {
        System.out.println("Error de conexión para el equipo " + team.getId() + ": " + e.getMessage());
        e.printStackTrace();
      } catch (Exception e) {
        System.out.println("Error al procesar los datos para el equipo " + team.getId());
        e.printStackTrace();
      }
    }
  }
}