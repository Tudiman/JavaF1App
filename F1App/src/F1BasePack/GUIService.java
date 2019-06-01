package F1BasePack;

import F1BasePack.Utility.EventReferenceConsts;

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

    public void loadSingleItemPane(String text, ActionListener action) {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String[] texts = {"Back"};
        ActionListener[] actions = {action};

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 500,
                texts.length, baseColor, hoverColor, pressColor, 300, 150, texts, font2,
                fontColor, fontHoverColor, border, actions);

    }

    public void loadInfoListPane(String title, ArrayList<?> list, ActionListener action) {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,24);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = "<html>" + title + "<br/>";
        for(int i = 0; i < list.size(); i++)
            text += "<br/>" + (i + 1) + ": " + list.get(i);

        text += "</html>";

        String[] texts = {"Back"};
        ActionListener[] actions = {action};

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 700,
                texts.length, baseColor, hoverColor, pressColor, 300, 150, texts,
                font2, fontColor, fontHoverColor, border, actions);

    }

    public void loadStartMenu() {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
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

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 300,
                texts.length, baseColor, hoverColor, pressColor, 300, 250, texts,
                font2, fontColor, fontHoverColor, border, actions);

    }

    public void loadChampionshipLoader() {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
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
        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, delimiterHeight,
                buttonCount, baseColor, hoverColor, pressColor, buttonWidth, 150, texts, font2,
                fontColor, fontHoverColor, border, actions);
    }

    public void loadChampionshipDeleter() {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
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
        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, delimiterHeight,
                buttonCount, baseColor, hoverColor, pressColor, buttonWidth, 150, texts, font2,
                fontColor, fontHoverColor, border, actions);

    }

    public void loadChampionshipMenu(Championship championship) {

        ChampionshipHandler.getReference().setCurrentChampionship(championship);

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = "<html>Championship " + (championship.getSlot() + 1) + "<br/>Current progress: ";
        String[] texts;
        ActionListener[] actions;
        ActionListener[] commonActions = {
                a->GUIService.getReference().loadLeaderboardMenu(),
                a->GUIService.getReference().loadWeekendScrollMenu(
                        ChampionshipHandler.getReference().getCurrentChampionship()),
                a->GUIService.getReference().loadStartMenu()
        };

        if(championship.getProgress() == championship.getWeekends().size()) {
            text += "Finished.</html>";
            texts = new String[]{"Leaderboards","Weekends list","Back"};
            actions = commonActions;
        }
        else {
            text += "Weekend " + (championship.getProgress() + 1) + "</html";
            texts = new String[]{"Leaderboards","Weekend Details","Weekends List","Advance","Back"};
            actions = new ActionListener[]{
                    commonActions[0],
                    a->loadWeekendMenu(
                            ChampionshipHandler.getReference().getCurrentChampionship().getCurrentWeekend()
                    ),
                    commonActions[1],
                    a->loadWeekendAdvanceMenu(championship),
                    commonActions[2]
            };
        }

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 400,
                texts.length, baseColor, hoverColor, pressColor, 200, 250, texts,
                font2, fontColor, fontHoverColor, border, actions);

    }

    public void loadLeaderboardMenu() {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = "Choose a leaderboard to display.";
        String[] texts = {"Drivers Leaderboard","Teams Leaderboard","Back"};
        ActionListener[] actions = {
                a->GUIService.getReference().loadInfoListPane("Drivers Leaderboard",
                    ChampionshipHandler.getReference().getCurrentChampionship().getDriversLeaderboard(),
                        actionEvent->GUIService.getReference().loadLeaderboardMenu()),
                a->GUIService.getReference().loadInfoListPane("Teams Leaderboard",
                        ChampionshipHandler.getReference().getCurrentChampionship().getTeamsLeaderboard(),
                        actionEvent->GUIService.getReference().loadLeaderboardMenu()),
                a->GUIService.getReference().loadChampionshipMenu(
                        ChampionshipHandler.getReference().getCurrentChampionship())
        };
        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 400,
                texts.length, baseColor, hoverColor, pressColor, 300, 250, texts,
                font2, fontColor, fontHoverColor, border, actions);
    }

    public void loadWeekendMenu(Weekend weekend) {

        EventReferenceConsts.setWeekend(weekend);

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = weekend.toString();

        String[] texts;
        ActionListener[] actions;

        ActionListener[] commonActions = {
                a -> GUIService.getReference().loadSingleItemPane(
                        EventReferenceConsts.getWeekend().getTrack().toString(),
                        actionEvent -> GUIService.getReference().loadWeekendMenu(EventReferenceConsts.getWeekend())
                ),
                a -> GUIService.getReference().loadInfoListPane("Participants",
                        EventReferenceConsts.getWeekend().getTeams(),
                        actionEvent -> GUIService.getReference().loadWeekendMenu(EventReferenceConsts.getWeekend())
                ),
                a -> GUIService.getReference().loadSessionScrollMenu(
                        EventReferenceConsts.getWeekend()
                ),
                a -> GUIService.getReference().loadChampionshipMenu(
                        ChampionshipHandler.getReference().getCurrentChampionship()
                )
        };

        if(weekend.getProgress() == -1) {
            texts = new String[]{"Track", "Participants", "Back"};

            actions = new ActionListener[]{commonActions[0], commonActions[1], commonActions[3]};
        }
        else if(weekend.getProgress() == 7) {
            texts = new String[]{"Track", "Participants", "Sessions List", "Back"};
            actions = commonActions;
        }
        else {
            texts = new String[]{"Track", "Participants", "Current Session", "Sessions List", "Back"};

            actions = new ActionListener[]{
                    commonActions[0],
                    commonActions[1],
                    a -> GUIService.getReference().loadSessionMenu(
                            EventReferenceConsts.getWeekend().getCurrentSession()
                    ),
                    commonActions[2],
                    commonActions[3]
            };
        }

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 400,
                texts.length, baseColor, hoverColor, pressColor, 300, 250, texts,
                font2, fontColor, fontHoverColor, border, actions);
    }

    public void loadSessionMenu(Session session) {

        EventReferenceConsts.setSession(session);

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String text = session.toString();

        String[] texts;
        ActionListener[] actions;

        ActionListener[] commonActions = {
                a -> GUIService.getReference().loadSingleItemPane(
                        EventReferenceConsts.getSession().getTimeOfDay() +
                                ", " + EventReferenceConsts.getSession().getWeather(),
                        actionEvent -> GUIService.getReference().loadSessionMenu(EventReferenceConsts.getSession())
                ),
                a -> GUIService.getReference().loadInfoListPane("Participants",
                        EventReferenceConsts.getSession().getTeams(),
                        actionEvent -> GUIService.getReference().loadSessionMenu(EventReferenceConsts.getSession())
                ),
                a -> GUIService.getReference().loadWeekendMenu(EventReferenceConsts.getWeekend())
        };

        if(session.getLeaderboard().size() == 0) {
            texts = new String[]{"Weather and time of day", "Participants", "Back"};

            actions = new ActionListener[]{commonActions[0], commonActions[1], commonActions[2]};
        }
        else {
            texts = new String[]{"Weather and time of day", "Participants", "Leaderboard", "Back"};

            actions = new ActionListener[]{
                    commonActions[0],
                    commonActions[1],
                    a->GUIService.getReference().loadInfoListPane("Leaderboard",
                            EventReferenceConsts.getSession().getLeaderboard(),
                            actionEvent->GUIService.getReference().loadSessionMenu(EventReferenceConsts.getSession())),
                    commonActions[2]
            };
        }

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 400,
                texts.length, baseColor, hoverColor, pressColor, 300, 250, texts,
                font2, fontColor, fontHoverColor, border, actions);

    }

    public void loadWeekendScrollMenu(Championship championship) {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        int size = championship.getWeekends().size();

        String text = "Choose a weekend, total: " + size;
        String[] texts = new String[size + 1];
        ActionListener[] actions = new ActionListener[size + 1];

        for(int i = 0; i < size; i++) {
            texts[i] = "Weekend " + (i + 1);
            actions[i] = a-> {
                int which = (Integer)((JButton)a.getSource()).getClientProperty("which");
                GUIService.getReference().loadWeekendMenu(
                        ChampionshipHandler.getReference().getCurrentChampionship().getWeekends().get(which)
                );

            };
        }
        texts[size] = "Back";
        actions[size] = a->GUIService.getReference().loadChampionshipMenu(
                ChampionshipHandler.getReference().getCurrentChampionship()
        );

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 300,
                texts.length, baseColor, hoverColor, pressColor, 200, 100, texts,
                font2, fontColor, fontHoverColor, border, actions);
    }

    public void loadSessionScrollMenu(Weekend weekend) {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        int size = 7;

        String text = "Choose a session.";
        String[] texts = new String[size + 1];
        ActionListener[] actions = new ActionListener[size + 1];
        int counter = 0;
        for(int i = 0; i < 3; i++) {
            texts[counter] = "Practice Session " + (i + 1);
            actions[counter] = a-> {
                int which = (Integer)((JButton)a.getSource()).getClientProperty("which");
                GUIService.getReference().loadSessionMenu(
                        EventReferenceConsts.getWeekend().getPracticeSessions().get(which)
                );

            };
            counter++;
        }
        for(int i = 0; i < 3; i++) {
            texts[counter] = "Qualifying Session " + (i + 1);
            actions[counter] = a-> {
                int which = (Integer)((JButton)a.getSource()).getClientProperty("which");
                GUIService.getReference().loadSessionMenu(
                        EventReferenceConsts.getWeekend().getQualifyingSessions().get(which - 3)
                );

            };
            counter++;
        }
        texts[counter] = "Race";
        actions[counter] = a->GUIService.getReference().loadSessionMenu(
                EventReferenceConsts.getWeekend().getRace()
        );
        texts[size] = "Back";
        actions[size] = a->GUIService.getReference().loadWeekendMenu(
                EventReferenceConsts.getWeekend()
        );

        PaneDrawer.getReference().changePane(frame, Color.BLACK, text, font1, fontColor, 300,
                texts.length, baseColor, hoverColor, pressColor, 250, 100, texts,
                font2, fontColor, fontHoverColor, border, actions);
    }

    public void loadWeekendAdvanceMenu(Championship championship) {

        Color baseColor = new Color(0,0,0);
        Color hoverColor = new Color(234,234,213);
        Color pressColor = new Color(142,142,129);
        Color fontColor = new Color(255,255,220);
        Color fontHoverColor = new Color(61,61,56);
        Font font1 = new Font("Helvetica",Font.BOLD,40);
        Font font2 = new Font("Helvetica",Font.BOLD,24);
        LineBorder border = new LineBorder(fontColor,5);

        String[] nextSession = {"Practice 1","Practice 2","Practice 3",
                "Qualifying 1","Qualifying 2","Qualifying 3","Race"};
        Weekend weekend = championship.getCurrentWeekend();

        String[] texts;
        ActionListener[] actions;
        ActionListener[] commonActions = {
                a->loadInfoListPane(nextSession[weekend.getProgress()],
                            WeekendHandler.getReference().runSession(
                            championship.getCurrentWeekend(),
                            championship.getSlot(),
                            championship.getProgress()
                            ), actionEvent->loadWeekendAdvanceMenu(championship)),
                a->loadChampionshipMenu(championship)
        };
        if(weekend.getProgress() == 6) {
            texts = new String[]{"Start " + nextSession[weekend.getProgress()], "Starting Grid", "Back"};
            actions = new ActionListener[]{
                    a->loadInfoListPane("Race",
                            WeekendHandler.getReference().runSession(weekend, championship.getSlot(),
                                    championship.getProgress()),
                            actionEvent->{
                                championship.actualiseLeaderboards();
                                championship.nextWeekend(false);
                                loadChampionshipMenu(championship);
                            }),
                    a->loadInfoListPane("Starting Grid",
                            WeekendHandler.getReference().getStartingGrid(weekend),
                            actionEvent->loadWeekendAdvanceMenu(championship)),
                    a->loadChampionshipMenu(championship)
            };
        }
        else {
            texts = new String[]{"Start " + nextSession[weekend.getProgress()],"Back"};
            actions = commonActions;
        }

        PaneDrawer.getReference().changePane(frame, Color.BLACK, nextSession[weekend.getProgress()],
                font1, fontColor, 300, texts.length, baseColor, hoverColor, pressColor,
                250, 100, texts, font2, fontColor, fontHoverColor, border, actions);
    }

}
