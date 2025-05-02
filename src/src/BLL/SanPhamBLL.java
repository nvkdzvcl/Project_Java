package BLL;


import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import java.util.ArrayList;

public class SanPhamBLL {

    private SanPhamDAO dao;

    public SanPhamBLL(){
        dao = new SanPhamDAO();
    }

    public ArrayList<SanPhamDTO> getlistsp(){
        return dao.getallsanpham();
    }
    public SanPhamDTO getonesp(int id)
    {
       return SanPhamDAO.getonesanpham(id);
    }
    public boolean insert(SanPhamDTO dto)
    {
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
