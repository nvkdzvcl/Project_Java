package GUI.DIALOG;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChitietNhanVienDialog extends JDialog {
    public ChitietNhanVienDialog() {

        setTitle("Xem Nhân Viên");
        setSize(520,800);
        setLayout( new GridLayout(0, 1, 10, 10));
        JLabel Tenkh=new JLabel("Họ Và Tên");
        JLabel em=new JLabel("Email");
        JLabel sdt=new JLabel("Số Điện Thoại");
        JLabel GT=new JLabel("Giới Tính");
        JLabel ns=new JLabel("Ngày Sinh");
        JTextField tf1=new JTextField(47);
        tf1.setPreferredSize(new Dimension(200,45));
        tf1.setEnabled(false);
        JTextField tf2=new JTextField(47);
        tf2.setEnabled(false);
        tf2.setPreferredSize(new Dimension(200,45));
        JTextField tf3=new JTextField(47);
        tf3.setPreferredSize(new Dimension(200,45));
        tf3.setEnabled(false);
        JTextField tf4=new JTextField(47);
        tf4.setPreferredSize(new Dimension(200,45));
        tf4.setEnabled(false);

        JDateChooser DC=new JDateChooser();
        DC.setPreferredSize(new Dimension(480,45));
        DC.setEnabled(false);

        ButtonGroup bg=new ButtonGroup();
        JRadioButton nam=new JRadioButton("Nam");
        nam.setEnabled(false);
        JRadioButton nu=new JRadioButton("Nữ");
        nu.setEnabled(false);
        bg.add(nam);
        bg.add(nu);


        JPanel P=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P1=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P2=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P3=new JPanel(new FlowLayout(FlowLayout.CENTER,5,20));
        JPanel P4=new JPanel(new BorderLayout());
        JPanel P5=new JPanel(new FlowLayout(FlowLayout.LEFT,9,20));
        JPanel P6=new JPanel(new FlowLayout(FlowLayout.LEFT,9,  20));
        JLabel lb1=new JLabel("XEM NHÂN VIÊN");
        lb1.setPreferredSize(new Dimension(200,50));
        Font largerFont = new Font("Arial", Font.BOLD, 25);
        lb1.setFont(largerFont);
        Color customBlue = new Color(30, 129, 206);

        P4.setBackground(customBlue);
        P4.setForeground(Color.white);

        P.add(Tenkh);
        P.add(tf1);

        P1.add(em);
        P1.add(tf2);

        P2.add(sdt);
        P2.add(tf3);
//
        P6.add(GT);
        P6.add(nam);
        P6.add(nu);

        P5.add(ns);
        P5.add(DC);




        P4.add(lb1,BorderLayout.CENTER);
        lb1.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnthem=new JButton("LƯU THÔNG TIN");
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
//        P3.add(btnthem);
        P3.add(btnhuy);

        add(P4);

        add(P);
        add(P1);

        add(P2);

        add(P6);

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

    }

