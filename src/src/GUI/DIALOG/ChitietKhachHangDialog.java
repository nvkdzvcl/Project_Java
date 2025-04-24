package GUI.DIALOG;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChitietKhachHangDialog extends JDialog {
    private JTextField txtHoVaTen, txtEmail, txtSDT, txtDiaChi;
    private JButton btnDong;

    public ChitietKhachHangDialog(Frame owner) {
        super(owner);
        setTitle("Xem Khách Hàng");
        setSize(400, 600);
        setLocationRelativeTo(owner);
        setLayout(null);

        //Tittle
        JLabel lbTittle = new JLabel("XEM KHÁCH HÀNG", SwingConstants.CENTER);
        lbTittle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTittle.setBounds(0, 10, 400, 60);
        lbTittle.setOpaque(true);
        lbTittle.setBackground(new Color(30,129,206));
        lbTittle.setForeground(Color.WHITE);
        add(lbTittle);

        //Các thuộc tính
        JLabel lbHoVaTen = new JLabel("Họ Và Tên");
        lbHoVaTen.setBounds(70, 100, 250, 25);
        add(lbHoVaTen);
        txtHoVaTen = new JTextField();
        txtHoVaTen.setBounds(70,130,250,25);
        add(txtHoVaTen);

        JLabel lbSDT = new JLabel("Số Điện Thoại");
        lbSDT.setBounds(70, 170, 250, 25);
        add(lbSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(70,200,250,25);
        add(txtSDT);

//        JLabel lbEmail = new JLabel("Email");
//        lbEmail.setBounds(70, 240, 250, 25);
//        add(lbEmail);
//        txtEmail = new JTextField();
//        txtEmail.setBounds(70,270,250,25);
//        add(txtEmail);

        JLabel lbDiaChi = new JLabel("Địa Chỉ");
        lbDiaChi.setBounds(70, 240, 250, 25);
        add(lbDiaChi);
        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(70,270,250,25);
        add(txtDiaChi);

        //Nút Đóng
        btnDong = new JButton("Đóng");
        btnDong.setBounds(125, 500, 150, 25);
        btnDong.setBackground(new Color(56,168,223));
        add(btnDong);
        btnDong.addActionListener(e -> {
            dispose();
        });
    }
}
