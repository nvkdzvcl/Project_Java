package GUI.DIALOG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KhachHangDialog extends JDialog {
    public KhachHangDialog() {

        setTitle("Thêm Khách Hàng");
        setSize(520,750);
        setLayout( new GridLayout(0, 1, 10, 10));
        JLabel Tenkh=new JLabel("Tên Khách Hàng");
        JLabel sdt=new JLabel("Số Điện Thoại");
        JLabel dc=new JLabel("Địa Chỉ");
        JLabel em=new JLabel("Email");
        JTextField tf1=new JTextField(47);
        tf1.setPreferredSize(new Dimension(200,50));
        JTextField tf2=new JTextField(47);
        tf2.setPreferredSize(new Dimension(200,50));
        JTextField tf3=new JTextField(47);
        tf3.setPreferredSize(new Dimension(200,50));
        JTextField tf4=new JTextField(47);
        tf4.setPreferredSize(new Dimension(200,50));

        JPanel P=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P1=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P2=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P3=new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));
        JPanel P4=new JPanel(new BorderLayout());
        JPanel P5=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));

        JLabel lb1=new JLabel("THÊM KHÁCH HÀNG");
        lb1.setPreferredSize(new Dimension(200,50));
        Font largerFont = new Font("Arial", Font.BOLD, 25);
        lb1.setFont(largerFont);
        Color customBlue = new Color(30, 129, 206);
        P4.setBackground(customBlue);
        P4.setForeground(Color.white);
        P.add(Tenkh);
        P.add(tf1);
        P1.add(sdt);
        P1.add(tf2);
        P2.add(dc);
        P2.add(tf3);
        P5.add(em);
        P5.add(tf4);
        P4.add(lb1,BorderLayout.CENTER);
        lb1.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnthem=new JButton("Thêm Khách Hàng");
        btnthem.setPreferredSize(new Dimension(150,38));
        Color themKhachHangColor = new Color(56, 168, 223);

        btnthem.setBackground(themKhachHangColor);
        btnthem.setForeground(Color.white);
        lb1.setForeground(Color.white);

        JButton btnhuy=new JButton("Hủy Bỏ");
        Color huyBoColor = new Color(216, 92, 99);
        btnhuy.setPreferredSize(new Dimension(150,38));
        btnhuy.setBackground(huyBoColor);
        btnhuy.setForeground(Color.white);
        P3.add(btnthem);
        P3.add(btnhuy);
        add(P4);
        add(P);

        add(P1);

        add(P2);
        add(P5);
        add(P3);

        setLocationRelativeTo(null);



        btnhuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
}
public static void main(String[] args) {
    new KhachHangDialog();}
}
