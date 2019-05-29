package F1BasePack.Utility;

public class Interval {

    private Date start;
    private Date end = null;

    public Interval(Date start) {
        this.start = start;
    }

    public Interval(Date start, Date end) {
        if(start.compareTo(end) > 0)
            throw new Error("bad end date input for interval, end date can't be earlier than start date");
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        String displayEndDate = "TBD";
        if(end != null)
            displayEndDate = "" + end;
        return start + "-" + displayEndDate;
    }
}
