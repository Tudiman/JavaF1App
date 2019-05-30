package F1BasePack.Utility;

import F1BasePack.Car;
import F1BasePack.Driver;
import F1BasePack.Team;
import F1BasePack.Track;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseParser{

    private static DatabaseParser reference = new DatabaseParser();

    private DatabaseParser() {
    }

    public static DatabaseParser getReference() {
        return reference;
    }

    public ArrayList<String> parseFileLineByLine(String path) {

        BufferedReader reader = null;
        ArrayList<String> parsedDatabase = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(path));
            TimestampService.getReference().postTimestamp("Database parser read from " + path);
            String line = reader.readLine();
            while(line != null) {

                parsedDatabase.add(line);
                line = reader.readLine();
            }
        } catch(IOException e) {
            System.out.println("File not found");
        } finally {
            if(reader != null)
                try {
                    reader.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        }
        return parsedDatabase;
    }

    public void prepareDatabase(String path, String[] files, String[] types) {

        if(files.length != types.length)
            throw new Error("Database preparer received different size array parameters");

        Country.setCurrentList((ArrayList<Country>)reference.loadDatabase(reference.parseFileLineByLine(path + "/" + files[0]), types[0]));
        Team.setCurrentList((ArrayList<Team>)reference.loadDatabase(reference.parseFileLineByLine(path + "/" + files[1]), types[1]));
        Car.setCurrentList((ArrayList<Car>)reference.loadDatabase(reference.parseFileLineByLine(path + "/" + files[2]), types[2]));
        Driver.setCurrentList((ArrayList<Driver>)reference.loadDatabase(reference.parseFileLineByLine(path + "/" + files[3]), types[3]));
        Track.setCurrentList((ArrayList<Track>)reference.loadDatabase(reference.parseFileLineByLine(path + "/" + files[4]), types[4]));

        linkTables();
    }

    public ArrayList<?> loadDatabase(ArrayList<String> formattedLines, String type) {

        TimestampService.getReference().postTimestamp("Database loader loaded the dynamic " + type + " database");

        if(type.equalsIgnoreCase("country")) {
            ArrayList<Country> objects = new ArrayList<Country>();
            for(String line: formattedLines)
                objects.add(Country.parseLine(line));
            return objects;
        }
        else if(type.equalsIgnoreCase("car")) {
            ArrayList<Car> objects = new ArrayList<>();
            for(String line: formattedLines)
                objects.add(Car.parseLine(line));
            return objects;
        }
        else if(type.equalsIgnoreCase("driver")) {
            ArrayList<Driver> objects = new ArrayList<>();
            for(String line: formattedLines)
                objects.add(Driver.parseLine(line));
            return objects;
        }
        else if(type.equalsIgnoreCase("team")) {
            ArrayList<Team> objects = new ArrayList<>();
            for(String line: formattedLines)
                objects.add(Team.parseLine(line));
            return objects;
        }
        else if(type.equalsIgnoreCase("track")) {
            ArrayList<Track> objects = new ArrayList<>();
            for(String line: formattedLines)
                objects.add(Track.parseLine(line));
            return objects;
        }
        else throw new Error("No such object type in database");
    }

    public void linkTables() {

        ArrayList<Team> teams = Team.getCurrentList();

        for(int i = 0; i < teams.size(); i++) {
            teams.get(i).setCar(Car.getCurrentList().get(i));
            teams.get(i).setCurrentDrivers(Driver.getCurrentList().get(2*i),1);
            teams.get(i).setCurrentDrivers(Driver.getCurrentList().get(2*i + 1),2);
        }
        TimestampService.getReference().postTimestamp("Database linker linked tables");
    }
}
