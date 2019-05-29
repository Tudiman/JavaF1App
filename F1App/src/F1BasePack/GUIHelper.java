package F1BasePack;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIHelper {

    private static GUIHelper reference = new GUIHelper();

    private GUIHelper() {

    }

    public static GUIHelper getReference() {
        return reference;
    }

    public void createPane(Color bgColor, String labelText, Font labelFont, Color labelFontColor, int delimiterHeight,
                           int buttonCount, int buttonWidth, int buttonHeight,
                           String[] buttonTexts, Font buttonFont, Color buttonFontColor, LineBorder border,
                           ActionListener[] actions){

        JFrame frame = GUIService.getReference().getFrame();

        clearFrame(frame);
        frame.getContentPane().setBackground(bgColor);

        frame.add(simpleLabel(labelText, labelFont, labelFontColor, 0, 0, frame.getWidth(), delimiterHeight));

        JButton[] buttons = linearButtonLayout(buttonCount, buttonWidth, frame.getWidth(), frame.getHeight(),
                buttonHeight, delimiterHeight, buttonTexts, buttonFont, buttonFontColor, border, actions);
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

    public JButton[] linearButtonLayout(int count, int buttonWidth, int frameWidth, int frameHeight, int height,
                                        int yPos, String[] texts, Font font, Color fontColor, LineBorder border,
                                        ActionListener[] actions) {

        JButton[] buttons = new JButton[count];

        int itemWidth = buttonWidth + 2 * border.getThickness();
        int spacing = (frameWidth - itemWidth * count) / (count + 1);
        int computedSpacing = spacing;
        int computedYPos = yPos + (frameHeight - yPos - height) / 2;

        for(int i = 0; i < count; i++) {

            JButton button = new JButton(texts[i]);
            button.setBounds(computedSpacing, computedYPos, buttonWidth, height);
            button.setFont(font);
            button.setForeground(fontColor);
            button.setContentAreaFilled(false);
            button.setBorder(border);
            button.setFocusPainted(false);
            button.addActionListener(actions[i]);
            buttons[i] = button;
            buttons[i].putClientProperty("which",i);

            computedSpacing += spacing + itemWidth;

        }

        return buttons;
    }

    public void changePane(JFrame frame, Color bgColor, String labelText, Font labelFont, Color labelFontColor,
                            int delimiterHeight, int buttonCount, int buttonWidth, int buttonHeight,
                            String[] buttonTexts, Font buttonFont, Color buttonFontColor, LineBorder border,
                            ActionListener[] actions) {
        clearFrame(frame);
        createPane(Color.BLACK, labelText, labelFont, labelFontColor, delimiterHeight, buttonCount,
                buttonWidth, buttonHeight, buttonTexts, buttonFont, buttonFontColor, border, actions);
    }

}
