package F1BasePack;

import F1BasePack.Utility.*;

public class Prize {

    private String title;
    private String description;
    private Date dateAcquired;

    public Prize(String title, String description, Date dateAcquired) {
        this.title = title;
        this.description = description;
        this.dateAcquired = dateAcquired;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    @Override
    public String toString() {
        return title + ", " + dateAcquired;
    }
}
