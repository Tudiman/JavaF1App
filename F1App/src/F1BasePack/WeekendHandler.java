package F1BasePack;

import F1BasePack.Utility.Consts;
import F1BasePack.Utility.SaveManager;
import F1BasePack.Utility.SessionLeaderboardComparator;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeekendHandler {

    private static WeekendHandler reference = new WeekendHandler();

    private WeekendHandler(){
    }

    public static WeekendHandler getReference() {
        return reference;
    }

    private double getConcentration(Driver driver, int session) {

        double sessionSeed = session == 1 ? 1 : session == 2 ? 3 : 2;

        int seed = Consts.YEAR - driver.getBirthday().getYear();
        seed = seed < 20 ? 20 : seed;
        seed -= 20;
        seed += Consts.YEAR - driver.getJoinDate().getYear();

        double deviation = 0.05 * sessionSeed - 0.0033*seed;
        double center = 1 + 0.0033*seed;

        return Math.random() * 2 * deviation + center - deviation;
    }

    private double getConcentrationAverage(Driver driver) {

        int seed = Consts.YEAR - driver.getBirthday().getYear();
        seed = seed < 20 ? 20 : seed;
        seed -= 20;
        seed += Consts.YEAR - driver.getJoinDate().getYear();

        return 1 + 0.0033*seed;
    }

    private double getDeviation(Driver driver, int session) {

        double sessionSeed = session == 1 ? 1 : session == 2 ? 3 : 2;

        int seed = Consts.YEAR - driver.getBirthday().getYear();
        seed = seed < 20 ? 20 : seed;
        seed -= 20;
        seed += Consts.YEAR - driver.getJoinDate().getYear();

        return 0.05 * sessionSeed - 0.0033*seed;
    }

    private double getMistakeFactor(int experience, int seed) {

        double mistakeSeed = seed == 1 ?  1 : seed == 2 ? 0.75 : 2;
        double bigChance = (0.15 - 0.003*experience) * mistakeSeed;
        double mediumChance = (0.05 - 0.001*experience) * mistakeSeed;
        double smallChance = (0.015 - 0.0005*experience) * mistakeSeed;

        double base = Math.random();
        if(base < bigChance)
            return 0.95;
        else if(base < bigChance + mediumChance)
            return 0.85;
        else if(base < bigChance + mediumChance + smallChance)
            return 0;
        else
            return 1;
    }

    private double getCarFailFactor(Car car, int seed) {

        double sessionSeed = seed == 1 ? 0 : seed == 2 ? 0.2 : 1;

        double issuesFactor = (1 - car.getReliability())*sessionSeed;
        double base = Math.random();
        if(base < issuesFactor)
            return 0.9;
        else if(base < issuesFactor * 1.5)
            return 0.6;
        else if(base < issuesFactor * 1.75)
            return 0;
        else
            return 1;
    }

    private String performanceFormula(Driver driver, boolean predict, int sessionSeed) {

        DecimalFormat formatter = new DecimalFormat("#.#####");

        Car car = driver.getCurrentTeam().getCar();

        double concentration = getConcentration(driver, sessionSeed);
        double mistakeFactor = getMistakeFactor(Consts.YEAR - driver.getJoinDate().getYear(),sessionSeed);
        boolean crashed = mistakeFactor == 0;
        double carFailFactor = getCarFailFactor(car,sessionSeed);

        String details;

        if(predict) {
            details = formatter.format(driver.getPerformance() * car.getPerformance());
            return details;
        }
        else
            details = formatter.format(driver.getPerformance()*car.getPerformance()*
                    concentration*mistakeFactor*carFailFactor);

        if(concentration > getConcentrationAverage(driver) + getDeviation(driver,2) / 2)
            details += ", strong pace";
        else if(concentration < getConcentrationAverage(driver) - getDeviation(driver,2) / 2)
            details += ", weak pace";
        else details += ", average pace";

        if(mistakeFactor == 0.95)
            details += ", made minor mistakes";
        else if(mistakeFactor == 0.85)
            details += ",made a major mistake";
        else if(mistakeFactor == 0) {
            details += ",crashed";
        }
        if(carFailFactor == 0.9)
            details += ",had minor mechanical issues";
        else if(carFailFactor == 0.6)
            details += ",had major mechanical issues";
        else if(carFailFactor == 0 && !crashed)
            details += ",had a race-ending mechanical failure";

        return details;

    }

    public ArrayList<SessionLeaderboardEntry> runSession(Weekend weekend, int slot, int which) {

        Session session = weekend.getCurrentSession();
        int sessionSeed = weekend.getProgress() < 3 ? 1 : weekend.getProgress() < 6 ? 2 : 3;
        ArrayList<SessionLeaderboardEntry> leaderboard = new ArrayList<>();
        String performance;
        ArrayList<Driver> drivers = new ArrayList<>();
        ArrayList<Team> teams = session.getTeams();
        ArrayList<Driver> teamDrivers;

        if(weekend.getProgress() < 4)
            for (Team team : teams) {
                teamDrivers = team.getCurrentDrivers();
                drivers.add(teamDrivers.get(0));
                drivers.add(teamDrivers.get(1));
            }
        else if(weekend.getProgress() == 4)
            for(int i = 0; i < 15; i++)
                drivers.add((Driver)(weekend.getQualifyingSessions().get(0).getLeaderboard().get(i).getEntrant()));

        else if(weekend.getProgress() == 5)
            for(int i = 0; i < 10; i++)
                drivers.add((Driver)(weekend.getQualifyingSessions().get(1).getLeaderboard().get(i).getEntrant()));

        else {
            int i = 0;
            for(; i < 10; i++)
                drivers.add((Driver)(weekend.getQualifyingSessions().get(2).getLeaderboard().get(i).getEntrant()));
            for(; i < 15; i++)
                drivers.add((Driver)(weekend.getQualifyingSessions().get(1).getLeaderboard().get(i).getEntrant()));
            for(; i < 20; i++)
                drivers.add((Driver)(weekend.getQualifyingSessions().get(0).getLeaderboard().get(i).getEntrant()));
        }

        for(Driver driver: drivers) {
            performance = performanceFormula(driver,false, sessionSeed);
            leaderboard.add(new SessionLeaderboardEntry(driver,performance));
        }

        if(weekend.getProgress() == 6) {
            for(int i = 0; i < 20; i++)
                leaderboard.get(i).setDelay(i + 1);
        }

        leaderboard.sort(new SessionLeaderboardComparator());

        session.setLeaderboard(leaderboard);

        SaveManager.getReference().saveSession(session, slot, which, weekend.getProgress());

        weekend.nextSession(slot, which, false);

        return leaderboard;
    }

    public ArrayList<Driver> getStartingGrid(Weekend weekend) {

        for(int i = 0; i < 3; i++)
            if(weekend.getQualifyingSessions().get(i).getLeaderboard().size() == 0)
                throw new Error("Qualifying not complete");

        ArrayList<Driver> startingGrid = new ArrayList<>();

        int i = 0;
        for(; i < 10; i++)
            startingGrid.add((Driver)(weekend.getQualifyingSessions().get(2).getLeaderboard().get(i).getEntrant()));
        for(; i < 15; i++)
            startingGrid.add((Driver)(weekend.getQualifyingSessions().get(1).getLeaderboard().get(i).getEntrant()));
        for(; i < 20; i++)
            startingGrid.add((Driver)(weekend.getQualifyingSessions().get(0).getLeaderboard().get(i).getEntrant()));

        return startingGrid;

    }
}
