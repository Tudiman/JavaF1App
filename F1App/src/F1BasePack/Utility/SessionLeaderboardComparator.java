package F1BasePack.Utility;

import F1BasePack.SessionLeaderboardEntry;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionLeaderboardComparator implements Comparator<SessionLeaderboardEntry> {

    public int compare(SessionLeaderboardEntry a, SessionLeaderboardEntry b) {

        double x, y;

        String regex = "(-?\\d(\\.\\d+)?)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(a.getPerformance());
        m.find();
        x = Double.parseDouble(m.group(1));

        m = p.matcher(b.getPerformance());
        m.find();
        y = Double.parseDouble(m.group(1));

        return (int)((y - x)*100000);
    }
}
