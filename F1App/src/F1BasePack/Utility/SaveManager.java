package F1BasePack.Utility;

import F1BasePack.*;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;

public class SaveManager {

    private static SaveManager reference = new SaveManager();

    private SaveManager(){}

    public static SaveManager getReference() {
        return reference;
    }

    public void uploadOrigin(String path, String[] files, int slot) {

        String name;
        File source;
        File destination;

        for(String fileName: files) {
            try {
                source = new File(path + fileName);
                new File("Save Files/save_" + slot).mkdirs();
                destination = new File("Save Files/save_" + slot + "/" + fileName);
                Files.copy(source.toPath(), destination.toPath());
            } catch(Exception e) {

            }
        }

        TimestampService.getReference().postTimestamp("Database uploader transferred original databases'" +
                " info to save slot " + slot);
    }

    public void saveChampionship(Championship championship, boolean whole) {

        BufferedWriter bw = null;

        int slot = championship.getSlot();

        try {
            File destination = new File("Save Files/save_" + slot + "/Championship.csv");
            if(!destination.exists())
                destination.createNewFile();

            bw = new BufferedWriter(new FileWriter(destination));
            bw.write("Year\n" +championship.getYear() + "\n");
            bw.write("Scoring\n");
            for(int i:championship.getScoring())
                bw.write(i + " ");
            bw.write("\nTeams\n" + championship.getTeams().size() + "\n");
            for(Team team: championship.getTeams())
                bw.write(team + "\n");
            bw.write("Weekends\n" + championship.getWeekends().size() + "\n");
            bw.write("Drivers leaderboard\n" + championship.getDriversLeaderboard().size() + "\n");
            for(ChampionshipLeaderboardEntry entry: championship.getDriversLeaderboard())
                bw.write(entry + "\n");
            bw.write("Teams leaderboard\n" + championship.getTeamsLeaderboard().size() + "\n");
            for(ChampionshipLeaderboardEntry entry: championship.getTeamsLeaderboard())
                bw.write(entry + "\n");
            bw.write("Progress\n" + championship.getProgress());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        TimestampService.getReference().postTimestamp("Championship " + slot + " has been saved in "
                + "Save Files/save_" + slot + "/Championship.csv");

        if(!whole)
            return;
        int counter = 0;
        for(Weekend weekend: championship.getWeekends())
            saveWeekend(weekend,slot,counter++,true);
    }

    public void saveWeekend(Weekend weekend, int slot, int which, boolean whole) {

        BufferedWriter bw = null;

        try {
            new File("Save Files/save_" + slot + "/Weekend_" +
                    which).mkdirs();
            File destination = new File("Save Files/save_" + slot + "/Weekend_" +
                    which + "/Weekend.csv");
            if(!destination.exists())
                destination.createNewFile();

            bw = new BufferedWriter(new FileWriter(destination));
            bw.write("Track\n" + weekend.getTrack() + "\n");
            bw.write("Progress\n" + weekend.getProgress());

        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        TimestampService.getReference().postTimestamp("Weekend " + which + " of championship "
                + slot + " has been saved in " + "Save Files/save_" + slot + "/Weekend_" +
                which + "/Weekend.csv");

        if(!whole)
            return;

        if(weekend.getProgress() == -1)
            return;

        int counter = 0;
        for(Session session : weekend.getPracticeSessions())
            saveSession(session, slot, which, counter++);
        for(Session session : weekend.getQualifyingSessions())
            saveSession(session, slot, which, counter++);
        saveSession(weekend.getRace(), slot, which, counter);
    }

    public void saveSession(Session session, int slot, int weekend, int which) {

        BufferedWriter bw = null;

        try {
            File destination = new File("Save Files/save_" + slot +
                    "/Weekend_" + weekend + "/Session_" + which + ".csv");
            if(!destination.exists())
                destination.createNewFile();

            bw = new BufferedWriter(new FileWriter(destination));
            bw.write("Type\n" + session.getType() + "\n");
            bw.write("Weather\n" + session.getWeather() + "\n");
            bw.write("Time of day\n" + session.getTimeOfDay() + "\n");
            bw.write("Leaderboard\n" + session.getLeaderboard().size() + "\n");
            for(SessionLeaderboardEntry entry : session.getLeaderboard())
                bw.write(entry + "\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(bw != null)
                    bw.close();
            } catch (Exception e)  {
                System.out.println(e.getMessage());
            }
        }
        TimestampService.getReference().postTimestamp("Session " + which + " from weekend " + weekend
                + " of championship " + slot + " has been saved in " + "Save Files/save_" + slot +
                "/Weekend_" + weekend + "/Session_" + which + ".csv");
    }

    public Session loadSession(int slot, int weekend, ArrayList<Team> teams, int which) {

        int type;
        String weather;
        String timeOfDay;
        ArrayList<SessionLeaderboardEntry> leaderboard = new ArrayList<>();
        int size;

        BufferedReader br = null;
        String columnName;
        try {
            File source = new File("Save Files/save_" + slot +
                    "/Weekend_" + weekend + "/Session_" + which + ".csv");
            if(!source.exists())
                throw new Error("Session file does not exist");

            br = new BufferedReader(new FileReader(source));

            columnName = br.readLine();
            if(!columnName.equals("Type"))
                throw new Error("Corrupted save file");
            type = Integer.parseInt(br.readLine());

            columnName = br.readLine();
            if(!columnName.equals("Weather"))
                throw new Error("Corrupted save file");
            weather = br.readLine();

            columnName = br.readLine();
            if(!columnName.equals("Time of day"))
                throw new Error("Corrupted save file");
            timeOfDay = br.readLine();

            columnName = br.readLine();
            if(!columnName.equals("Leaderboard"))
                throw new Error("Corrupted save file");
            size = Integer.parseInt(br.readLine());
            for(int i = 0; i < size; i++)
                leaderboard.add(SessionLeaderboardEntry.parseEntry(br.readLine()));

            TimestampService.getReference().postTimestamp("Session " + which + " from weekend " + weekend
                    + " of championship " + slot + " has been loaded from " + "Save Files/save_" + slot +
                    "/Weekend_" + weekend + "/Session_" + which + ".csv");

            return new Session(type,weather,timeOfDay,teams,leaderboard);

        } catch(Exception | Error e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Weekend loadWeekend(int slot, int weekend, ArrayList<Team> teams) {

        Weekend loadedWeekend;
        Track track;
        int progress;

        BufferedReader br = null;
        String columnName;

        try {
            File source = new File("Save Files/save_" + slot + "/Weekend_" + weekend + "/Weekend.csv");
            if(!source.exists())
                throw new Error("Weekend file does not exist");

            br = new BufferedReader(new FileReader(source));

            columnName = br.readLine();
            if(!columnName.equals("Track"))
                throw new Error("Corrupted save file");
            track = Track.parseTrack(br.readLine(),Track.getCurrentList());

            columnName = br.readLine();
            if(!columnName.equals("Progress"))
                throw new Error("Corrupted save file");
            progress = Integer.parseInt(br.readLine());

            loadedWeekend = new Weekend(track, teams, progress);

            if(progress == -1)
                return loadedWeekend;

            ArrayList<Session> practice = new ArrayList<>();
            ArrayList<Session> quali = new ArrayList<>();
            Session race;
            int i = 0;
            for(; i < 3; i++)
                practice.add(loadSession(slot, weekend, teams, i));
            for(; i < 6; i++)
                quali.add(loadSession(slot, weekend, teams, i));
            race = loadSession(slot, weekend, teams, i);

            loadedWeekend.setPracticeSessions(practice);
            loadedWeekend.setQualifyingSessions(quali);
            loadedWeekend.setRace(race);

            TimestampService.getReference().postTimestamp("Weekend " + weekend + " of championship "
                    + slot + " has been loaded from " + "Save Files/save_" + slot + "/Weekend_" +
                    weekend + "/Weekend.csv");

            return loadedWeekend;


        } catch(Exception | Error e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Championship loadChampionship(int slot) {

        int year;
        String[] basicScoring;
        int[] scoring;
        int size;
        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<Weekend> weekends = new ArrayList<>();
        ArrayList<ChampionshipLeaderboardEntry> driversLeaderboard = new ArrayList<>();
        ArrayList<ChampionshipLeaderboardEntry> teamsLeaderboard = new ArrayList<>();
        int progress;

        BufferedReader br = null;
        String columnName;

        try {
            File source = new File("Save Files/save_" + slot + "/Championship.csv");
            if(!source.exists())
                throw new Error("Championship file does not exist");

            br = new BufferedReader(new FileReader(source));
            columnName = br.readLine();
            if(!columnName.equals("Year"))
                throw new Error("Corrupted save file");
            year = Integer.parseInt(br.readLine());

            columnName = br.readLine();
            if(!columnName.equals("Scoring"))
                throw new Error("Corrupted save file");
            basicScoring = br.readLine().split(" ");
            scoring = new int[basicScoring.length];
            for(int i = 0; i < scoring.length; i++)
                scoring[i] = Integer.parseInt(basicScoring[i]);

            columnName = br.readLine();
            if(!columnName.equals("Teams"))
                throw new Error("Corrupted save file");
            size = Integer.parseInt(br.readLine());
            for(int i = 0; i < size; i++)
                teams.add(Team.parseTeam(br.readLine(),Team.getCurrentList()));

            columnName = br.readLine();
            if(!columnName.equals("Weekends"))
                throw new Error("Corrupted save file");
            size = Integer.parseInt(br.readLine());
            for(int i = 0; i < size; i++)
                weekends.add(loadWeekend(slot, i, teams));

            columnName = br.readLine();
            if(!columnName.equals("Drivers leaderboard"))
                throw new Error("Corrupted save file");
            size = Integer.parseInt(br.readLine());
            for(int i = 0; i < size; i++)
                driversLeaderboard.add(ChampionshipLeaderboardEntry.parseEntry(br.readLine(),0));

            columnName = br.readLine();
            if(!columnName.equals("Teams leaderboard"))
                throw new Error("Corrupted save file");
            size = Integer.parseInt(br.readLine());
            for(int i = 0; i < size; i++)
                teamsLeaderboard.add(ChampionshipLeaderboardEntry.parseEntry(br.readLine(),1));

            columnName = br.readLine();
            if(!columnName.equals("Progress"))
                throw new Error("Corrupted save file");
            progress = Integer.parseInt(br.readLine());

            TimestampService.getReference().postTimestamp("Championship " + slot + " has been loaded from "
                    + "Save Files/save_" + slot + "/Championship.csv");

            return new Championship(year, slot, scoring, teams, weekends,
                    driversLeaderboard, teamsLeaderboard, progress);

        } catch(Exception | Error e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void uploadSaveSlots(ArrayList<Integer> slots) {

        BufferedWriter bw = null;

        try {
            File destination = new File(Consts.generalFilesPath + "Slots.csv");
            if(!destination.exists())
                destination.createNewFile();
            bw = new BufferedWriter(new FileWriter(destination));
            for(int i: slots)
                bw.write(i + ",");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(bw != null)
                    bw.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Integer> loadSaveSlots() {

        BufferedReader br = null;
        String[] basicSlots;
        ArrayList<Integer> slots = new ArrayList<>();

        try {
            File source = new File(Consts.generalFilesPath + "Slots.csv");
            if(!source.exists()) {
                return new ArrayList<>();
            }
            try {

                br = new BufferedReader(new FileReader(source));
                basicSlots = br.readLine().split(",");
            } catch (Exception e){
                return new ArrayList<>();
            }
            for(String slot : basicSlots)
                slots.add(Integer.parseInt(slot));

            return slots;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteDirectory(File file) {

        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                deleteDirectory(child);
            }
        }
        file.delete();
    }

    public void deleteSaveSlot(int slot) {

        ArrayList<Integer> slots = loadSaveSlots();
        slots.remove((Integer)slot);
        uploadSaveSlots(slots);
    }

    public void deleteChampionship(int slot) {

        deleteDirectory(new File("Save files/save_" + slot));
        deleteSaveSlot(slot);

    }
}
