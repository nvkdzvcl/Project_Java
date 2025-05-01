package GUI.DIALOG;
import BLL.NhanVienBLL;
import DTO.NhanVienDTO;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuaNhanVienDialog extends JDialog {
    private JTextField txtHoVaTen, txtEmail, txtSDT,txtdc;
    private JRadioButton btnNam, btnNu;
    private JDateChooser ngaySinh;
    private JButton btnSua, btnHuy;
    private int id;
    public SuaNhanVienDialog(Frame owner,int id) {
        super(owner);
        this.id=id;
        setTitle("Sửa Nhân Viên");
        setSize(400, 650);
        setLocationRelativeTo(owner);
        setLayout(null);

        //Tittle
        JLabel lbTittle = new JLabel("SỬA NHÂN VIÊN", SwingConstants.CENTER);
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

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setBounds(70, 170, 250, 25);
        add(lbEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(70,200,250,25);
        add(txtEmail);

        JLabel lbSDT = new JLabel("Số Điện Thoại");
        lbSDT.setBounds(70, 240, 250, 25);
        add(lbSDT);
        txtSDT = new JTextField();
        txtSDT.setBounds(70,270,250,25);
        add(txtSDT);

        JLabel lbGioiTinh = new JLabel("Giới tính");
        lbGioiTinh.setBounds(70, 310, 250, 25);
        add(lbGioiTinh);
        btnNam = new JRadioButton("Nam");
        btnNam.setBounds(70, 340, 60, 25);
        add(btnNam);
        btnNu = new JRadioButton("Nữ");
        btnNu.setBounds(130, 340, 60, 25);
        add(btnNu);

        JLabel lbNgaySinh = new JLabel("Ngày Sinh");
        lbNgaySinh.setBounds(70, 380, 250, 25);
        add(lbNgaySinh);
        ngaySinh = new JDateChooser();
        ngaySinh.setBounds(70,410,250,25);
        add(ngaySinh);

        JLabel lbdc = new JLabel("Địa Chỉ");
        lbdc.setBounds(70, 450, 250, 25);
        add(lbdc);
        txtdc = new JTextField();
        txtdc.setBounds(70,480,250,25);
        add(txtdc);

        loadtabledata(id);
        //Nút thêm, hủy
        btnSua = new JButton("Sửa Nhân Viên");
        btnSua.setBounds(50, 520, 150, 40);
        btnSua.setBackground(new Color(56,168,223));
        add(btnSua);
        btnSua.addActionListener(e -> {
            String hoVaTen = txtHoVaTen.getText().trim();
            String email = txtEmail.getText().trim();
            String sdt = txtSDT.getText().trim();
            if (hoVaTen.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập Họ và tên!","Lỗi", JOptionPane.ERROR_MESSAGE);
                txtHoVaTen.requestFocusInWindow();
                return;
            }
            String hoVaTenRegex = "^[\\p{L}\\s']+$";
            if (!hoVaTen.matches(hoVaTenRegex)) {
                JOptionPane.showMessageDialog(this,"Tên không hợp lệ!","Lỗi", JOptionPane.ERROR_MESSAGE);
                txtHoVaTen.requestFocusInWindow();
                return;
            }

            if (sdt.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng Số điện thoại!","Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSDT.requestFocusInWindow();
                return;
            }
            String sdtRegex = "^0[0-9]{9}$";
            if (!sdt.matches(sdtRegex)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSDT.requestFocusInWindow();
                return;
            }

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Vui lòng nhập Email!","Lỗi", JOptionPane.ERROR_MESSAGE);
                txtEmail.requestFocusInWindow();
                return;
            }
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!email.matches(emailRegex)) {
                JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtEmail.requestFocusInWindow();
                return;
            }

            if (!btnNam.isSelected() && !btnNu.isSelected()) {
                JOptionPane.showMessageDialog(this,"Vui lòng chọn Giới tính!","Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            if (ngaySinh.getDate() == null) {
                JOptionPane.showMessageDialog(this,"Vui lòng chọn Ngày sinh!","Lỗi", JOptionPane.ERROR_MESSAGE);
                ngaySinh.requestFocusInWindow();
            }
            Date utilDate = ngaySinh.getDate(); // hoặc java.sql.Date cũng được
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng tùy theo bạn cần
            String ngaysinh = sdf.format(utilDate);
            String gioiTinh = "";
            if (btnNam.isSelected()) {
                gioiTinh = "Nam";
            } else if (btnNu.isSelected()) {
                gioiTinh = "Nữ";
            }
            String diachi=txtdc.getText();
            NhanVienDTO tmp= new NhanVienDTO(id,hoVaTen,gioiTinh,ngaysinh,sdt,email,diachi);
            NhanVienBLL bll= new NhanVienBLL();
            bll.update(tmp);
            dispose();
        });

        btnHuy = new JButton("Hủy");
        btnHuy.setBounds(200, 520, 150, 40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(e -> {
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
