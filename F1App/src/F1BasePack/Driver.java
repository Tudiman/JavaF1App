package F1BasePack;

import F1BasePack.Utility.*;

import java.util.ArrayList;

public class Driver {

    private static ArrayList<Driver> currentList = new ArrayList<>();

    private String name;
    private Country birthplace;
    private Date birthday;
    private Date joinDate;
    private double performance;
    private int number;
    private Team currentTeam = null;
    private ArrayList<PartnershipContract> teamsHistory = new ArrayList<>();
    private ArrayList<Weekend> weekendsHistory = new ArrayList<>();
    private ArrayList<Championship> championshipsHistory = new ArrayList<>();
    private ArrayList<Prize> prizes = new ArrayList<>();

    public Driver() {
    }

    public Driver(String name, Country birthplace, Date birthday, Date joinDate, double performance, int number) {
        this.name = name;
        this.birthplace = birthplace;
        this.birthday = birthday;
        this.joinDate = joinDate;
        this.performance = performance;
        this.number = number;
    }

    public static ArrayList<Driver> getCurrentList() {
        return currentList;
    }

    public String getName() {
        return name;
    }

    public Country getBirthplace() {
        return birthplace;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public double getPerformance() {
        return performance;
    }

    public int getNumber() {
        return number;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public ArrayList<PartnershipContract> getTeamsHistory() {
        return teamsHistory;
    }

    public ArrayList<Weekend> getWeekendsHistory() {
        return weekendsHistory;
    }

    public ArrayList<Championship> getChampionshipsHistory() {
        return championshipsHistory;
    }

    public ArrayList<Prize> getPrizes() {
        return prizes;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInfo() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", birthplace=" + birthplace +
                ", birthday=" + birthday +
                ", joinDate=" + joinDate +
                ", performance=" + performance +
                ", number=" + number +
                ", currentTeam=" + currentTeam +
                ", teamsHistory=" + teamsHistory +
                ", weekendsHistory=" + weekendsHistory +
                ", championshipsHistory=" + championshipsHistory +
                ", prizes=" + prizes +
                '}';
    }

    public static void setCurrentList(ArrayList<Driver> currentList) {
        Driver.currentList = currentList;
    }

    public static Driver parseLine(String car) {

        String[] values = car.split(",");
        return new Driver(values[0],Country.convertToCountry(values[1]),
                Date.parseDate(values[2]),Date.parseDate(values[3]),
                Double.parseDouble(values[4]),Integer.parseInt(values[5]));

    }

    public static Driver parseDriver(String name, ArrayList<Driver> drivers) {

        for(Driver driver: drivers)
            if(driver.getName().equals(name))
                return driver;
        throw new Error("Driver not found");
    }

    public void joinNewTeam(Team team) {

        if(currentTeam == null)
            currentTeam = team;
        else throw new Error("Driver already in a team");
    }
}
