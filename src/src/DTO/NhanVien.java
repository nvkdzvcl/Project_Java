package com.GUI.Panel;
import com.BUS.NhanVienBUS;
import com.BUS.QuyenBUS;
import com.DTO.NhanVienDTO;
import com.GUI.Dialog.NhanVien.ChitietNhanVienDialog;
import com.GUI.Dialog.NhanVien.SuaNhanVienDialog;
import com.GUI.Dialog.NhanVien.ThemNhanVienDialog;
import com.UTILS.IE_Excel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class NhanVien extends JPanel {
    public NhanVien(){
///////// TẠO PANEL
        setLayout(new BorderLayout(10, 10));
        JPanel P=new JPanel(new BorderLayout());
        JPanel P1=new JPanel();
/// //////////////////////////////////////////

        /// ///// TẠO BUTTON
        ImageIcon addIcon = resizeimg(new ImageIcon(getClass().getResource("/icons/insert.png")));
        JButton btnthem= createIconButton("Thêm", addIcon);

        btnthem.setOpaque(false);
        btnthem.setFocusPainted(false);
        btnthem.setBorderPainted(false);

        ImageIcon suaicon= resizeimg(new ImageIcon(getClass().getResource("/icons/edit.png")));
        JButton btnsua= createIconButton("Sửa", suaicon);

        btnsua.setOpaque(false);
        btnsua.setFocusPainted(false);
        btnthem.setBorderPainted(false);
        ImageIcon xoaicon= resizeimg(new ImageIcon(getClass().getResource("/icons/delete.png")));
        JButton btnxoa= createIconButton("Xóa", xoaicon);

        btnxoa.setOpaque(false);
        btnxoa.setFocusPainted(false);
        btnxoa.setBorderPainted(false);

        ImageIcon lmcon= resizeimg(new ImageIcon(getClass().getResource("/icons/reload.png")));
        JButton btnlm= createIconButton("Làm Mới", lmcon);
        btnlm.setOpaque(false);
        btnlm.setFocusPainted(false);
        btnlm.setVerticalTextPosition(SwingConstants.CENTER);
        btnlm.setHorizontalTextPosition(SwingConstants.RIGHT);

        ImageIcon chitieticon= resizeimg(new ImageIcon(getClass().getResource("/icons/info.png")));
        JButton btnct=createIconButton("Chi Tiết", chitieticon);
        btnct.setOpaque(false);
        btnct.setFocusPainted(false);
        btnct.setBorderPainted(false);

        ImageIcon nhapicon= resizeimg(new ImageIcon(getClass().getResource("/icons/excel.png")));
        JButton btnnhap=createIconButton("Nhập Excel",nhapicon);
        btnnhap.setOpaque(false);
        btnnhap.setFocusPainted(false);
        btnnhap.setBorderPainted(false);

        ImageIcon xuaticon=resizeimg(new ImageIcon(getClass().getResource("/icons/excel.png")));
        JButton btnxuat=createIconButton("Xuất Excel", xuaticon);
        btnxuat.setOpaque(false);
        btnxuat.setFocusPainted(false);
        btnxuat.setBorderPainted(false);
        /// ////////////////////

        P1.setLayout(new FlowLayout(FlowLayout.LEFT));
        P1.add(btnthem);
        P1.add(btnsua);
        P1.add(btnxoa);
        P1.add(btnlm);
        P1.add(btnct);
//        P1.add(btnnhap);
        P1.add(btnxuat);

        /// ////////////// TẠO BẢNG TABLE
        String[] cb={"Tất Cả","MNV","Họ Tên","Ngày Sinh","Giới Tính","diaChi","SĐT","Email","Chức Vụ"};
        JComboBox pl=new JComboBox(cb);

        pl.setPreferredSize(new Dimension(100,40));
        JTextField tf=new JTextField(20);
        tf.setPreferredSize(new Dimension(100,40));

        JPanel P2=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        P2.add(pl);
        P2.add(tf);
        P2.add(btnlm);
        P.add(P1, BorderLayout.WEST);
        P.add(P2,BorderLayout.EAST);
        add(P, BorderLayout.NORTH);

        String[] collum={"MNV","Họ Tên","Ngày Sinh","Giới Tính","diaChi","SĐT","Email","Chức Vụ"};
        JTable bangnv = new JTable();
        DefaultTableModel model = new DefaultTableModel(collum,0);
        bangnv.setModel(model);

        loadtabledata(model);

        JScrollPane scrollPane = new JScrollPane(bangnv);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTableHeader header = bangnv.getTableHeader();

       bangnv.setShowGrid(false);
        add(scrollPane,BorderLayout.CENTER);
/// //////////////// XỬ LÝ SỰ KIỆN
        btnthem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThemNhanVienDialog them=new ThemNhanVienDialog();
                them.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadtabledata(model); // tự động reload lại khi dialog đóng
                    }
                });
            }
        });
    btnxoa.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(bangnv.getSelectedRow()!=-1){
                int option = JOptionPane.showConfirmDialog(
                        NhanVien.this,
                        " Xác nhận xóa","Xác nhận xóa",


                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if(option==JOptionPane.YES_OPTION) {
                    int maNV = (int) bangnv.getValueAt(bangnv.getSelectedRow(), 0);
                    NhanVienBUS nv = new NhanVienBUS();
                    nv.deleteNhanVien(maNV);
                    loadtabledata(model);
                }
            }
//        JOptionPane.showConfirmDialog("Bạn CÓ")
        else{
                JOptionPane.showMessageDialog(null,"Vui Lòng Chọn Nhân Viên");
            }
        }
    });
    btnsua.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(bangnv.getSelectedRow()==-1){
                JOptionPane.showMessageDialog(null,"Vui Lòng Chọn Nhân Viên");
            }
            else {


                SuaNhanVienDialog sua = new SuaNhanVienDialog((int) bangnv.getValueAt(bangnv.getSelectedRow(), 0));
                sua.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        loadtabledata(model);
                    }
                });
            }
        }
    });
    btnct.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ChitietNhanVienDialog ct = new ChitietNhanVienDialog(((int) bangnv.getValueAt(bangnv.getSelectedRow(), 0)));


        }

        
    });
        btnxuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IE_Excel.ExportExcel(bangnv);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    /// /////////////////////////////////
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Lấy giá trị từ JComboBox và JTextField
                String selectedCriteria = (String) pl.getSelectedItem();
                String searchText = tf.getText().trim();

                // Gọi hàm tìm kiếm hoặc lọc dữ liệu
                if (!searchText.isEmpty()) {
                    searchTableData(model, selectedCriteria, searchText);
                } else {
                    loadtabledata(model); // Load lại toàn bộ dữ liệu nếu không có từ khóa
                }
            }
        });

        if(!QuyenBUS.checkQuyen(Login.getMaNhomQuyen(), "Quản lý nhân viên", "thêm")){
            btnthem.setEnabled(false);
        }
        if(!QuyenBUS.checkQuyen(Login.getMaNhomQuyen(), "Quản lý nhân viên", "sửa")){
            btnsua.setEnabled(false);
        }
        if(!QuyenBUS.checkQuyen(Login.getMaNhomQuyen(), "Quản lý nhân viên", "xem")){
            btnct.setEnabled(false);
        }
        if(!QuyenBUS.checkQuyen(Login.getMaNhomQuyen(), "Quản lý nhân viên", "xoá")){
            btnxoa.setEnabled(false);
        }

        setVisible(true);
    }



    /// ////// HÀM
