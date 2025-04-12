package GUI.PANEL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class NhaCungCap extends JPanel {

    public NhaCungCap() {
        // --- Panel chính chứa toàn bộ giao diện ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Panel Top (Chứa các nút chức năng và tìm kiếm) ---
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- Panel Center (Không chứa gì) ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Thêm panel chính vào JPanel này
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }


        private JPanel createTopPanel() {
            JPanel P = new JPanel(new BorderLayout());

            // --- Panel nút bên trái ---
            JPanel P1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JButton btnthem = createIconButton("THÊM", resizeimg(loadIcon("/icon/them.png")));
            JButton btnsua = createIconButton("SỬA", resizeimg(loadIcon("/icon/sua.png")));
            JButton btnxoa = createIconButton("XÓA", resizeimg(loadIcon("/icon/xoa.png")));
            JButton btnct = createIconButton("CHI TIẾT", resizeimg(loadIcon("/icon/chitiet.png")));
            JButton btnnhap = createIconButton("NHẬP EXCEL", resizeimg(loadIcon("/icon/nhapexcel.png")));
            JButton btnxuat = createIconButton("XUẤT EXCEL", resizeimg(loadIcon("/icon/xuatexcel.png")));

            for (JButton btn : new JButton[]{btnthem, btnsua, btnxoa, btnct, btnnhap, btnxuat}) {
                P1.add(btn);
            }

            // --- Panel tìm kiếm bên phải ---
            JPanel P2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            String[] cb = {"Tất cả", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại"};
            JComboBox<String> pl = new JComboBox<>(cb);
            pl.setPreferredSize(new Dimension(100, 40));
            JTextField tf = new JTextField(20);
            tf.setPreferredSize(new Dimension(100, 40));
            JButton btnlm = createIconButton("LÀM MỚI", resizeimg(loadIcon("/icon/lammoi.png")));
            btnlm.setVerticalTextPosition(SwingConstants.CENTER);
            btnlm.setHorizontalTextPosition(SwingConstants.RIGHT);

            P2.add(pl);
            P2.add(tf);
            P2.add(btnlm);

            P.add(P1, BorderLayout.WEST);
            P.add(P2, BorderLayout.EAST);
            return P;
        }




    public ImageIcon resizeimg(ImageIcon img) {
        if (img == null) return null;
        Image tmp = img.getImage();
        Image tmp2 = tmp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new ImageIcon(tmp2);
    }



    private ImageIcon loadIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Không thể tải icon: " + path);
            return null;
        }
    }

    private JButton createIconButton(String text, ImageIcon icon) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Không thể đặt Look and Feel của hệ thống.");
            }

            // Tạo JFrame và thêm JPanel vào
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new NhaCungCap());
            frame.setVisible(true);
        });
    }
}
