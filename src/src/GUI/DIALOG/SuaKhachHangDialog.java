package GUI.DIALOG;

import BLL.KhachHangBLL;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuaKhachHangDialog extends JDialog {
    private JTextField txtHoVaTen, txtEmail, txtSDT, txtDiaChi;
    private JButton btnSua, btnHuy;
    private int id;
    private JComboBox<String> cbTrangThai;
    public SuaKhachHangDialog(Frame owner,int id) {
        super(owner);
        this.id=id;
        setTitle("Sửa Khách Hàng");
        setSize(400,600);
        setLocationRelativeTo(owner);
        setLayout(null);

        //Tittle
        JLabel lbTittle = new JLabel("SỬA KHÁCH HÀNG", SwingConstants.CENTER);
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
        JLabel lbTrangThai = new JLabel("Trạng Thái");
        lbTrangThai.setBounds(70, 310, 250, 25);
        add(lbTrangThai);
        cbTrangThai = new JComboBox<>(new String[] {"Hoạt Động", "Ngừng Hoạt Động"});
        cbTrangThai.setBounds(70,340, 250, 25);

        add(cbTrangThai);

        //Nút thêm, hủy
        btnSua = new JButton("Sửa Khách Hàng");
        btnSua.setBounds(50, 500, 150, 40);
        btnSua.setBackground(new Color(56,168,223));
        add(btnSua);
        btnSua.addActionListener(e -> {
            String hoVaTen = txtHoVaTen.getText().trim();
            String sdt = txtSDT.getText().trim();
            String diaChi = txtDiaChi.getText().trim();

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

//            if (email.isEmpty()) {
//                JOptionPane.showMessageDialog(this,"Vui lòng nhập Email!","Lỗi", JOptionPane.ERROR_MESSAGE);
//                txtEmail.requestFocusInWindow();
//                return;
//            }
//            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//            if (!email.matches(emailRegex)) {
//                JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                txtEmail.requestFocusInWindow();
//                return;
//            }

            if (diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtDiaChi.requestFocusInWindow();
                return;
            }
          int TrangThai;
            if ("Hoạt Động".equals(cbTrangThai.getSelectedItem().toString())) {
                TrangThai = 1;
            } else {
                TrangThai = 0;
            }
            KhachHangDTO dto = new KhachHangDTO();
            KhachHangBLL khachHangBLL = new KhachHangBLL();
            dto.setMaKhachHang(id);
            dto.setTenKhachHang(hoVaTen);
            dto.setDiachi(diaChi);
            dto.setSoDienThoai(sdt);
           dto.setTrangThai(TrangThai);
            khachHangBLL.update(dto);
            dispose();
        });

        btnHuy = new JButton("Hủy");
        btnHuy.setBounds(200, 500, 150, 40);
        btnHuy.setBackground(new Color(216,92,99));
        add(btnHuy);
        btnHuy.addActionListener(e -> {
            dispose();
        });
        loadtabledata(id);
    }
    public void loadtabledata(int id)
    {
        KhachHangDTO dto = new KhachHangDTO();
        KhachHangBLL bll = new KhachHangBLL();
        dto=bll.getonekh(id);
        txtHoVaTen.setText(dto.getTenKhachHang());
        txtDiaChi.setText(dto.getDiachi());
        txtSDT.setText(dto.getSoDienThoai());
        if(dto.getTrangThai()==1)
        cbTrangThai.setSelectedItem("Hoạt Động");
        else{
            cbTrangThai.setSelectedItem("Ngừng Hoạt Động");
        }

    }
}
