package GUI.DIALOG;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChitietNhanVienDialog extends JDialog {
    private JTextField txtHoVaTen, txtEmail, txtSDT;
    private JRadioButton btnNam, btnNu;
    private JDateChooser ngaySinh;
    private JButton btnDong;

    public ChitietNhanVienDialog(Frame owner) {
        super(owner);
        setTitle("Xem Nhân Viên");
        setSize(400, 600);
        setLocationRelativeTo(owner);
        setLayout(null);

        //Tittle
        JLabel lbTittle = new JLabel("XEM NHÂN VIÊN", SwingConstants.CENTER);
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
        txtHoVaTen.setEditable(false);
        add(txtHoVaTen);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setBounds(70, 170, 250, 25);
        add(lbEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(70,200,250,25);
        txtEmail.setEditable(false);
        add(txtEmail);

        JLabel lbSDT = new JLabel("Số Điện Thoại");
        lbSDT.setBounds(70, 240, 250, 25);
        add(lbSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(70,270,250,25);
        txtSDT.setEditable(false);
        add(txtSDT);

        JLabel lbGioiTinh = new JLabel("Giới tính");
        lbGioiTinh.setBounds(70, 310, 250, 25);
        add(lbGioiTinh);
        btnNam = new JRadioButton("Nam");
        btnNam.setBounds(70, 340, 60, 25);
        btnNam.setEnabled(false);
        add(btnNam);
        btnNu = new JRadioButton("Nữ");
        btnNu.setBounds(130, 340, 60, 25);
        btnNu.setEnabled(false);
        add(btnNu);

        JLabel lbNgaySinh = new JLabel("Ngày Sinh");
        lbNgaySinh.setBounds(70, 380, 250, 25);
        add(lbNgaySinh);
        ngaySinh = new JDateChooser();
        ngaySinh.setBounds(70,410,250,25);
        ngaySinh.setEnabled(false);
        ((JTextField) ngaySinh.getDateEditor().getUiComponent()).setEditable(false);
        add(ngaySinh);

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
