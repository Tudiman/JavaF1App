package F1BasePack.Utility;

import F1BasePack.Session;
import F1BasePack.Weekend;

public class EventReferenceConsts {

    private static Weekend weekend;
    private static Session session;

    public static Weekend getWeekend() {
        return weekend;
    }

    public static void setWeekend(Weekend weekend) {
        EventReferenceConsts.weekend = weekend;
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        EventReferenceConsts.session = session;
    }
}
