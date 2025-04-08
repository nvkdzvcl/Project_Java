//package GUI.COMPONENT;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.HashMap;
//
//public class MainFunction extends JToolBar {
//
//    public ButtonToolBar btnAdd, btnEdit, btnDetail, btnNhapExcel, btnXuatExcel, btnHuyPhieu;
//    public JSeparator separator;
//    public HashMap<String, ButtonToolBar> btn = new HashMap<>();
//    //private final NhomQuyenBUS nhomQuyenBus = new NhomQuyenBUS();
//
//    public MainFunction(int maNhomQuyen, String chucNang, String[] listBtn){
//        initData();
//        initComponent(maNhomQuyen, chucNang, listBtn);
//    }
//
//    public void initData(){
//
//        btn.put("create", new ButtonToolBar("THÊM", "add.svg", "create"));
//        btn.put("delete", new ButtonToolBar("XÓA", "delete.svg", "delete"));
//        btn.put("update", new ButtonToolBar("SỬA", "edit.svg", "update"));
//        btn.put("cancel", new ButtonToolBar("HUỶ PHIẾU", "cancel.svg", "delete"));
//        btn.put("detail", new ButtonToolBar("CHI TIẾT", "detail.svg", "view"));
//        btn.put("import", new ButtonToolBar("NHẬP EXCEL", "import_excel.svg", "create"));
//        btn.put("export", new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view"));
//        btn.put("phone", new ButtonToolBar("XEM DS", "phone.svg", "view"));
//    }
//
//    private void initComponent(int maNhomQuyen, String chucNang, String[] listBtn){
//
//        this.setBackground(Color.WHITE);
//        this.setRollover(true);
//        initData();
//        for(String btn1 : listBtn){
//            this.add(btn.get(btn1));
//            if(!nhomQuyenBus.checkPermission(maNhomQuyen, chucNang, btn.get(btn1).getPermission())){
//                btn.get(btn1).setEnabled(false);
//            }
//        }
//    }
//}
