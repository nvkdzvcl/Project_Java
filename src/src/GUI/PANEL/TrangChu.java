package GUI.PANEL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TrangChu extends JPanel {

    public TrangChu() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);

        JLabel mainTitle = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN QUẦN ÁO");
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainTitle.setForeground(new Color(0, 102, 204));

        JLabel subTitle = new JLabel("-Hãy cảm nhận nỗi đau, suy ngẫm về nỗi đau, và học cách chấp nhận nó. Những ai không biết về nỗi đau sẽ không biết thế nào là hòa bình thật sự-");
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitle.setForeground(Color.DARK_GRAY);

        JLabel companyLabel = new JLabel("Tập đoàn 4 chàng lính ngự lâm");
        companyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        companyLabel.setForeground(Color.GRAY);

        panel.add(mainTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(subTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(companyLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 30, 0));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(false);

        panel.add(createInfoBox(
                "/icon/tutin.png",
                "Thanh lịch – Tự tin – Nổi bật mỗi ngày",
                "Bộ sưu tập thời trang công sở mới nhất từ [Tên Thương Hiệu]..."
        ));
        panel.add(createInfoBox(
                "/icon/catinh.png",
                "Phá cách – Cá tính – Không giới hạn",
                "Thể hiện cá tính riêng qua từng outfit với dòng sản phẩm thời trang đường phố..."
        ));
        panel.add(createInfoBox(
                "/icon/matme.png",
                "Mùa hè rực rỡ cùng phong cách thời trang mát mẻ",
                "Đón nắng cùng BST hè cực chất từ [Tên Thương Hiệu]..."
        ));

        return panel;
    }

    private JPanel createInfoBox(String iconPath, String title, String description) {
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220)),
                new EmptyBorder(20, 20, 20, 20)
        ));
        boxPanel.setBackground(new Color(248, 249, 250));

        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image scaledImg = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            iconLabel.setText("[ICON]");
            System.err.println("Không thể load icon: " + iconPath);
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(52, 58, 64));

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setBackground(new Color(0, 0, 0, 0));
        descriptionArea.setForeground(Color.DARK_GRAY);
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionArea.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));

        boxPanel.add(iconLabel);
        boxPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        boxPanel.add(titleLabel);
        boxPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        boxPanel.add(descriptionArea);

        return boxPanel;
    }

}
