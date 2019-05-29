package F1BasePack;

import F1BasePack.Utility.ChampionshipLeaderboardComparator;
import F1BasePack.Utility.SaveManager;

import java.util.ArrayList;
import java.util.Arrays;

public class Championship {

    private int year;
    private int slot;
    private int[] scoring;
    private ArrayList<Team> teams;
    private ArrayList<Weekend> weekends = new ArrayList<>();
    private ArrayList<ChampionshipLeaderboardEntry> driversLeaderboard = new ArrayList<>();
    private ArrayList<ChampionshipLeaderboardEntry> teamsLeaderboard = new ArrayList<>();
    private int progress = -1;

    public Championship(int year, int slot, int[] scoring, ArrayList<Team> teams) {
        this.year = year;
        this.slot = slot;
        this.scoring = scoring;
        this.teams = teams;
    }

    public Championship(int year, int slot, int[] scoring, ArrayList<Team> teams,
                        ArrayList<Weekend> weekends, ArrayList<ChampionshipLeaderboardEntry> driversLeaderboard,
                        ArrayList<ChampionshipLeaderboardEntry> teamsLeaderboard, int progress) {
        this.year = year;
        this.slot = slot;
        this.scoring = scoring;
        this.teams = teams;
        this.weekends = weekends;
        this.driversLeaderboard = driversLeaderboard;
        this.teamsLeaderboard = teamsLeaderboard;
        this.progress = progress;
    }

    public int getYear() {
        return year;
    }

    public int getSlot() {
        return slot;
    }

    public int[] getScoring() {
        return scoring;
    }

    public ArrayList<Team> getParticipants() {
        return teams;
    }

    public ArrayList<Weekend> getWeekends() {
        return weekends;
    }

    public Weekend getCurrentWeekend() {
        return weekends.get(progress);
    }

    public ArrayList<ChampionshipLeaderboardEntry> getDriversLeaderboard() {
        return driversLeaderboard;
    }

    public ArrayList<ChampionshipLeaderboardEntry> getTeamsLeaderboard() {
        return teamsLeaderboard;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return year + " FIA Formula One World Championship";
    }

    public String getInfo() {
        return "Championship{" +
                "year=" + year +
                "\nscoring=" + Arrays.toString(scoring) +
                "\nteams=" + teams +
                "\nweekends=" + weekends +
                "\ndriversLeaderboard=" + driversLeaderboard +
                "\nteamsLeaderboard=" + teamsLeaderboard +
                '}';
    }

    public void addWeekend(Track track, ArrayList<Team>teams){
        weekends.add(new Weekend(track,teams));
    }

    public void nextWeekend(boolean whole) {
        progress++;

        Weekend weekend = getCurrentWeekend();

        weekend.getPracticeSessions().add(new Session(0,getTeams()));
        weekend.getPracticeSessions().add(new Session(0,getTeams()));
        weekend.getPracticeSessions().add(new Session(0,getTeams()));
        weekend.getQualifyingSessions().add(new Session(1,getTeams()));
        weekend.getQualifyingSessions().add(new Session(1,getTeams()));
        weekend.getQualifyingSessions().add(new Session(1,getTeams()));
        weekend.setRace(new Session(2,getTeams()));

        weekend.nextSession(slot, progress, true);

        SaveManager saveManager = SaveManager.getReference();

        SaveManager.getReference().saveChampionship(this, whole);
    }

    public ChampionshipLeaderboardEntry findLeaderboardEntry(Driver driver) {

        for(ChampionshipLeaderboardEntry entry : driversLeaderboard)
            if(driver == entry.getEntrant())
                return entry;
        throw new Error("Driver not found");
    }

    public ChampionshipLeaderboardEntry findLeaderboardEntry(Team team) {

        for(ChampionshipLeaderboardEntry entry : teamsLeaderboard)
            if(team == entry.getEntrant())
                return entry;
        throw new Error("Team not found");
    }

    public void actualiseLeaderboards() {

        ArrayList<SessionLeaderboardEntry> finishOrder = getCurrentWeekend().getRace().getLeaderboard();
        Driver driver;

        for(int i = 0; i < 10; i++) {
            driver = (Driver)(finishOrder.get(i).getEntrant());
            findLeaderboardEntry(driver).addPoints(scoring[i]);
            findLeaderboardEntry(driver.getCurrentTeam()).addPoints(scoring[i]);
        }

        ChampionshipLeaderboardComparator comparator = new ChampionshipLeaderboardComparator();

        driversLeaderboard.sort(comparator);
        teamsLeaderboard.sort(comparator);
    }
}
