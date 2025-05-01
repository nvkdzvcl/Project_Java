package GUI.PANEL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import BLL.KhachHangBLL;
import BLL.NhanVienBLL;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import GUI.DIALOG.ChitietNhanVienDialog;
import GUI.DIALOG.ThemNhanVienDialog;
import GUI.DIALOG.SuaNhanVienDialog;


public class NhanVien extends JPanel {
    public NhanVien() {

        setLayout(new BorderLayout(10, 10));
        JPanel P = new JPanel(new BorderLayout());
        JPanel P1 = new JPanel();


        ImageIcon addIcon = resizeimg(new ImageIcon((getClass().getResource("/icon/them.png"))));
        JButton btnthem = createIconButton("Thêm", addIcon);
//        btnthem.setContentAreaFilled(false);
        btnthem.setOpaque(false);
        btnthem.setFocusPainted(false);
        btnthem.setBorderPainted(false);

        ImageIcon suaicon = resizeimg(new ImageIcon((getClass().getResource("/icon/sua.png"))));
        JButton btnsua = createIconButton("Sửa", suaicon);
//        btnsua.setContentAreaFilled(false);
        btnsua.setOpaque(false);
        btnsua.setFocusPainted(false);
        btnsua.setBorderPainted(false);
        ImageIcon xoaicon = resizeimg(new ImageIcon((getClass().getResource("/icon/xoa.png"))));
        JButton btnxoa = createIconButton("Xóa", xoaicon);
//        btnxoa.setContentAreaFilled(false);
        btnxoa.setOpaque(false);
        btnxoa.setFocusPainted(false);
        btnxoa.setBorderPainted(false);

        ImageIcon lmcon = resizeimg(new ImageIcon((getClass().getResource("/icon/lammoi.png"))));
        JButton btnlm = createIconButton("Làm Mới", lmcon);
        btnlm.setOpaque(false);
        btnlm.setFocusPainted(false);
        btnlm.setVerticalTextPosition(SwingConstants.CENTER);
        btnlm.setHorizontalTextPosition(SwingConstants.RIGHT);

        ImageIcon chitieticon = resizeimg(new ImageIcon((getClass().getResource("/icon/chitiet.png"))));
        JButton btnct = createIconButton("Chi Tiết", chitieticon);
        btnct.setOpaque(false);
        btnct.setFocusPainted(false);
        btnct.setBorderPainted(false);

        ImageIcon nhapicon = resizeimg(new ImageIcon((getClass().getResource("/icon/nhapexcel.png"))));
        JButton btnnhap = createIconButton("Nhập Excel", nhapicon);
        btnnhap.setOpaque(false);
        btnnhap.setFocusPainted(false);
        btnnhap.setBorderPainted(false);

        ImageIcon xuaticon = resizeimg(new ImageIcon((getClass().getResource("/icon/xuatexcel.png"))));
        JButton btnxuat = createIconButton("Xuất Excel", xuaticon);
        btnxuat.setOpaque(false);
        btnxuat.setFocusPainted(false);
        btnxuat.setBorderPainted(false);

        P1.setLayout(new FlowLayout(FlowLayout.LEFT));
        P1.add(btnthem);
        P1.add(btnsua);
        P1.add(btnxoa);
        P1.add(btnlm);
        P1.add(btnct);
        P1.add(btnnhap);
        P1.add(btnxuat);

        String[] cb = {"Tất Cả", "Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "SĐT", "Email","Địa chỉ"};
        JComboBox pl = new JComboBox(cb);
        pl.setPreferredSize(new Dimension(100, 40));
        JTextField tf = new JTextField(20);
        tf.setPreferredSize(new Dimension(100, 40));

        JPanel P2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        P2.add(pl);
        P2.add(tf);
        P2.add(btnlm);
        P.add(P1, BorderLayout.WEST);
        P.add(P2, BorderLayout.EAST);
        add(P, BorderLayout.NORTH);


        String[] collum = {"Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "SĐT", "Email","Địa Chỉ"};
        JTable bangnv = new JTable();
        DefaultTableModel model = new DefaultTableModel(collum, 0);
        bangnv.setModel(model);


        JScrollPane scrollPane = new JScrollPane(bangnv);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTableHeader header = bangnv.getTableHeader();


        bangnv.setShowGrid(false);
//       JButton them=new JButton("Thêm");
//         add(them);
        loadtabledata(model);
        add(scrollPane, BorderLayout.CENTER);

        btnthem.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            ThemNhanVienDialog dlgThemNhanVien = new ThemNhanVienDialog(parent);
            dlgThemNhanVien.setVisible(true);
            dlgThemNhanVien.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadtabledata(model);
                }
            });
        });
        btnsua.addActionListener(e -> {
            if(bangnv.getSelectedRow()!=-1) {
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
                SuaNhanVienDialog dlgSuaNhanVien = new SuaNhanVienDialog(parent, (int) bangnv.getValueAt(bangnv.getSelectedRow(), 0));
                dlgSuaNhanVien.setVisible(true);
                dlgSuaNhanVien.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        loadtabledata(model);
                    }
                });
            }
            else{
                JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Nhân Viên");
            }
        });
        btnct.addActionListener(e -> {
            if(bangnv.getSelectedRow()!=-1) {
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
                ChitietNhanVienDialog dlgChitietNhanVien = new ChitietNhanVienDialog(parent, (int) bangnv.getValueAt(bangnv.getSelectedRow(), 0));
                dlgChitietNhanVien.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Nhân Viên");
            }
        });
        btnxoa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (bangnv.getSelectedRow() != -1) {
                    int option = JOptionPane.showConfirmDialog(
                            NhanVien.this,
                            " Xác nhận xóa", "Xác nhận xóa",


                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        int maNV = (int) bangnv.getValueAt(bangnv.getSelectedRow(), 0);
                        NhanVienBLL bl = new NhanVienBLL();
                        bl.delete(maNV);
                        loadtabledata(model);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Nhân Viên");
                }
            }
        });
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String selectedCriteria = (String) pl.getSelectedItem();
                String searchText = tf.getText().trim();
                if (!searchText.isEmpty()) {
                    searchTableData(model, selectedCriteria, searchText);
                } else {
                    loadtabledata(model);
                }
            }
        });
    }

    public ImageIcon resizeimg(ImageIcon img) {
        Image tmp = img.getImage();
        Image tmp2 = tmp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img = new ImageIcon(tmp2);
        return img;
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

    public void loadtabledata(DefaultTableModel model) {
        model.setRowCount(0);
        NhanVienBLL bll = new NhanVienBLL();
        ArrayList<NhanVienDTO> nv = bll.getlistnv();
        for (NhanVienDTO dto : nv) {
            int manv = dto.getMaNV();
            String tenkh = dto.getHoTen();
            String gioitinh = dto.getGioiTinh();
            String ns = dto.getNgaySinh();
            String sdt = dto.getSdt();
            String email = dto.getEmail();
            String diachi = dto.getDiachi();
            Object[] row = new Object[]{manv, tenkh, gioitinh, ns, sdt, email, diachi};
            model.addRow(row);

        }
    }

    public void searchTableData(DefaultTableModel model, String criteria, String searchText) {
        model.setRowCount(0);

        NhanVienBLL nvBLL = new NhanVienBLL();
        ArrayList<NhanVienDTO> list = nvBLL.getlistnv();

        if (list != null && !list.isEmpty()) {
            searchText = searchText.trim().toLowerCase();

            for (NhanVienDTO nhanvien : list) {
                boolean match = false;

                switch (criteria) {
                    case "Tất Cả":
                        match = String.valueOf(nhanvien.getMaNV()).toLowerCase().contains(searchText)
                                || nhanvien.getHoTen().toLowerCase().contains(searchText)
                                || nhanvien.getGioiTinh().toLowerCase().contains(searchText)
                                || nhanvien.getNgaySinh().toLowerCase().contains(searchText)
                                || nhanvien.getSdt().toLowerCase().contains(searchText)
                                || nhanvien.getEmail().toLowerCase().contains(searchText)
                                || nhanvien.getDiachi().toLowerCase().contains(searchText);
                        break;

                    case "Mã nhân viên":
                        match = String.valueOf(nhanvien.getMaNV()).toLowerCase().contains(searchText);
                        break;

                    case "Tên nhân viên":
                        match = nhanvien.getHoTen().toLowerCase().contains(searchText);
                        break;

                    case "Giới tính":
                        match = nhanvien.getGioiTinh().toLowerCase().contains(searchText);
                        break;

                    case "Ngày sinh":
                        match = nhanvien.getNgaySinh().toLowerCase().contains(searchText);
                        break;

                    case "Số điện thoại":
                        match = nhanvien.getSdt().toLowerCase().contains(searchText);
                        break;

                    case "Email":
                        match = nhanvien.getEmail().toLowerCase().contains(searchText);
                        break;

                    case "Địa chỉ":
                        match = nhanvien.getDiachi().toLowerCase().contains(searchText);
                        break;
                }

                if (match) {
                    Object[] row = new Object[]{
                            nhanvien.getMaNV(),
                            nhanvien.getHoTen(),
                            nhanvien.getGioiTinh(),
                            nhanvien.getNgaySinh(),
                            nhanvien.getSdt(),
                            nhanvien.getEmail(),
                            nhanvien.getDiachi()
                    };
                    model.addRow(row);
                }
            }
        }
    }

}




