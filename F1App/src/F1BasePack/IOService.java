package F1BasePack;

import F1BasePack.Utility.SaveManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IOService {

    private static IOService reference = new IOService();

    private IOService(){
    }

    public static IOService getReference() {
        return reference;
    }

    public void loadStartMenu(){

        ChampionshipHandler handler = ChampionshipHandler.getReference();
        Championship championship;
        int[] scoring = {25,18,15,12,10,8,6,4,2,1};

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;

        System.out.println("Welcome to my F1 Championship Simulator!");

        while(true) {
            System.out.println("\n1 to create a new championship, 2 to load an existing one," +
                    " 3 to delete one, 0 to exit");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice == 1) {
                    championship = handler.newChampionship(scoring);
                    loadChampionshipMenu(championship);
                }
                else if(choice == 2) {
                    choice = loadChampionshipLoader();
                    if(choice != -1) {
                        championship = handler.loadChampionship(choice);
                        loadChampionshipMenu(championship);
                    }
                }
                else if(choice == 3) {
                    loadChampionshipDeleter();
                }
                else throw new Error("Not a valid choice");
            } catch (Exception | Error e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int loadChampionshipLoader() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;

        ChampionshipHandler handler = ChampionshipHandler.getReference();
        ArrayList<Integer> slots = handler.getSlotsList();

        if(slots.size() == 0) {
            System.out.println("No save file found");
            return -1;
        }

        System.out.println("Available slots:");
        for(int slot:slots)
            System.out.println("slot " + (slot + 1));

        while(true) {
            System.out.println("\nChoose a save slot,0 to go back");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line) - 1;
                if(choice == -1)
                    return choice;
                for(int slot:slots)
                    if(choice == slot)
                        return choice;
                throw new Exception("Slot is empty");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadChampionshipDeleter() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;

        ChampionshipHandler handler = ChampionshipHandler.getReference();
        ArrayList<Integer> slots = handler.getSlotsList();

        if(slots.size() == 0) {
            System.out.println("No save file found");
            return;
        }

        System.out.println("Available slots:");
        for(int slot:slots)
            System.out.println("slot " + (slot + 1));

        while(true) {
            System.out.println("\nChoose a slot to delete, 0 to go back");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line) - 1;
                if(choice == -1)
                    return;
                handler.deleteChampionship(choice);
                System.out.println("Championship " + (choice + 1) + " deleted!\n");
                return;

            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadChampionshipMenu(Championship championship) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;
        boolean flag;

        while(true) {
            flag = championship.getProgress() != championship.getWeekends().size();
            if(!flag) {
                System.out.println("\nChampionship ended!");
                System.out.println("Final standings ");
                System.out.println("Drivers:");
                ArrayList<ChampionshipLeaderboardEntry> leaderboard = championship.getDriversLeaderboard();
                for(int i = 0; i < leaderboard.size(); i++)
                    System.out.println((i + 1) + ": " + leaderboard.get(i));
                System.out.println("\n Teams:");
                leaderboard = championship.getTeamsLeaderboard();
                for(int i = 0; i < leaderboard.size(); i++)
                    System.out.println((i + 1) + ": " + leaderboard.get(i));
                System.out.println("\nPress any key to go back");
                while(true) {
                    try {
                        br.readLine();
                        return;
                    } catch (Exception e) {
                        System.out.println("bad input or not a valid choice," +
                                " only enter one of the numbers displayed on screen");
                    }
                }
            }
            System.out.println("\n1 to check leaderboards, 2 to check current weekend details," +
                    "3 to check championship weekends, 4 to advance in current weekend, " +
                    "0 to go back");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice == 1)
                    loadLeaderboardPage(championship);
                else if(choice == 2)
                    loadWeekendMenu(championship.getCurrentWeekend());
                else if(choice == 3)
                    loadWeekendScrollMenu(championship);
                else if(choice == 4)
                    loadWeekendProgressMenu(championship);
                else throw new Error("not a valid choice");
            } catch (Exception | Error e) {
                System.out.println("bad input or not a valid choice," +
                        " only enter one of the numbers displayed on screen");
            }
        }
    }

    public void loadLeaderboardPage(Championship championship) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;

        while(true) {
            System.out.println("\n1 for drivers leaderboard, 2 for teams leaderboard, 0 to go back");
            ArrayList<ChampionshipLeaderboardEntry> leaderboard;
            try {
                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice == 1) {
                    leaderboard = championship.getDriversLeaderboard();
                    for(int i = 0; i < leaderboard.size(); i++)
                        System.out.println((i + 1) + ": " + leaderboard.get(i));
                }
                else if(choice == 2) {
                    leaderboard = championship.getTeamsLeaderboard();
                    for(int i = 0; i < leaderboard.size(); i++)
                        System.out.println((i + 1) + ": " + leaderboard.get(i));
                }
                else throw new Error("not a valid choice");
            } catch (Exception | Error e) {
                System.out.println("bad input or not a valid choice," +
                        " only enter one of the numbers displayed on screen");
            }
        }
    }

    public void loadWeekendMenu(Weekend weekend) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;
        boolean flag = true;

        System.out.println("\nName:" + weekend);
        if(weekend.getProgress() == -1)
            flag = false;

        while(true) {
            System.out.println("\n1 for track, 2 for participants, 3 for current session," +
                    " 4 to see all sessions, 0 to go back");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice == 1)
                    System.out.println(weekend.getTrack());
                else if(choice == 2)
                    for(Team team:weekend.getTeams())
                        for(Driver driver: team.getCurrentDrivers())
                            System.out.println(driver);
                else if(choice == 3)
                    if(flag)
                        loadSessionMenu(weekend.getCurrentSession());
                    else
                        System.out.println("Weekend not started yet, sessions unavailable");
                else if(choice == 4)
                    if(flag)
                        loadSessionScrollMenu(weekend);
                    else
                        System.out.println("Weekend not started yet, sessions unavailable");
                else throw new Error("not a valid choice");
            } catch (Exception | Error e) {
                System.out.println("bad input or not a valid choice," +
                        " only enter one of the numbers displayed on screen");
            }
        }
    }
    public void loadWeekendScrollMenu(Championship championship) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;

        System.out.println("\nTotal weekends " + championship.getWeekends().size() + ",Current weekend: " + championship.getCurrentWeekend());
        int counter = 1;
        for(Weekend weekend: championship.getWeekends())
            System.out.println(counter++ + ": " + weekend);
        while(true) {
            System.out.println("\nChoose a weekend to get details about, 0 to go back");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice <= 0 || choice >= counter)
                    throw new Error("not a valid choice");
                else
                    loadWeekendMenu(championship.getWeekends().get(choice - 1));
            } catch (Exception | Error e) {
                System.out.println("bad input or not a valid choice," +
                        " only enter one of the numbers displayed on screen");
            }
        }
    }

    public void loadSessionMenu(Session session) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;
        String[] type = {"Practice session","Qualifying session","Race"};

        boolean flag = true;
        if(session.getLeaderboard().size() == 0)
            flag = false;

        System.out.println("\n" + type[session.getType()]);

        while(true) {
            System.out.println("\n1 to see time of day and weather,2 to see participants, 3 to see leaderboard, 0 to go back");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice == 1)
                    System.out.println(session.getTimeOfDay() + ", " + session.getWeather());
                else if(choice == 2)
                    for(Team team: session.getTeams())
                        for(Driver driver: team.getCurrentDrivers())
                            System.out.println(driver);
                else if(choice == 3)
                    if(flag)
                        for(SessionLeaderboardEntry sessionLeaderboardEntry: session.getLeaderboard())
                            System.out.println(sessionLeaderboardEntry);
                    else
                        System.out.println("Session not started");
                else throw new Error("not a valid choice");
            } catch (Exception | Error e) {
                System.out.println("bad input or not a valid choice," +
                        " only enter one of the numbers displayed on screen");
            }
        }
    }

    public void loadSessionScrollMenu(Weekend weekend) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;

        while(true) {
            System.out.println("\nChoose a session, 1 to 3 for practice, 4 to 6 for qualifying, 7 for race 0 to go back");
            try {
                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice >= 1 && choice <= 3)
                    loadSessionMenu(weekend.getPracticeSessions().get(choice - 1));
                else if(choice >= 4 && choice <= 6)
                    loadSessionMenu(weekend.getQualifyingSessions().get(choice - 4));
                else if(choice == 7)
                    loadSessionMenu(weekend.getRace());
                else throw new Error("not a valid choice");
            } catch (Exception | Error e) {
                System.out.println("bad input or not a valid choice," +
                        " only enter one of the numbers displayed on screen");
            }
        }
    }

    public void loadWeekendProgressMenu(Championship championship) {

        Weekend weekend = championship.getCurrentWeekend();

        WeekendHandler handler = WeekendHandler.getReference();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int choice;

        boolean flag;
        boolean qualiFlag;

        String[] nextSession = {"Practice 1","Practice 2","Practice 3",
                "Qualifying 1","Qualifying 2","Qualifying 3","Race"};

        while(true) {
            flag = weekend.getProgress() != 7;
            qualiFlag = weekend.getProgress() == 6;
            if(qualiFlag)
                System.out.println("\n1 to start " + nextSession[weekend.getProgress()] +
                        ", 2 to see starting grid, 0 to go back");
            else if(flag)
                System.out.println("\n1 to start " + nextSession[weekend.getProgress()] +
                        ", 0 to go back");
            else {
                championship.actualiseLeaderboards();
                System.out.println("\n1 to advance to next weekend, 0 to go back");
            }
            try {

                line = br.readLine();
                choice = Integer.parseInt(line);
                if(choice == 0)
                    return;
                else if(choice == 1)
                    if(flag) {
                        ArrayList<SessionLeaderboardEntry> leaderboard = handler.runSession(weekend,
                                championship.getSlot(), championship.getProgress());
                        for(int i = 0; i < leaderboard.size();i++)
                            System.out.println((i+1) + ": " + leaderboard.get(i));
                    }
                    else {
                        championship.nextWeekend(false);
                        return;
                    }
                else if(choice == 2 && qualiFlag) {
                    ArrayList<Driver> grid = handler.getStartingGrid(weekend);
                    for(int i = 0; i < grid.size(); i++)
                        System.out.println(i + 1 + ": " + grid.get(i));
                }
                else throw new Error("not a valid choice");
            } catch (Exception | Error e) {
                System.out.println("bad input or not a valid choice," +
                        " only enter one of the numbers displayed on screen");
            }
        }
    }
}
