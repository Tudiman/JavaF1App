package F1BasePack;

import F1BasePack.Utility.*;

import java.util.ArrayList;

public class Session {

    private int type;
    private String weather = "sunny";
    private String timeOfDay = "noon";
    private ArrayList<Team> teams;
    private ArrayList<SessionLeaderboardEntry> leaderboard = new ArrayList<>();
    private ArrayList<String> notableEvents = new ArrayList<>();

    public Session(int type, ArrayList<Team> teams) {
        this.type = type;
        this.teams = teams;
    }

    public Session(int type, String weather, String timeOfDay, ArrayList<Team> teams) {
        this.type = type;
        this.weather = weather;
        this.timeOfDay = timeOfDay;
        this.teams = teams;
    }

    public Session(int type, String weather, String timeOfDay, ArrayList<Team> teams, ArrayList<SessionLeaderboardEntry> leaderboard) {
        this.type = type;
        this.weather = weather;
        this.timeOfDay = timeOfDay;
        this.teams = teams;
        this.leaderboard = leaderboard;
    }

    public int getType() {
        return type;
    }

    public String getWeather() {
        return weather;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<SessionLeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }

    public ArrayList<String> getNoteableEvents() {
        return notableEvents;
    }

    @Override
    public String toString() {
        String[] type = {"Practice session","Qualifying session","Race"};
        return type[this.type];
    }

    public void setLeaderboard(ArrayList<SessionLeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
