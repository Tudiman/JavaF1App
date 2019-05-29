package F1BasePack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionLeaderboardEntry extends LeaderboardEntry{

    private String performance;

    public SessionLeaderboardEntry(Object entrant, String performance) {
        super(entrant);
        this.performance = performance;
    }

    public String getPerformance() {
        return performance;
    }

    @Override
    public String toString() {
        return getEntrant() + ": " + performance;
    }

    public void setDelay(int place) {

        String regex = "(\\d(\\.\\d+)?)(.*)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(performance);
        m.find();
        double aux = Double.parseDouble(m.group(1));

        aux -= (place - 1)*0.05;

        DecimalFormat formatter = new DecimalFormat("#.#####");

        performance = formatter.format(aux) + m.group(3);
    }

    public static SessionLeaderboardEntry parseEntry(String line) {

        Pattern p = Pattern.compile("([a-zA-Z\\- ]+): ([a-zA-Z0-9.,\\- ]+)");
        Matcher m = p.matcher(line);
        m.find();
        for(Driver driver: Driver.getCurrentList()) {
            if(m.group(1).equals(driver.getName())) {
                return new SessionLeaderboardEntry(Driver.parseDriver(m.group(1),Driver.getCurrentList()),
                        m.group(2));
            }
        }
        throw new Error("Entry not found");
    }
}
