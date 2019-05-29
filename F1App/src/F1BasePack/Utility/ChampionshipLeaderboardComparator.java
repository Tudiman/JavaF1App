package F1BasePack.Utility;

import F1BasePack.ChampionshipLeaderboardEntry;

import java.util.Comparator;

public class ChampionshipLeaderboardComparator implements Comparator<ChampionshipLeaderboardEntry> {

    public int compare(ChampionshipLeaderboardEntry a, ChampionshipLeaderboardEntry b) {

        return b.getPoints() - a.getPoints();

    }

}
