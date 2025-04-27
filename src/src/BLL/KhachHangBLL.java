package BLL;

import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;

import java.util.ArrayList;

public class KhachHangBLL {
    public ArrayList<KhachHangDTO> kh;
    public KhachHangBLL(){
        KhachHangDAO dao = new KhachHangDAO();
        kh=dao.getallkhachhang();
    }
    public ArrayList<KhachHangDTO> getlistkh() {
        return kh;
    }
    public KhachHangDTO getonekh(int id) {
        return KhachHangDAO.getonekhachhang(id);
    }
    public boolean insert(KhachHangDTO dto)
    {
        KhachHangDAO dao = new KhachHangDAO();
        kh.add(dto);
        return dao.insertkhachhang(dto);


    }
    public boolean update(KhachHangDTO dto)
    {
        if(dto == null)
        {
            return false;
        }
        return KhachHangDAO.updatekhachhang(dto);
    }
    public boolean delete(int id)
    {
        if(id <0)
        {
            return false;
        }
        return KhachHangDAO.deletekhachhang(id);
    }
}


