package F1BasePack;

import F1BasePack.Utility.*;

import java.util.ArrayList;

public class Weekend {

    private Track track;
    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<Session> practiceSessions = new ArrayList<>();
    private ArrayList<Session> qualifyingSessions = new ArrayList<>();
    private Session race;
    private int progress = -1;

    public Weekend() {
    }

    public Weekend(Track track, ArrayList<Team> teams) {
        this.track = track;
        this.teams = teams;
    }

    public Weekend(Track track, ArrayList<Team> teams, int progress) {
        this.track = track;
        this.teams = teams;
        this.progress = progress;
    }

    public Track getTrack() {
        return track;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Session> getPracticeSessions() {
        return practiceSessions;
    }

    public ArrayList<Session> getQualifyingSessions() {
        return qualifyingSessions;
    }

    public Session getRace() {
        return race;
    }

    public int getProgress() {
        return progress;
    }

    public Session getCurrentSession() {
        if(progress < 3)
            return practiceSessions.get(progress%3);
        else if(progress < 6)
            return qualifyingSessions.get(progress%3);
        else return race;
    }

    public void setPracticeSessions(ArrayList<Session> practiceSessions) {
        this.practiceSessions = practiceSessions;
    }

    public void setQualifyingSessions(ArrayList<Session> qualifyingSessions) {
        this.qualifyingSessions = qualifyingSessions;
    }

    public void setRace(Session race) {
        this.race = race;
    }

    @Override
    public String toString() {
        return track.getCountry().getReferralName() + " GP";
    }

    public void nextSession(int slot, int which, boolean whole) {

        progress++;
        SaveManager.getReference().saveWeekend(this,slot,which,whole);
    }
}
