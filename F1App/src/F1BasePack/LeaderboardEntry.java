package F1BasePack;

public abstract class LeaderboardEntry <T>{

    private T entrant;

    LeaderboardEntry(T entrant) {
        this.entrant = entrant;
    }

    public T getEntrant() {
        return entrant;
    }

}
