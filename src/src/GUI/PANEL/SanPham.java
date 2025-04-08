package GUI.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class SanPham extends JPanel {
    public SanPham() {
        setLayout(new BorderLayout(10, 10));

        JPanel P=new JPanel(new BorderLayout());
        JPanel P1=new JPanel();


        ImageIcon addIcon = resizeimg(new ImageIcon((getClass().getResource("/icon/them.png"))));
        JButton btnthem= createIconButton("THÊM", addIcon);
//        btnthem.setContentAreaFilled(false);
        btnthem.setOpaque(false);
        btnthem.setFocusPainted(false);
        btnthem.setBorderPainted(false);

        ImageIcon suaicon= resizeimg(new ImageIcon((getClass().getResource("/icon/sua.png"))));
        JButton btnsua= createIconButton("Sửa", suaicon);
//        btnsua.setContentAreaFilled(false);
        btnsua.setOpaque(false);
        btnsua.setFocusPainted(false);
        btnthem.setBorderPainted(false);
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


        P1.setLayout(new FlowLayout(FlowLayout.LEFT));
        P1.add(btnthem);
        P1.add(btnsua);
        P1.add(btnxoa);

        String[] cb={"Tất Cả","Top"};
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


        String[] collum={"Mã Sp","Tên Sản Phẩm","Số Lượng Tồn","Thương Hiệu","Hệ Điều Hành","Kích Thước Màn","Chip Xử Lý","Dung Lượng Pin","Xuất Xứ","Khu Vực Kho"};
        JTable bangsp=new JTable();
        DefaultTableModel model=new DefaultTableModel(collum,0);
        bangsp.setModel(model);



        JScrollPane scrollPane = new JScrollPane(bangsp);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTableHeader header = bangsp.getTableHeader();



        JButton them=new JButton("Thêm");
        add(them);
        add(scrollPane,BorderLayout.CENTER);

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
    }

