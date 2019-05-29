package F1BasePack;

import F1BasePack.Utility.*;

public class PartnershipContract {

    private Interval interval;
    private Driver driver;
    private Team team;

    public PartnershipContract(Interval interval, Driver driver, Team team) {
        this.interval = interval;
        this.driver = driver;
        this.team = team;
    }

    public Interval getInterval() {
        return interval;
    }

    public Driver getDriver() {
        return driver;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "Contract between " + driver + " and " + team +
                " from " + interval.getStart() + " to " + interval.getEnd();
    }
}
