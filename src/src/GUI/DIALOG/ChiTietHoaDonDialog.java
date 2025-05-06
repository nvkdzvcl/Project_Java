package GUI.DIALOG;

import BLL.HoaDonBLL;
import BLL.KhachHangBLL;
import BLL.NhanVienBLL;
import BLL.SanPhamBLL;
import DAO.CTHoaDonDAO;
import DTO.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ChiTietHoaDonDialog extends JDialog {
    private final JTextField txtMaHD;
    private final JTextField txtTenNV_BanHang;
    private final JTextField txtTenKH;
    private final JTextField txtThoiGian;
    private final JComboBox<String> cbTrangThai;
    private final JTextField txtTongTien;
    private final JTable tblChiTietHD;
    private final DefaultTableModel detailModel;

    public ChiTietHoaDonDialog(Frame owner, int maHD) {
        super(owner, "Chi Tiết Hóa Đơn", true);
        setSize(980, 500);
        setLocationRelativeTo(owner);
        setLayout(null);

        // Tiêu đề
        JLabel lbTitle = new JLabel("CHI TIẾT HÓA ĐƠN", SwingConstants.CENTER);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        lbTitle.setBounds(0, 10, 970, 60);
        lbTitle.setOpaque(true);
        lbTitle.setBackground(new Color(30, 129, 206));
        lbTitle.setForeground(Color.WHITE);
        add(lbTitle);

        // Mã HĐ
        add(createLabel("Mã HĐ", 10, 80));
        txtMaHD = createTextField(10, 110);
        add(txtMaHD);

        // Tên NV bán hàng
        add(createLabel("Tên NV Bán Hàng", 170, 80));
        txtTenNV_BanHang = createTextField(170, 110);
        add(txtTenNV_BanHang);

        // Tên KH
        add(createLabel("Khách Hàng", 330, 80));
        txtTenKH = createTextField(330, 110);
        add(txtTenKH);

        // Thời gian
        add(createLabel("Thời gian tạo", 490, 80));
        txtThoiGian = createTextField(490, 110);
        add(txtThoiGian);

        // Trạng thái
        add(createLabel("Trạng thái", 650, 80));
        cbTrangThai = new JComboBox<>(new String[]{"Đang giao", "Đã giao", "Đã hủy"});
        cbTrangThai.setBounds(650, 110, 150, 25);
        cbTrangThai.setEnabled(false);
        add(cbTrangThai);

        // Tổng tiền
        add(createLabel("Tổng tiền", 810, 80));
        txtTongTien = createTextField(810, 110);
        add(txtTongTien);

        // Bảng chi tiết
        tblChiTietHD = new JTable(new DefaultTableModel(
                new Object[]{"STT", "Mã SP", "Màu sắc", "Kích thước", "Thương hiệu", "Xuất xứ", "Giá bán", "Số lượng"},
                0
        ));
        detailModel = (DefaultTableModel) tblChiTietHD.getModel();
        JScrollPane scroll = new JScrollPane(tblChiTietHD);
        scroll.setBounds(10, 150, 950, 300);
        add(scroll);

        // Nạp dữ liệu
        loadData(maHD);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setBounds(x, y, 150, 25);
        return lb;
    }

    private JTextField createTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 25);
        tf.setEnabled(false);
        return tf;
    }

    private void loadData(int maHD) {
        // 1. Lấy hóa đơn
        HoaDonDTO hd = new HoaDonBLL().getHoaDonById(maHD);
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn #" + maHD, "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // 2. Đổ lên form
        txtMaHD.setText(String.valueOf(hd.getMaHoaDon()));
        // Nhân viên
        NhanVienDTO nv = new NhanVienBLL().getonenv(hd.getNhanVienBan());
        txtTenNV_BanHang.setText(nv != null ? nv.getHoTen() : "");
        // Khách hàng
        KhachHangDTO kh = new KhachHangBLL().getonekh(hd.getKhachHang());
        txtTenKH.setText(kh != null ? kh.getTenKhachHang() : "");
        // Thời gian
        txtThoiGian.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(hd.getThoiGian()));
        // Trạng thái
        int st = hd.getTrangThai();
        cbTrangThai.setSelectedIndex(st == 2 ? 1 : st == 3 ? 2 : 0);
        // Tổng tiền
        txtTongTien.setText(String.valueOf(hd.getTongTien()));

        // 3. Chi tiết
        detailModel.setRowCount(0);
        SanPhamBLL sanPhamBLL = new SanPhamBLL();
        int stt = 1;
        for (CTHoaDonDTO ct : CTHoaDonDAO.getInstance().getByMaHoaDon(maHD)) {
            SanPhamDTO sp = sanPhamBLL.getonesp(ct.getMaSP());
            detailModel.addRow(new Object[]{
                    stt++,
                    ct.getMaSP(),
                    sp != null ? sp.getMauSac() : "",
                    sp != null ? sp.getKichThuoc() : "",
                    sp != null ? sp.getThuongHieu() : "",
                    sp != null ? sp.getXuatXu() : "",
                    ct.getDonGia(),
                    ct.getSoLuong()
            });
        }
    }
}
