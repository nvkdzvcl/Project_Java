package GUI.COMPONENT;

import javax.swing.*;
import java.awt.*;

public class ButtonToolBar extends JButton {

    String permission;

    public ButtonToolBar(String text, String icon, String permission){

        this.permission = permission;
        this.setForeground(new Color(1, 88, 155));
        this.setIcon(new ImageIcon());
        this.setText(text);
        this.setFocusable(false);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getPermission(){
        return this.permission;
    }
}
