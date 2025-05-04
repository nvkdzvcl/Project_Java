package GUI.DIALOG;

import BLL.NhanVienBLL;
import BLL.PhieuNhapBLL;
import BLL.SanPhamBLL;
import DAO.CTPhieuNhapDAO;
import DTO.CTPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import GUI.PANEL.PhieuNhap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ChiTietPhieuNhapDialog extends JDialog {
    private JTextField txtMaPN, txtTenNV_Nhap, txtThoiGian, txtTongTien;
    private JTable tblChiTietPN;

    private DefaultTableModel detailModel;

    public ChiTietPhieuNhapDialog(Frame owner) {
        super(owner,"Chi Tiet Phieu Nhap",true);
        setSize(900,500);
        setTitle("Chi Tiết Phiếu Nhập");
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbTittle = new JLabel("CHI TIẾT PHIẾU NHẬP", SwingConstants.CENTER);
        lbTittle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTittle.setBounds(0,10,900,60);
        lbTittle.setOpaque(true);
        lbTittle.setBackground(new Color(30,129,206));
        lbTittle.setForeground(Color.WHITE);
        add(lbTittle);

        //các textfield
        JLabel lbMaPN = new JLabel("Mã PN");
        lbMaPN.setBounds(10,80,120,25);
        add(lbMaPN);
        txtMaPN = new JTextField();
        txtMaPN.setBounds(10,110,210,25);
        add(txtMaPN);

        JLabel lbTenNV_Nhap = new JLabel("Tên NV nhập");
        lbTenNV_Nhap.setBounds(230,80,120,25);
        add(lbTenNV_Nhap);
        txtTenNV_Nhap = new JTextField();
        txtTenNV_Nhap.setBounds(230,110,210,25);
        add(txtTenNV_Nhap);

        JLabel lbThoiGian = new JLabel("Thời gian tạo");
        lbThoiGian.setBounds(450,80,120,25);
        add(lbThoiGian);
        txtThoiGian = new JTextField();
        txtThoiGian.setBounds(450,110,210,25);
        add(txtThoiGian);

        JLabel lbTongTien = new JLabel("Tổng tiền");
        lbTongTien.setBounds(670,80,120,25);
        add(lbTongTien);
        txtTongTien = new JTextField();
        txtTongTien.setBounds(670,110,210,25);
        add(txtTongTien);

        tblChiTietPN = new JTable(new DefaultTableModel(new Object[] {"STT","Mã SP","Màu sắc","Kích thước","Thương hiệu","Xuất xứ","Giá nhập","Số lượng"},0));
        detailModel = (DefaultTableModel) tblChiTietPN.getModel();
        JScrollPane scrtblChiTietPN = new JScrollPane(tblChiTietPN);
        scrtblChiTietPN.setBounds(10,150,865,300);
        add(scrtblChiTietPN);

    }

    public ChiTietPhieuNhapDialog(Frame owner,int maPN) {
        this(owner);
        initData(maPN);
    }

    private void initData(int maPN) {
        PhieuNhapDTO pn = new PhieuNhapBLL().getById(maPN);
        if (pn == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu " + maPN, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        txtMaPN.setText(String.valueOf(pn.getMaPhieuNhap()));
        txtTenNV_Nhap .setText(new NhanVienBLL().getonenv(pn.getNhanVienNhap()).getHoTen());
        txtThoiGian .setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pn.getNgay()));
        txtTongTien .setText(String.valueOf(pn.getTongTien()));

        detailModel.setRowCount(0);
        int stt = 1;
        for (CTPhieuNhapDTO ct : CTPhieuNhapDAO.getInstance().getByMaPhieuNhap(maPN)) {
            SanPhamDTO sp = new SanPhamBLL().getonesp(ct.getMaSP());
            detailModel.addRow(new Object[] {
                    stt++,
                    ct.getMaSP(),
                    // nếu DTO sp có thêm thuộc tính mauSac/kichThuoc, dùng sp.getMauSac()...
                    sp.getMauSac(),
                    sp.getKichThuoc(),
                    sp.getThuongHieu(),
                    sp.getXuatXu(),
                    ct.getDonGia(),
                    ct.getSoLuong()
            });
        }
    }

}