public ImageIcon resizeimg(ImageIcon img)
{
    Image tmp = img.getImage();
    Image tmp2 = tmp.getScaledInstance(43,43,Image.SCALE_SMOOTH);
    img = new ImageIcon(tmp2);
     return img;
}
    private JButton createIconButton (String text, ImageIcon icon){
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }
    public void loadtabledata(DefaultTableModel model)
    {
        model.setRowCount(0);
        NhanVienBUS nv=new NhanVienBUS();
        ArrayList<NhanVienDTO> list= nv.getAllNhanVien();
        for(NhanVienDTO nhanVien:list){
            int MNV=nhanVien.getMaNV();
            String Tennv=nhanVien.getTenNV();
            String Gt=nhanVien.getGioiTinh();
            Date ns=nhanVien.getNgaySinh();
            String dc=nhanVien.getDiaChi();
            String sdt=nhanVien.getSdt();
            String email=nhanVien.getEmail();
            String cv=nhanVien.getChucVu();
            Object[] row= new Object[]{MNV,Tennv,ns,Gt,dc,sdt,email,cv};
            model.addRow(row);

        }
    }
    public void searchTableData(DefaultTableModel model, String criteria, String searchText) {
        model.setRowCount(0);
        NhanVienBUS nv = new NhanVienBUS();
        ArrayList<NhanVienDTO> list = nv.getAllNhanVien();

        for (NhanVienDTO nhanVien : list) {
            boolean match = false;
            switch (criteria) {
                case "Tất Cả":
                    match = String.valueOf(nhanVien.getMaNV()).contains(searchText) ||
                            nhanVien.getTenNV().toLowerCase().contains(searchText.toLowerCase()) ||
                            nhanVien.getGioiTinh().toLowerCase().contains(searchText.toLowerCase()) ||
                            nhanVien.getDiaChi().toLowerCase().contains(searchText.toLowerCase()) ||
                            nhanVien.getSdt().contains(searchText) ||
                            nhanVien.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                            nhanVien.getChucVu().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "MNV":
                    match = String.valueOf(nhanVien.getMaNV()).contains(searchText);
                    break;
                case "Họ Tên":
                    match = nhanVien.getTenNV().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "Giới Tính":
                    match = nhanVien.getGioiTinh().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "diaChi":
                    match = nhanVien.getDiaChi().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "SĐT":
                    match = nhanVien.getSdt().contains(searchText);
                    break;
                case "Email":
                    match = nhanVien.getEmail().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "Chức Vụ":
                    match = nhanVien.getChucVu().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "Ngày Sinh":
                    // Có thể cần format ngày sinh để so sánh
                    match = nhanVien.getNgaySinh().toString().contains(searchText);
                    break;
            }
            if (match) {
                Object[] row = new Object[]{
                        nhanVien.getMaNV(),
                        nhanVien.getTenNV(),
                        nhanVien.getNgaySinh(),
                        nhanVien.getGioiTinh(),
                        nhanVien.getDiaChi(),
                        nhanVien.getSdt(),
                        nhanVien.getEmail(),
                        nhanVien.getChucVu()
                };
                model.addRow(row);
            }
        }
    }

    public static void main(String[] args) {
        JFrame J=new JFrame();
        J.setSize(1200,700);
        J.add(new NhanVien());
        J.setLocationRelativeTo(null);
        J.setVisible(true);
    }

    }
/// /////////////////////////
