package GUI.COMPONENT;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ItemTaskbar extends JPanel implements MouseListener {

    Color fontColor = new Color(96, 125, 139);

    JLabel lblIcon, pnlContent, pnlQuantity, pnlContent1;
    JLabel img;

    JPanel right;

    public boolean isSelected;

    public ItemTaskbar(String linkIcon, String content){

        this.setLayout(new FlowLayout(1, 10, 7));
        this.setPreferredSize(new Dimension(225, 45));
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);

        lblIcon = new JLabel();
        lblIcon.setBorder(new EmptyBorder(0, 10, 0, 0));
        lblIcon.setPreferredSize(new Dimension(45, 30));
        lblIcon.setIcon(new ImageIcon());
        this.add(lblIcon);

        pnlContent = new JLabel(content);
        pnlContent.setPreferredSize(new Dimension(155, 30));
        pnlContent.setForeground(Color.BLACK);
        this.add(pnlContent);
    }

    public ItemTaskbar(String linkIcon, String content1, String content2){

        this.setLayout(new FlowLayout(0, 20, 50));
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);

        lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(110, 110));
        lblIcon.setIcon(new ImageIcon());
        this.add(lblIcon);

        pnlContent = new JLabel(content1);
        pnlContent.setPreferredSize(new Dimension(170, 30));
        pnlContent.setForeground(fontColor);
        this.add(pnlContent);
    }

    public ItemTaskbar(String linkImg, String tenSP, int quantity){

        this.setLayout(new BorderLayout(0, 0));
        this.setPreferredSize(new Dimension(380, 60));
        this.setBackground(Color.WHITE);

        img = new JLabel("");
        img.setIcon(new ImageIcon());
        this.add(img, BorderLayout.WEST);

        right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        right.setBorder(new EmptyBorder(10, 10, 0, 0));
        right.setOpaque(false);
        this.add(right, BorderLayout.CENTER);

        pnlContent = new JLabel(tenSP);
        pnlContent.setForeground(Color.BLACK);
        right.add(pnlContent);

        pnlQuantity = new JLabel("Số lượng: " + quantity);
        pnlQuantity.setPreferredSize(new Dimension(350, 20));
        pnlQuantity.setForeground(Color.GRAY);
        right.add(pnlQuantity);
    }

    public ItemTaskbar(String linkIcon, String content1, String content2, int n){

        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);

        lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(100, 100));
        lblIcon.setBorder(new EmptyBorder(0, 20, 0, 0));

        lblIcon.setIcon(new ImageIcon());
        this.add(lblIcon, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(0, 10, 0));
        centerPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        centerPanel.setOpaque(false);
        this.add(centerPanel);

        pnlContent = new JLabel(content1);
        pnlContent.setPreferredSize(new Dimension(250, 30));
        pnlContent.setForeground(fontColor);
        centerPanel.add(pnlContent);

        pnlContent1 = new JLabel(content2);
        pnlContent1.setPreferredSize(new Dimension(250, 30));
        pnlContent1.setForeground(fontColor);
        centerPanel.add(pnlContent1);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent){
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent){
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent){
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent){
        if(!isSelected){
            setBackground(new Color(235, 237, 240));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent){
        if(!isSelected){
            setBackground(Color.WHITE);
        }
    }
}
