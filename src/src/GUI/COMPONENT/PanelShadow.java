package GUI.COMPONENT;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelShadow extends JPanel {

    int shadowSize = 3;
    float shadowOpacity = 0.3f;
    private Color shadowColor = Color.BLACK;

    JPanel iconBackground;
    JLabel lblIcon, lblTitle, lblContent;
    Color howerBackgroundColor = new Color(187, 222, 251);

    Color mainColor = Color.WHITE;
    Color fontColor = new Color(0, 151, 178);
    Color backgroundColor = new Color(240, 247, 250);
    Color howerFontColor = new Color(225, 230, 232);

    public PanelShadow(){
        setOpaque(false);
    }

    public PanelShadow(String linkIcon, String title, String content){

        this.setPreferredSize(new Dimension(300, 450));
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        this.setBorder(new EmptyBorder(10, 0, 0, 0));

        iconBackground = new JPanel();
        iconBackground.setPreferredSize(new Dimension(250, 150));
        iconBackground.setBackground(backgroundColor);
        iconBackground.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        lblIcon = new JLabel();
        lblIcon.setIcon(new ImageIcon());
        iconBackground.add(lblIcon);

        this.add(iconBackground);

        lblTitle = new JLabel(title.toUpperCase());
        lblTitle.setForeground(fontColor);
        this.add(lblTitle);

        lblContent = new JLabel(content);
        lblContent.setForeground(Color.GRAY);
        this.add(lblContent);
    }

}
