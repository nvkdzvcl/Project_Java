package GUI.DIALOG;
import BLL.NhanVienBLL;
import DTO.NhanVienDTO;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class ChitietNhanVienDialog extends JDialog {
    private JTextField txtHoVaTen, txtEmail, txtSDT,txtdc;
    private JRadioButton btnNam, btnNu;
    private JDateChooser ngaySinh;
    private JButton btnDong;
    private int id;
    public ChitietNhanVienDialog(Frame owner,int id) {
        super(owner);
        this.id=id;
        setTitle("Xem Nhân Viên");
        setSize(400, 650);
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
        txtHoVaTen.setEnabled(false);
        add(txtHoVaTen);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setBounds(70, 170, 250, 25);
        add(lbEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(70,200,250,25);
        txtEmail.setEnabled(false);
        add(txtEmail);

        JLabel lbSDT = new JLabel("Số Điện Thoại");
        lbSDT.setBounds(70, 240, 250, 25);
        add(lbSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(70,270,250,25);
        txtSDT.setEnabled(false);
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

        JLabel lbdc = new JLabel("Địa Chỉ");
        lbdc.setBounds(70, 450, 250, 25);
        add(lbdc);
        txtdc = new JTextField();
        txtdc.setBounds(70,480,250,25);
        add(txtdc);
        txtdc.setEnabled(false);
        loadtabledata(id);
        //Nút Đóng
        btnDong = new JButton("Đóng");
        btnDong.setBounds(125, 520, 150, 25);
        btnDong.setBackground(new Color(56,168,223));
        add(btnDong);
        btnDong.addActionListener(e -> {
           dispose();
        });
    }
    public void loadtabledata(int id){
        NhanVienBLL bll= new NhanVienBLL();
        NhanVienDTO tmp=bll.getonenv(id);
        txtHoVaTen.setText(tmp.getHoTen());
        txtSDT.setText(tmp.getSdt());
        txtEmail.setText(tmp.getEmail());
        if (tmp.getGioiTinh().equalsIgnoreCase("Nam")) {
            btnNam.setSelected(true);
        } else if (tmp.getGioiTinh().equalsIgnoreCase("Nữ")) {
            btnNu.setSelected(true);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sqlDate = java.sql.Date.valueOf(tmp.getNgaySinh());
        ngaySinh.setDate(sqlDate);
        txtdc.setText(tmp.getDiachi());
    }
}
