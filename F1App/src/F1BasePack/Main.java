package F1BasePack;

import F1BasePack.Utility.ConnectionManager;
import F1BasePack.Utility.Consts;
import F1BasePack.Utility.SaveManager;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    static public void main(String[] args) {

        //IOService.getReference().loadStartMenu();
        GUIService.getReference().loadStartMenu();
    }

}
