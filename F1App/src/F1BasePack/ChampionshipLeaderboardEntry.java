package F1BasePack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChampionshipLeaderboardEntry extends LeaderboardEntry {

    private int points;

    public ChampionshipLeaderboardEntry(Object entrant, int points) {
        super(entrant);
        if(points < 0)
            throw new Error("bad points input to leaderboard entry, points can't be negative");
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return getEntrant() + ": " + points + " points";
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public static ChampionshipLeaderboardEntry parseEntry(String line, int type) throws Exception {

        Pattern p = Pattern.compile("([a-zA-Z\\-0-9 ]+): ([0-9]+)");
        Matcher m = p.matcher(line);
        m.find();

        if(type == 0) {
            for (Driver driver : Driver.getCurrentList()) {
                if (m.group(1).equals(driver.getName())) {
                    return new ChampionshipLeaderboardEntry(Driver.parseDriver(m.group(1), Driver.getCurrentList()),
                            Integer.parseInt(m.group(2)));
                }
            }
            throw new Exception("Entry not found");
        }
        else if(type == 1){
            for (Team team : Team.getCurrentList()) {
                if (m.group(1).equals(team.getName())) {
                    return new ChampionshipLeaderboardEntry(Team.parseTeam(m.group(1), Team.getCurrentList()),
                            Integer.parseInt(m.group(2)));
                }
            }
            throw new Exception("Entry not found");
        }
        else throw new Exception("Bad type parameter");
    }
}
