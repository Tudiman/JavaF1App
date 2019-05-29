package F1BasePack;

import F1BasePack.Utility.*;

import java.util.ArrayList;

public class Track {

    private static ArrayList<Track> currentList = new ArrayList<>();

    private String name;
    private double length;
    private Country country;
    private int inauguralYear;
    private Time record;
    private ArrayList<Championship> features = new ArrayList<>();

    public Track(String name, double length, Country country, int inauguralYear, Time record) {
        this.name = name;
        this.length = length;
        this.country = country;
        this.inauguralYear = inauguralYear;
        this.record = record;
    }

    public static ArrayList<Track> getCurrentList() {
        return currentList;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public Country getCountry() {
        return country;
    }

    public int getInauguralYear() {
        return inauguralYear;
    }

    public Time getRecord() {
        return record;
    }

    public ArrayList<Championship> getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInfo() {
        return "Track{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", country=" + country +
                ", inauguralYear=" + inauguralYear +
                ", record=" + record +
                ", features=" + features +
                '}';
    }

    public static void setCurrentList(ArrayList<Track> currentList) {
        Track.currentList = currentList;
    }

    public static Track parseLine(String track) {

        String[] values = track.split(",");
        return new Track(values[0],Double.parseDouble(values[1]),
                Country.convertToCountry(values[2]),Integer.parseInt(values[3]),Time.parseTime(values[4]));
    }

    public static Track parseTrack(String name, ArrayList<Track> tracks) {

        for(Track track: tracks)
            if(track.getName().equals(name))
                return track;
        throw new Error("Track not found");
    }
}
