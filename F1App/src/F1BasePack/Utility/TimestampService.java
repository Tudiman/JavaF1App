package F1BasePack.Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalTime;

public class TimestampService {

    private static TimestampService reference = new TimestampService();

    private TimestampService() {}

    public static TimestampService getReference() {
        return reference;
    }

    public void postTimestamp(String what) {

        BufferedWriter bw = null;

        try {
            File destination = new File(Consts.generalFilesPath + "Timestamps.csv");
            if(!destination.exists())
                destination.createNewFile();

            bw = new BufferedWriter(new FileWriter(destination,true));
            bw.write(what + " at " + LocalTime.now() + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(bw != null)
                    bw.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
