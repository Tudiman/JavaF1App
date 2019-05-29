package F1BasePack;

import F1BasePack.Utility.*;

import java.util.ArrayList;

public class Team {

    private static ArrayList<Team> currentList = new ArrayList<>();

    private String name;
    private String principal;
    private int joinYear;
    private Country country;
    private Car car;
    private ArrayList<Driver> currentDrivers = new ArrayList<>();
    private ArrayList<Driver> driversHistory = new ArrayList<>();
    private ArrayList<Championship> championshipsHistory = new ArrayList<>();
    private ArrayList<Prize> prizes = new ArrayList<>();

    public Team(String name, String principal, int foundingYear, Country country) {
        this.name = name;
        this.principal = principal;
        this.joinYear = foundingYear;
        this.country = country;
        currentDrivers.add(null);
        currentDrivers.add(null);
    }

    public static ArrayList<Team> getCurrentList() {
        return currentList;
    }

    public String getName() {
        return name;
    }

    public String getPrincipal() {
        return principal;
    }

    public int getJoinYear() {
        return joinYear;
    }

    public Country getCountry() {
        return country;
    }

    public Car getCar() {
        return car;
    }

    public ArrayList<Driver> getCurrentDrivers() {
        return currentDrivers;
    }

    public ArrayList<Driver> getDriversHistory() {
        return driversHistory;
    }

    public ArrayList<Championship> getChampionshipsHistory() {
        return championshipsHistory;
    }

    public ArrayList<Prize> getPrizes() {
        return prizes;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCurrentDrivers(Driver driver, int position) {
        this.currentDrivers.set(position - 1,driver);
        driver.joinNewTeam(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInfo() {
        return "Team{" +
                "name='" + name + '\'' +
                ", principal='" + principal + '\'' +
                ", joinYear=" + joinYear +
                ", country=" + country +
                ", car=" + car +
                ", currentDrivers=" + currentDrivers +
                ", driversHistory=" + driversHistory +
                ", championshipsHistory=" + championshipsHistory +
                ", prizes=" + prizes +
                '}';
    }

    public static void setCurrentList(ArrayList<Team> currentList) {
        Team.currentList = currentList;
    }

    public static Team parseLine(String team) {

        String[] values = team.split(",");
        return new Team(values[0],values[1],Integer.parseInt(values[2]),Country.convertToCountry(values[3]));
    }

    public void hireDriver(Driver driver,int position) {

        if(currentDrivers.get(position - 1) != null) {
            driver.joinNewTeam(this);
            currentDrivers.set(position - 1, driver);
        }
        else throw new Error("seat is not empty");
    }

    public static Team parseTeam(String name, ArrayList<Team> teams) {

        for(Team team: teams)
            if(team.getName().equals(name))
                return team;
        throw new Error("Team not found");
    }
}
