package GUI.PANEL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import GUI.DIALOG.ThemTaiKhoanDialog;
import GUI.DIALOG.SuaTaiKhoanDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaiKhoan extends JPanel {
    public TaiKhoan() {
        setLayout(new BorderLayout(10, 10));

        JPanel P = new JPanel(new BorderLayout());
        JPanel P1 = new JPanel();
        ImageIcon addIcon = resizeimg(new ImageIcon((getClass().getResource("/icon/them.png"))));
        JButton btnThem = createIconButton("Thêm", addIcon);

        btnThem.setOpaque(false);
        btnThem.setFocusPainted(false);
        btnThem.setBorderPainted(false);
        ImageIcon suaicon = resizeimg(new ImageIcon((getClass().getResource("/icon/sua.png"))));
        JButton btnSua = createIconButton("Sửa", suaicon);

        btnSua.setOpaque(false);
        btnSua.setFocusPainted(false);
        btnSua.setBorderPainted(false);
        ImageIcon xoaicon = resizeimg(new ImageIcon((getClass().getResource("/icon/xoa.png"))));
        JButton btnXoa = createIconButton("Xóa", xoaicon);
        btnXoa.setOpaque(false);
        btnXoa.setFocusPainted(false);
        btnXoa.setBorderPainted(false);

//        ImageIcon chitieticon = resizeimg(new ImageIcon((getClass().getResource("/icon/chitiet.png"))));
//        JButton btnct = createIconButton("Chi Tiết", chitieticon);
//        btnct.setOpaque(false);
//        btnct.setFocusPainted(false);
//        btnct.setBorderPainted(false);

//        ImageIcon nhapicon = resizeimg(new ImageIcon((getClass().getResource("/icon/nhapexcel.png"))));
//        JButton btnnhap = createIconButton("Nhập Excel",nhapicon);
//        btnnhap.setOpaque(false);
//        btnnhap.setFocusPainted(false);
//        btnnhap.setBorderPainted(false);
//
//        ImageIcon xuaticon = resizeimg(new ImageIcon((getClass().getResource("/icon/xuatexcel.png"))));
//        JButton btnxuat = createIconButton("Xuất Excel", xuaticon);
//        btnxuat.setOpaque(false);
//        btnxuat.setFocusPainted(false);
//        btnxuat.setBorderPainted(false);





        ImageIcon lmcon = resizeimg(new ImageIcon((getClass().getResource("/icon/lammoi.png"))));
        JButton btnLamMoi = createIconButton("Làm Mới", lmcon);
        btnLamMoi.setOpaque(false);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setVerticalTextPosition(SwingConstants.CENTER);
        btnLamMoi.setHorizontalTextPosition(SwingConstants.RIGHT);


        P1.setLayout(new FlowLayout(FlowLayout.LEFT));
        P1.add(btnThem);
        P1.add(btnSua);
        P1.add(btnXoa);
//        P1.add(btnct);
//        P1.add(btnnhap);
//        P1.add(btnxuat);


        String[] cb={"Tất Cả","Mã NV","Tên Đăng Nhập","Chức Vụ","Trạng Thái"};
        JComboBox pl = new JComboBox(cb);
        pl.setPreferredSize(new Dimension(100,40));
        JTextField tf = new JTextField(20);
        tf.setPreferredSize(new Dimension(100,40));
        JPanel P2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        P2.add(pl);
        P2.add(tf);
        P2.add(btnLamMoi);
        P.add(P1, BorderLayout.WEST);
        P.add(P2, BorderLayout.EAST);
        add(P, BorderLayout.NORTH);
        String[] collum = {"Mã NV","Tên Đăng Nhập","Chức Vụ","Trạng Thái"};
        JTable bangkh = new JTable();
        DefaultTableModel model = new DefaultTableModel(collum,0);
        bangkh.setModel(model);
        JScrollPane scrollPane = new JScrollPane(bangkh);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTableHeader header = bangkh.getTableHeader();
        add(scrollPane, BorderLayout.CENTER);

        btnThem.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            ThemTaiKhoanDialog dlgThemTaiKhoan = new ThemTaiKhoanDialog(parent);
            dlgThemTaiKhoan.setVisible(true);
        });
        
        btnSua.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            SuaTaiKhoanDialog dlgSuaTaiKhoan = new SuaTaiKhoanDialog(parent);
            dlgSuaTaiKhoan.setVisible(true);
        });

        setVisible(true);
    }
    public ImageIcon resizeimg(ImageIcon img)
    {
        Image tmp = img.getImage();
        Image tmp2 = tmp.getScaledInstance(30,30, Image.SCALE_SMOOTH);
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

    private void setButtonFlat (JButton button){
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }
}



