package GUI.PANEL;

import BLL.KhachHangBLL;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import GUI.DIALOG.ChitietKhachHangDialog;
import GUI.DIALOG.SuaKhachHangDialog;
import GUI.DIALOG.ThemKhachHangDialog;
import GUI.DIALOG.SuaKhachHangDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KhachHang extends JPanel {
    public KhachHang() {
        setLayout(new BorderLayout(10, 10));

        JPanel P=new JPanel(new BorderLayout());
        JPanel P1=new JPanel();
        ImageIcon addIcon = resizeimg(new ImageIcon((getClass().getResource("/icon/them.png"))));
        JButton btnthem= createIconButton("Thêm", addIcon);

        btnthem.setOpaque(false);
        btnthem.setFocusPainted(false);
        btnthem.setBorderPainted(false);
        ImageIcon suaicon= resizeimg(new ImageIcon((getClass().getResource("/icon/sua.png"))));
        JButton btnsua= createIconButton("Sửa", suaicon);

        btnsua.setOpaque(false);
        btnsua.setFocusPainted(false);
        btnsua.setBorderPainted(false);
        ImageIcon xoaicon= resizeimg(new ImageIcon((getClass().getResource("/icon/xoa.png"))));
        JButton btnxoa= createIconButton("Xóa", xoaicon);
        btnxoa.setOpaque(false);
        btnxoa.setFocusPainted(false);
        btnxoa.setBorderPainted(false);

        ImageIcon chitieticon= resizeimg(new ImageIcon((getClass().getResource("/icon/chitiet.png"))));
        JButton btnct=createIconButton("Chi Tiết", chitieticon);
        btnct.setOpaque(false);
        btnct.setFocusPainted(false);
        btnct.setBorderPainted(false);

        ImageIcon nhapicon= resizeimg(new ImageIcon((getClass().getResource("/icon/nhapexcel.png"))));
        JButton btnnhap=createIconButton("Nhập Excel",nhapicon);
        btnnhap.setOpaque(false);
        btnnhap.setFocusPainted(false);
        btnnhap.setBorderPainted(false);

        ImageIcon xuaticon=resizeimg(new ImageIcon((getClass().getResource("/icon/xuatexcel.png"))));
        JButton btnxuat=createIconButton("Xuất Excel", xuaticon);
        btnxuat.setOpaque(false);
        btnxuat.setFocusPainted(false);
        btnxuat.setBorderPainted(false);

        ImageIcon lmcon= resizeimg(new ImageIcon((getClass().getResource("/icon/lammoi.png"))));
        JButton btnlm= createIconButton("Làm Mới", lmcon);
        btnlm.setOpaque(false);
        btnlm.setFocusPainted(false);
        btnlm.setVerticalTextPosition(SwingConstants.CENTER);
        btnlm.setHorizontalTextPosition(SwingConstants.RIGHT);

        P1.setLayout(new FlowLayout(FlowLayout.LEFT));
        P1.add(btnthem);
        P1.add(btnsua);
        P1.add(btnxoa);
        P1.add(btnct);
        P1.add(btnnhap);
        P1.add(btnxuat);


        String[] cb={"Tất Cả","Mã khách hàng","Tên khách hàng","Số điện thoại","Địa chỉ"};
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
        String[] collum={"Mã khách hàng","Tên khách hàng","Số điện thoại","Địa chỉ"};
        JTable bangkh=new JTable();
        DefaultTableModel model=new DefaultTableModel(collum,0);
        bangkh.setModel(model);
        JScrollPane scrollPane = new JScrollPane(bangkh);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTableHeader header = bangkh.getTableHeader();
        loadtabledata(model);
        add(scrollPane,BorderLayout.CENTER);
        btnthem.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            ThemKhachHangDialog dlgThemKhachHang = new ThemKhachHangDialog(parent);
            dlgThemKhachHang.setVisible(true);
            dlgThemKhachHang.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadtabledata(model); // tự động reload lại khi dialog đóng
                }
            });
        });
        btnsua.addActionListener(e -> {
            if(bangkh.getSelectedRow()!=-1) {
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
                SuaKhachHangDialog dlgSuaKhachHang = new SuaKhachHangDialog(parent, (int) bangkh.getValueAt(bangkh.getSelectedRow(), 0));
                dlgSuaKhachHang.setVisible(true);
                dlgSuaKhachHang.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        loadtabledata(model);
                    }
                });
            }
            else{
                JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Khách Hàng");
            }
        });
        btnct.addActionListener(e -> {
            if(bangkh.getSelectedRow()!=-1) {
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
                ChitietKhachHangDialog dlgChitietKhachHang = new ChitietKhachHangDialog(parent, (int) bangkh.getValueAt(bangkh.getSelectedRow(), 0));
                dlgChitietKhachHang.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Khách Hàng");
            }
        });
        btnxoa.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            if(bangkh.getSelectedRow()!=-1){
                int option = JOptionPane.showConfirmDialog(
                        KhachHang.this,
                        " Xác nhận xóa","Xác nhận xóa",


                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if(option==JOptionPane.YES_OPTION) {
                    int maKH = (int) bangkh.getValueAt(bangkh.getSelectedRow(), 0);
                    KhachHangBLL kh = new KhachHangBLL();
                    kh.delete(maKH);
                    loadtabledata(model);
                }
            }
//        JOptionPane.showConfirmDialog("Bạn CÓ")
            else{
                JOptionPane.showMessageDialog(null,"Vui Lòng Chọn Khách Hàng");
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
        setVisible(true);
    }


    public ImageIcon resizeimg(ImageIcon img)
    {
        Image tmp=img.getImage();
        Image tmp2=tmp.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        img=new ImageIcon(tmp2);
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

        private void setButtonFlat (JButton button){
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
        }
    public void loadtabledata(DefaultTableModel model)
    {
        model.setRowCount(0);
        KhachHangBLL khachHangBLL = new KhachHangBLL();
        ArrayList<KhachHangDTO> kh=khachHangBLL.getlistkh();
        for(KhachHangDTO dto : kh)
        {
            int makh= dto.getMaKhachHang();
            String tenkh=dto.getTenKhachHang();
            String diachi=dto.getDiachi();
            String sdt=dto.getSoDienThoai();

            Object[] row= new Object[]{makh,tenkh,sdt,diachi};
            model.addRow(row);
        }
    }
    public void searchTableData(DefaultTableModel model, String criteria, String searchText) {
        model.setRowCount(0);
        KhachHangBLL kh = new KhachHangBLL();
        ArrayList<KhachHangDTO> list = kh.getlistkh();
        if (list != null) {
            for (KhachHangDTO khachhang : list) {
                boolean match = false;
                switch (criteria) {
                    case "Tất Cả":
                        match = String.valueOf(khachhang.getMaKhachHang()).contains(searchText) ||
                                khachhang.getTenKhachHang().toLowerCase().contains(searchText.toLowerCase()) ||
                                khachhang.getSoDienThoai().contains(searchText) ||
                                khachhang.getDiachi().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Mã khách hàng":
                        match = String.valueOf(khachhang.getMaKhachHang()).contains(searchText);
                        break;
                    case "Tên khách hàng":
                        match = khachhang.getTenKhachHang().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Số điện thoại":
                        match = khachhang.getSoDienThoai().contains(searchText);
                        break;

                    case "Địa chỉ":
                        match = khachhang.getDiachi().toLowerCase().contains(searchText.toLowerCase());
                        break;

                }
                if (match) {
                    Object[] row = new Object[]{
                            khachhang.getMaKhachHang(),
                            khachhang.getTenKhachHang(),
                            khachhang.getSoDienThoai(),
                            khachhang.getDiachi(),


                    };
                    model.addRow(row);
                }
            }
        }
    }
    }



