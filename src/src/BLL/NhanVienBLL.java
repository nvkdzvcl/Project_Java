package BLL;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

import java.util.ArrayList;

public class NhanVienBLL {
    public ArrayList<NhanVienDTO> nv;
    public NhanVienBLL(){
        NhanVienDAO dao = new NhanVienDAO();
        nv=dao.getallnhanvien();
    }
    public NhanVienBLL(int id){
        NhanVienDAO dao = new NhanVienDAO();
        nv=dao.gettennhanvien();
    }
    public ArrayList<NhanVienDTO> getlistnv() {
        return nv;
    }
    public NhanVienDTO getonenv(int id) {
        return NhanVienDAO.getonenhanvien(id);
    }
    public boolean insert(NhanVienDTO dto)
    {
        NhanVienDAO dao = new NhanVienDAO();
        nv.add(dto);
       return dao.insert(dto);


    }
    public boolean update(NhanVienDTO dto)
    {
        if(dto == null)
        {
            return false;
        }
        return NhanVienDAO.update(dto);
    }
    public boolean delete(int id)
    {
        if(id <0)
        {
            return false;
        }
        return NhanVienDAO.delete(id);
    }
}
