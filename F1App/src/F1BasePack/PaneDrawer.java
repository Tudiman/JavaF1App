package F1BasePack;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaneDrawer {

    private static PaneDrawer reference = new PaneDrawer();

    private PaneDrawer() {

    }

    public static PaneDrawer getReference() {
        return reference;
    }

    public void createPane(Color bgColor, String labelText, Font labelFont, Color labelFontColor, int delimiterHeight,
                           int buttonCount, Color baseColor, Color hoverColor, Color pressColor,
                           int buttonWidth, int buttonHeight, String[] buttonTexts, Font buttonFont,
                           Color buttonFontColor, Color fontHoverColor, LineBorder border, ActionListener[] actions){

        JFrame frame = GUIService.getReference().getFrame();

        clearFrame(frame);
        frame.getContentPane().setBackground(bgColor);

        frame.add(simpleLabel(labelText, labelFont, labelFontColor, 0, 0, frame.getWidth(), delimiterHeight));

        JButton[] buttons = linearButtonLayout(buttonCount, baseColor, hoverColor, pressColor, buttonWidth,
                frame.getWidth(), frame.getHeight(), buttonHeight, delimiterHeight, buttonTexts, buttonFont,
                buttonFontColor, fontHoverColor, border, actions);
        for(JButton button: buttons)
            frame.add(button);

    }

    public void clearFrame(JFrame frame) {

        frame.getContentPane().removeAll();
        frame.repaint();

    }

    public JLabel simpleLabel(String text, Font font, Color color, int top, int left, int width, int height) {

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(font);
        label.setBounds(top, left, width, height);
        return label;
    }

    public JButton[] linearButtonLayout(int count, Color baseColor, Color hoverColor, Color pressColor,
                                        int buttonWidth, int frameWidth, int frameHeight, int buttonHeight,
                                        int yPos, String[] texts, Font font, Color fontColor, Color fontHoverColor,
                                        LineBorder border, ActionListener[] actions) {

        JButton[] buttons = new JButton[count];

        int itemWidth = buttonWidth + 2 * border.getThickness();
        int itemHeight = buttonHeight + 2 * border.getThickness();
        int rowsQ = count / 5 + 1;
        int lastRowQ = count % 5;
        int totalCount = 0;
        double verticalSpacing = (frameHeight - yPos - rowsQ * itemHeight) / (rowsQ + 1);

        for(int j = 0; j < rowsQ; j++) {

            int limit = j != rowsQ - 1 ? 5 : lastRowQ;

            double horizontalSpacing = (frameWidth - itemWidth * limit) / (limit + 1);
            int computedYPos = (int)(yPos + verticalSpacing * (j + 1) + itemHeight * j);

            for (int i = 0; i < limit; i++) {

                int computedXPos = (int)(horizontalSpacing * (i + 1) + itemWidth * i);

                JButton button = new JButton(texts[totalCount]);
                button.setBounds(computedXPos, computedYPos, buttonWidth, buttonHeight);
                button.setFont(font);
                button.setForeground(fontColor);
                button.setContentAreaFilled(true);
                button.setBackground(baseColor);
                button.setBorder(border);
                button.setFocusPainted(false);
                button.addActionListener(actions[totalCount]);

                button.getModel().addChangeListener(
                        e->{
                            ButtonModel model = (ButtonModel)e.getSource();
                            if(model.isRollover()) {
                                button.setForeground(fontHoverColor);
                                if (model.isPressed()) {
                                    button.setBackground(pressColor);
                                } else {
                                    button.setBackground(hoverColor);
                                }
                            }
                            else {
                                button.setBackground(baseColor);
                                button.setForeground(fontColor);
                            }
                        }
                        );

                buttons[totalCount] = button;
                buttons[totalCount].putClientProperty("which", totalCount);

                totalCount++;
            }
        }

        return buttons;
    }

    public void changePane(JFrame frame, Color bgColor, String labelText, Font labelFont, Color labelFontColor,
                           int delimiterHeight, int buttonCount, Color baseColor, Color hoverColor, Color pressColor,
                           int buttonWidth, int buttonHeight, String[] buttonTexts, Font buttonFont, Color buttonFontColor,
                           Color fontHoverColor, LineBorder border, ActionListener[] actions) {
        clearFrame(frame);
        createPane(bgColor, labelText, labelFont, labelFontColor, delimiterHeight, buttonCount, baseColor,
                hoverColor, pressColor, buttonWidth, buttonHeight, buttonTexts, buttonFont, buttonFontColor,
                fontHoverColor, border, actions);
    }

}
