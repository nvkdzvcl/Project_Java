package BLL;


import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;

import java.util.ArrayList;

public class SanPhamBLL {
    public ArrayList<SanPhamDTO> sp;
    public SanPhamBLL(){
        SanPhamDAO dao = new SanPhamDAO();
        sp=dao.getallsanpham();
    }
    public ArrayList<SanPhamDTO> getlístsp(){
        return sp;
    }
    public SanPhamDTO getonesp(int id)
    {
       return SanPhamDAO.getonesanpham(id);
    }
    public boolean insert(SanPhamDTO dto)
    {
        SanPhamDAO dao = new SanPhamDAO();
        sp.add(dto);
        return dao.insert(dto);

    }
    public boolean update(SanPhamDTO dto)
    {
        if(dto == null)
        {
            return false;
        }
        return SanPhamDAO.update(dto);
    }
    public boolean delete(int id)
    {
        if(id <0)
        {
            return false;
        }
        return SanPhamDAO.delete(id);
    }
}
