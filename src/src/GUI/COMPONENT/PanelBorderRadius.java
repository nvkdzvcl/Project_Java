package GUI.COMPONENT;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PanelBorderRadius extends JPanel {

    int shadowSize = 3;
    Color howerBackGroundColor = new Color(187, 222, 251);

    public PanelBorderRadius() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs){
        createShadow(grphcs);
    }

    private void createShadow(Graphics grphcs){
        Graphics2D graphics2D = (Graphics2D) grphcs;
        int size = shadowSize * 2;
        int x = 0;
        int y = 0;
        int width = getWidth() - size;
        int height = getHeight() - size;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = img.createGraphics();
        graphics2D.setBackground(howerBackGroundColor);

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.fillRoundRect(0, 0, width, height, 15, 15);

        graphics2D.drawImage(img, x, y, null);
    }
}
