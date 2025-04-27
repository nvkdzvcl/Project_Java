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
    public ArrayList<NhanVienDTO> getlistnv() {
        return nv;
    }
    public NhanVienDTO getonenv(String id) {
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
    public boolean delete(String id)
    {
        if(id ==null)
        {
            return false;
        }
        return NhanVienDAO.delete(id);
    }
}
