package F1BasePack;

import F1BasePack.Utility.Consts;
import F1BasePack.Utility.DatabaseParser;
import F1BasePack.Utility.SaveManager;

import java.util.ArrayList;
import java.util.Comparator;

public class ChampionshipHandler {

    private static ChampionshipHandler reference = new ChampionshipHandler();

    private Championship currentChampionship;

    private ChampionshipHandler(){
    }

    public static ChampionshipHandler getReference() {
        return reference;
    }

    public Championship getCurrentChampionship() {
        return currentChampionship;
    }

    public void setCurrentChampionship(Championship currentChampionship) {
        this.currentChampionship = currentChampionship;
    }

    public int getSaveSlot() {

        SaveManager saveManager = SaveManager.getReference();

        ArrayList<Integer> slots = saveManager.loadSaveSlots();
        int where = 0;

        while(where < slots.size()) {
            if(slots.get(where) != where)
                break;
            where++;
        }

        slots.add(where);
        slots.sort(Comparator.naturalOrder());
        saveManager.uploadSaveSlots(slots);

        return where;
    }

    public boolean checkSaveSlot(int which) {

        SaveManager saveManager = SaveManager.getReference();

        ArrayList<Integer> slots = saveManager.loadSaveSlots();

        for(int slot: slots)
            if(slot == which)
                return true;

        return false;
    }

    public ArrayList<Integer> getSlotsList() {

        return SaveManager.getReference().loadSaveSlots();
    }

    public Championship newChampionship(int[] scoring){

        SaveManager saveManager = SaveManager.getReference();
        DatabaseParser databaseParser = DatabaseParser.getReference();

        Championship championship;

        int where = getSaveSlot();
        saveManager.uploadOrigin(Consts.generalFilesPath, Consts.originalDatabaseFiles, where);

        databaseParser.prepareDatabase("Save files/save_" + where, Consts.originalDatabaseFiles, Consts.originalDatabaseTypes);

        ArrayList<Team> teams = Team.getCurrentList();
        ArrayList<Track> tracks = Track.getCurrentList();

        championship = new Championship(2019, where, scoring, teams);

        for(Track track:tracks) {
            championship.addWeekend(track, teams);
        }
        for(Team team: teams){
            championship.getTeamsLeaderboard().add(new ChampionshipLeaderboardEntry(team,0));
            ArrayList<Driver> drivers = team.getCurrentDrivers();
            championship.getDriversLeaderboard().add(new ChampionshipLeaderboardEntry(drivers.get(0),0));
            championship.getDriversLeaderboard().add(new ChampionshipLeaderboardEntry(drivers.get(1),0));
        }

        championship.nextWeekend(true);

        return championship;
    }

    public Championship loadChampionship(int slot) throws Exception {

        SaveManager saveManager = SaveManager.getReference();

        boolean valid = checkSaveSlot(slot);
        if(valid) {

            DatabaseParser databaseParser = DatabaseParser.getReference();

            databaseParser.prepareDatabase("Save files/save_" + slot,
                    Consts.originalDatabaseFiles, Consts.originalDatabaseTypes);
            return saveManager.loadChampionship(slot);
        }
        throw new Exception("Championship not found");
    }

    public void deleteChampionship(int slot) throws Exception {

        SaveManager saveManager = SaveManager.getReference();

        boolean valid = checkSaveSlot(slot);
        if(valid) {
            saveManager.deleteChampionship(slot);
        }
        else throw new Exception("Not a valid slot");
    }
}
