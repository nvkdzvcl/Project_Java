package GUI.PANEL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import BLL.SanPhamBLL;
import DTO.SanPhamDTO;
import GUI.DIALOG.ThemSanPhamDialog;
import GUI.DIALOG.SuaSanPhamDialog;

public class SanPham extends JPanel {

    JTable bangsp;
    DefaultTableModel model;

    SanPhamBLL sanPhamBLL = new SanPhamBLL();

    public SanPham() {
        setLayout(new BorderLayout(10, 10));

        JPanel P=new JPanel(new BorderLayout());
        JPanel P1=new JPanel();


        ImageIcon addIcon = resizeimg(new ImageIcon((getClass().getResource("/icon/them.png"))));
        JButton btnThem= createIconButton("Thêm", addIcon);
//        btnThem.setContentAreaFilled(false);
        btnThem.setOpaque(false);
        btnThem.setFocusPainted(false);
        btnThem.setBorderPainted(false);

        ImageIcon suaicon= resizeimg(new ImageIcon((getClass().getResource("/icon/sua.png"))));
        JButton btnSua= createIconButton("Sửa", suaicon);
//        btnSua.setContentAreaFilled(false);
        btnSua.setOpaque(false);
        btnSua.setFocusPainted(false);
        btnSua.setBorderPainted(false);

        ImageIcon chitieticon= resizeimg(new ImageIcon((getClass().getResource("/icon/chitiet.png"))));
        JButton btnChiTiet = createIconButton("Chi Tiết", chitieticon);
        btnChiTiet.setOpaque(false);
        btnChiTiet.setFocusPainted(false);
        btnChiTiet.setBorderPainted(false);

        ImageIcon xoaicon= resizeimg(new ImageIcon((getClass().getResource("/icon/xoa.png"))));
        JButton btnxoa= createIconButton("Xóa", xoaicon);
//        btnxoa.setContentAreaFilled(false);
        btnxoa.setOpaque(false);
        btnxoa.setFocusPainted(false);
        btnxoa.setBorderPainted(false);

        ImageIcon lmcon= resizeimg(new ImageIcon((getClass().getResource("/icon/lammoi.png"))));
        JButton btnlm= createIconButton("Làm Mới", lmcon);
        btnlm.setOpaque(false);
        btnlm.setFocusPainted(false);
        btnlm.setVerticalTextPosition(SwingConstants.CENTER);
        btnlm.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnlm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataToTable(sanPhamBLL.getlístsp());
            }
        });


        P1.setLayout(new FlowLayout(FlowLayout.LEFT));
        P1.add(btnThem);
        P1.add(btnSua);
        P1.add(btnChiTiet);
        P1.add(btnxoa);

        String[] cb={"Tất Cả","Mã SP","Tên SP","Xuất Xứ","Thương Hiệu","Màu Sắc"};
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


        String[] collum={"Mã SP","Tên SP","Thương Hiệu","Xuất Xứ","Màu Sắc","Kích Thước", "Đơn Giá", "Số Lượng", "Trạng Thái"};
        bangsp=new JTable();
        model=new DefaultTableModel(collum,0);
        bangsp.setModel(model);
        loadDataToTable(sanPhamBLL.getlístsp());


        JScrollPane scrollPane = new JScrollPane(bangsp);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTableHeader header = bangsp.getTableHeader();

        add(scrollPane,BorderLayout.CENTER);

        btnThem.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            ThemSanPhamDialog dlgThemSanPham = new ThemSanPhamDialog(parent, this);
            dlgThemSanPham.setVisible(true);
        });

        btnSua.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            SuaSanPhamDialog dlgSuaSanPham = new SuaSanPhamDialog(parent);
            dlgSuaSanPham.setVisible(true);
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

    public void loadDataToTable(ArrayList<SanPhamDTO> danhSachSanPham){
        model.setRowCount(0);
        for(SanPhamDTO sanPham : danhSachSanPham){
            model.addRow(new String[]{
                    Integer.toString(sanPham.getMaSP()),
                    sanPham.getTenSP(),
                    sanPham.getThuongHieu(),
                    sanPham.getXuatXu(),
                    sanPham.getMauSac(),
                    sanPham.getKichThuoc(),
                    Integer.toString(sanPham.getDonGia()),
                    Integer.toString(sanPham.getSoLuong()),
                    (sanPham.getTrangThai() == 1 ? "Hoạt Động" : "Ngừng Hoạt Động")
            });
        }
    }
}
