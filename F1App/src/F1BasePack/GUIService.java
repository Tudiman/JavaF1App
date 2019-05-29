package F1BasePack;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIService {

    private static GUIService reference = new GUIService();

    private JFrame frame = new JFrame("F1 2019 Championship Simulator");

    private GUIService() {
        frame.setLayout(null);
        frame.setSize(1500,1000);
        frame.setVisible(true);
    }

    public static GUIService getReference() {
        return reference;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void loadStartMenu() {
        Color fontColor = new Color(255,255,220);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);
        String text = "My F1 Simulator ";
        String[] texts = {"Create Championship","Load Championship","Delete Championship","Exit"};
        ActionListener[] actions = {a-> {
            int[] scoring = {25,18,15,12,10,8,6,4,2,1};
            loadChampionshipMenu(ChampionshipHandler.getReference().newChampionship(scoring));
        },
                a->GUIService.getReference().loadChampionshipLoader(),
                a->GUIService.getReference().loadChampionshipDeleter(),
                a->GUIService.getReference().getFrame().dispose()};

        GUIHelper.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 300, 4,
                300, 250, texts, font2, fontColor, border, actions);

    }

    public void loadChampionshipLoader() {

        Color fontColor = new Color(255,255,220);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        ArrayList<Integer> slots = ChampionshipHandler.getReference().getSlotsList();

        String text;
        String[] texts;
        ActionListener[] actions;

        int length = slots.size();

        if(length == 0) {
            text = "No save files found.";
        }
        else {
            text = "<html>Choose a save file to load.<br/>Slots:";

            for(int slot: slots)
                text += "<br/>" + (slot + 1);

            text += "</html>";
        }

        texts = new String[length + 1];
        actions = new ActionListener[length + 1];

        for(int i = 0; i < length; i++) {
            texts[i] = "Slot " + (slots.get(i) + 1);
            actions[i] = a->
            {
                try {
                    ChampionshipHandler handler = ChampionshipHandler.getReference();
                    JButton source = (JButton)a.getSource();
                    int which = (Integer)source.getClientProperty("which");
                    Championship championship = handler.loadChampionship(handler.getSlotsList().get(which));
                    GUIService.getReference().loadChampionshipMenu(championship);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            };
        }

        texts[length] = "Back";
        actions[length] = a->GUIService.getReference().loadStartMenu();

        int buttonCount = length + 1;
        int delimiterHeight = 300 + length * 20;
        int buttonWidth = 300 - length * 15;
        GUIHelper.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, delimiterHeight, buttonCount,
                buttonWidth, 250, texts, font2, fontColor, border, actions);
    }

    public void loadChampionshipDeleter() {

        Color fontColor = new Color(255,255,220);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        ArrayList<Integer> slots = ChampionshipHandler.getReference().getSlotsList();

        String text;
        String[] texts;
        ActionListener[] actions;

        int length = slots.size();

        if(length == 0) {
            text = "No save files found.";
        }
        else {
            text = "<html>Choose a save file to delete.<br/>Slots:";

            for(int slot: slots)
                text += "<br/>" + (slot + 1);

            text += "</html>";
        }

        texts = new String[length + 1];
        actions = new ActionListener[length + 1];

        for(int i = 0; i < length; i++) {
            texts[i] = "Slot " + (slots.get(i) + 1);
            actions[i] = a->
            {
                try {
                    ChampionshipHandler handler = ChampionshipHandler.getReference();
                    JButton source = (JButton)a.getSource();
                    int which = (Integer)source.getClientProperty("which");
                    handler.deleteChampionship(handler.getSlotsList().get(which));
                    GUIService.getReference().loadChampionshipDeleter();
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            };
        }

        texts[length] = "Back";
        actions[length] = a->GUIService.getReference().loadStartMenu();

        int buttonCount = length + 1;
        int delimiterHeight = 300 + length * 20;
        int buttonWidth = 300 - length * 15;
        GUIHelper.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, delimiterHeight, buttonCount,
                buttonWidth, 250, texts, font2, fontColor, border, actions);

    }

    public void loadChampionshipMenu(Championship championship) {

        ChampionshipHandler.getReference().setCurrentChampionship(championship);

        Color fontColor = new Color(255,255,220);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = "<html>Championship " + (championship.getSlot() + 1) + "<br/>Current progress: ";
        String[] texts;
        ActionListener[] actions;
        boolean endFlag =  championship.getProgress() == championship.getWeekends().size();

        if(endFlag) {
            text += "Finished.</html>";
            texts = new String[]{"Leaderboards","Weekends list","Back"};
//            actions = new ActionListener[]{
//                    a->GUIService.getReference().loadLeaderboardMenu(
//                            ChampionshipHandler.getReference().getCurrentChampionship()),
//                    a->GUIService.getReference().loadWeekendScrollMenu(
//                            ChampionshipHandler.getReference().getCurrentChampionship()),
//                    a->GUIService.getReference().loadStartMenu()
//            };
            actions = new ActionListener[]{a->{},a->{},a->{}};
        }
        else {
            text += "Weekend " + (championship.getProgress() + 1) + "</html";
            texts = new String[]{"Leaderboards","Weekend Details","Weekends list","Advance","Back"};
//            actions = new ActionListener[]{
//                    a->GUIService.getReference().loadLeaderboardMenu(
//                            ChampionshipHandler.getReference().getCurrentChampionship()),
//                    a->GUIService.getReference().loadWeekendMenu(
//                            ChampionshipHandler.getReference().getCurrentChampionship()),
//                    a->GUIService.getReference().loadWeekendScrollMenu(
//                            ChampionshipHandler.getReference().getCurrentChampionship()),
//                    a->GUIService.getReference().loadWeekendProgressMenu(
//                            ChampionshipHandler.getReference().getCurrentChampionship()),
//                    a->GUIService.getReference().loadStartMenu()
//            };
            actions = new ActionListener[]{a->GUIService.getReference().loadLeaderboardMenu(),a->{},a->{},a->{},a->
                    GUIService.getReference().loadStartMenu()};
        }

        GUIHelper.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 400, texts.length,
                200, 250, texts, font2, fontColor, border, actions);

    }

    public void loadInfoListPane(String title, ArrayList<?> list) {

        Color fontColor = new Color(255,255,220);
        Font font1 = new Font("Helvetica",Font.BOLD,24);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = "<html>" + title + "<br/>";
        for(int i = 0; i < list.size(); i++)
            text += "<br/>" + (i + 1) + ": " + list.get(i);

        text += "</html>";

        String[] texts = {"Back"};
        ActionListener[] actions = {a->GUIService.getReference().loadLeaderboardMenu()};

        GUIHelper.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 700, texts.length,
                300, 150, texts, font2, fontColor, border, actions);

    }

    public void loadLeaderboardMenu() {

        Color fontColor = new Color(255,255,220);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = "Choose a leaderboard to display.";
        String[] texts = {"Drivers Leaderboard","Teams Leaderboard","Back"};
        ActionListener[] actions = {
                a->GUIService.getReference().loadInfoListPane("Drivers Leaderboard",
                    ChampionshipHandler.getReference().getCurrentChampionship().getDriversLeaderboard()),
                a->GUIService.getReference().loadInfoListPane("Teams Leaderboard",
                        ChampionshipHandler.getReference().getCurrentChampionship().getTeamsLeaderboard()),
                a->GUIService.getReference().loadChampionshipMenu(
                        ChampionshipHandler.getReference().getCurrentChampionship())
        };
        GUIHelper.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 400, texts.length,
                300, 250, texts, font2, fontColor, border, actions);
    }

    public void loadWeekendMenu() {

    }

}
