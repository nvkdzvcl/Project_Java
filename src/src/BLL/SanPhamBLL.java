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
        return dao.delete(id);
    }

    public ArrayList<SanPhamDTO> search(String txt, String type){
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type){
            case "Tất Cả" -> {
                for(SanPhamDTO sanPhamDTO : getlistsp()){
                    if(Integer.toString(sanPhamDTO.getMaSP()).equals(txt) ||
                            sanPhamDTO.getTenSP().toLowerCase().contains(txt) ||
                            sanPhamDTO.getXuatXu().toLowerCase().contains(txt) ||
                            sanPhamDTO.getThuongHieu().toLowerCase().contains(txt) ||
                            sanPhamDTO.getMauSac().toLowerCase().contains(txt)){
                        result.add(sanPhamDTO);
                    }
                }
            }
            case "Mã SP" -> {
                for(SanPhamDTO sanPhamDTO : getlistsp()){
                    if(Integer.toString(sanPhamDTO.getMaSP()).equals(txt)){
                        result.add(sanPhamDTO);
                    }
                }
            }
            case "Tên SP" -> {
                for(SanPhamDTO sanPhamDTO : getlistsp()){
                    if(sanPhamDTO.getTenSP().toLowerCase().contains(txt)){
                        result.add(sanPhamDTO);
                    }
                }
            }
            case "Xuất Xứ" -> {
                for(SanPhamDTO sanPhamDTO : getlistsp()){
                    if(sanPhamDTO.getXuatXu().toLowerCase().contains(txt)){
                        result.add(sanPhamDTO);
                    }
                }
            }
            case "Thương Hiệu" -> {
                for(SanPhamDTO sanPhamDTO : getlistsp()){
                    if(sanPhamDTO.getThuongHieu().toLowerCase().contains(txt)){
                        result.add(sanPhamDTO);
                    }
                }
            }
            case "Màu Sắc" -> {
                for(SanPhamDTO sanPhamDTO : getlistsp()){
                    if(sanPhamDTO.getMauSac().toLowerCase().contains(txt)){
                        result.add(sanPhamDTO);
                    }
                }
            }
        }

        return result;
    }
}
