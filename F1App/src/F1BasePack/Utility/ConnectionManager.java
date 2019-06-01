package F1BasePack.Utility;

import F1BasePack.Car;
import F1BasePack.Driver;
import F1BasePack.Team;
import F1BasePack.Track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

public class ConnectionManager {

    private static ConnectionManager reference = new ConnectionManager();
    private static Connection connection;

    private static String URL;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pvladone.13";

    private ConnectionManager() {

        try {
            URL = "jdbc:mysql://localhost:3306/f1database";
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionManager getReference() {
        return reference;
    }

    public Connection getConnection() {

        return connection;
    }

    public void populateTable(String name, Connection connection, String path) {

        BufferedReader br = null;
        String line;
        String[] fields;

        try {
            br = new BufferedReader(new FileReader(path));
            line = br.readLine();
            while(line != null) {

                fields = line.split(",");

                String query = "insert into " + name + " values (NULL,?";
                for(int i = 0; i < fields.length - 1; i++)
                    query += ",?";
                query += ")";
                PreparedStatement p = connection.prepareStatement(query);
                for(int i = 0; i < fields.length; i++)
                    p.setString(i + 1, fields[i]);
                p.executeUpdate();

                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(br != null)
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public void populateTables(String[] names, Connection connection, String[] paths) {

        for(int i = 0; i < names.length; i++)
            populateTable(names[i], connection, paths[i]);
    }

    public void initialPopulate() {

        int size = Consts.originalDatabaseFiles.length;
        String[] names = {"countries","teams","cars","drivers","tracks"};
        String[] paths = new String[size];
        for(int i = 0; i < size; i++) {
            paths[i] = Consts.generalFilesPath + Consts.originalDatabaseFiles[i];
        }
        populateTables(names, connection, paths);

    }

    public void createDatabase(int saveSlot) {

        String name = "f1db_" + saveSlot;

        String query = "create database " + name + ";";

        try {
            connection.prepareStatement(query).executeUpdate();
            query = "use " + name + ";";
            connection.prepareStatement(query).executeUpdate();
            query = "create table drivers (\n" +
                    "\tid int(5) not null primary key auto_increment,\n" +
                    "    name varchar(45) not null,\n" +
                    "    birthplace varchar(45) not null,\n" +
                    "    birthday varchar(45) not null,\n" +
                    "    joinDate varchar(45) not null,\n" +
                    "    performance varchar(45) not null,\n" +
                    "    number varchar(45) not null\n" +
                    ");";
            connection.prepareStatement(query).executeUpdate();
            query = "create table teams (\n" +
                    "\tid int(5) not null primary key auto_increment,\n" +
                    "    name varchar(45) not null,\n" +
                    "    principal varchar(45) not null,\n" +
                    "    joinYear varchar(45) not null,\n" +
                    "    country varchar(45) not null\n" +
                    ");";
            connection.prepareStatement(query).executeUpdate();
            query = "create table cars (\n" +
                    "\tid int(5) not null primary key auto_increment,\n" +
                    "    name varchar(45) not null,\n" +
                    "    version varchar(45) not null,\n" +
                    "    performance varchar(45) not null,\n" +
                    "    reliability varchar(45) not null\n" +
                    ");";
            connection.prepareStatement(query).executeUpdate();
            query = "create table tracks (\n" +
                    "\tid int(5) not null primary key auto_increment,\n" +
                    "    name varchar(45) not null,\n" +
                    "    length varchar(45) not null,\n" +
                    "    country varchar(45) not null,\n" +
                    "    inauguralYear varchar(45) not null,\n" +
                    "    record varchar(45) not null\n" +
                    ");";
            connection.prepareStatement(query).executeUpdate();
            query = "create table countries (\n" +
                    "\tid int(5) not null primary key auto_increment,\n" +
                    "    name varchar(45) not null,\n" +
                    "    referral varchar(45) not null,\n" +
                    "    continent varchar(45) not null\n" +
                    ");";
            connection.prepareStatement(query).executeUpdate();

            loadDatabase(saveSlot, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadDatabase(int which, boolean initialPopulate) {

        try {
            connection.close();
            if(which == -1)
                URL = "jdbc:mysql://localhost:3306/f1database";
            else
                URL = "jdbc:mysql://localhost:3306/f1db_" + which;
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(initialPopulate)
            initialPopulate();
        loadTables();
    }

    public void loadDatabase(int which) {

        try {
            connection.close();
            if(which == -1)
                URL = "jdbc:mysql://localhost:3306/f1database";
            else
                URL = "jdbc:mysql://localhost:3306/f1db_" + which;
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadTables();
    }



    public void dropDatabase(int which) {

        String query = "drop schema if exists f1db_" + which + ";";

        try {
            connection.prepareStatement(query).executeUpdate();
            loadDatabase(-1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadTable(Connection connection, String type) {

        if(type.equals("car")) {
            ArrayList<Car> cars = new ArrayList<>();
            try {
                ResultSet resultSet = connection.prepareStatement("select * from cars").executeQuery();
                while(resultSet.next()) {
                    cars.add(new Car(resultSet.getString("name"),
                            Integer.parseInt(resultSet.getString("version")),
                            Double.parseDouble(resultSet.getString("performance")),
                            Double.parseDouble(resultSet.getString("reliability"))));
                }
                Car.setCurrentList(cars);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("team")) {
            ArrayList<Team> teams = new ArrayList<>();
            try {
                ResultSet resultSet = connection.prepareStatement("select * from teams").executeQuery();
                while(resultSet.next()) {
                    teams.add(new Team(resultSet.getString("name"),
                            resultSet.getString("principal"),
                            Integer.parseInt(resultSet.getString("joinYear")),
                            Country.convertToCountry(resultSet.getString("country"))));
                }
                Team.setCurrentList(teams);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("track")) {
            ArrayList<Track> tracks = new ArrayList<>();
            try {
                ResultSet resultSet = connection.prepareStatement("select * from tracks").executeQuery();
                while(resultSet.next()) {
                    tracks.add(new Track(resultSet.getString("name"),
                            Double.parseDouble(resultSet.getString("length")),
                            Country.convertToCountry(resultSet.getString("country")),
                            Integer.parseInt(resultSet.getString("inauguralYear")),
                            Time.parseTime(resultSet.getString("record"))));
                }
                Track.setCurrentList(tracks);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("country")) {
            ArrayList<Country> countries = new ArrayList<>();
            try {
                ResultSet resultSet = connection.prepareStatement("select * from countries").executeQuery();
                while(resultSet.next()) {
                    countries.add(new Country(resultSet.getString("name"),
                            resultSet.getString("referral"),
                            resultSet.getString("continent")));
                }
                Country.setCurrentList(countries);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("driver")) {
            ArrayList<Driver> drivers = new ArrayList<>();
            try {
                ResultSet resultSet = connection.prepareStatement("select * from drivers").executeQuery();
                while(resultSet.next()) {
                    drivers.add(new Driver(resultSet.getString("name"),
                            Country.convertToCountry(resultSet.getString("birthplace")),
                            Date.parseDate(resultSet.getString("birthday")),
                            Date.parseDate(resultSet.getString("joinDate")),
                            Double.parseDouble(resultSet.getString("performance")),
                            Integer.parseInt(resultSet.getString("number"))));
                }
                Driver.setCurrentList(drivers);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void loadTables() {

        for(int i = 0; i < Consts.originalDatabaseTypes.length; i++)
            loadTable(connection, Consts.originalDatabaseTypes[i]);
    }

}
