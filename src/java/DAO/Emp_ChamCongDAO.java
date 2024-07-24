/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import Model.Emp_NhanVien;
import Model.Emp_ChamCong;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author PC
 */
public class Emp_ChamCongDAO {
    public  ArrayList<Emp_ChamCong> getAllChamCong(){
        ArrayList<Emp_ChamCong> list = new ArrayList<>();
        String sql = "SELECT chamcong.id, nhanvien.manv, nhanvien.hoten, chamcong.ngay, chamcong.giovao, chamcong.giora, chamcong.sogiolam " +
                      "FROM chamcong " +
                      "INNER JOIN nhanvien ON chamcong.manv = nhanvien.manv";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Emp_ChamCong cc = new Emp_ChamCong();
                Emp_NhanVien nv = new Emp_NhanVien();
                
                cc.setId_chamcong(rs.getInt("id"));
                nv.setManv(rs.getInt("manv"));
                nv.setHoten(rs.getString("hoten")); 
                cc.setNgay(rs.getString("ngay"));
                cc.setGiovao(rs.getString("giovao"));
                cc.setGiora(rs.getString("giora"));
                cc.setSogiolam(rs.getInt("sogiolam"));
                
                cc.setNhanVien(nv); 
                list.add(cc); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    

    public static void main(String[] args) {
        Emp_ChamCongDAO c = new Emp_ChamCongDAO();
        List<Emp_ChamCong> list = c.getAllChamCong();
        for (Emp_ChamCong o : list) {
            System.err.println(o);
        }
    }
    public void them(Emp_ChamCong cc) throws SQLException{
         String sql = "INSERT INTO chamcong (manv,ngay, giovao, giora,sogiolam) VALUES (?,?,?, ?,?)";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            
            st.setInt(1, cc.getManv());
            st.setString(2,cc.getNgay());
            st.setString(3, cc.getGiovao());
            st.setString(4, cc.getGiora());
            st.setFloat(5, cc.getSogiolam());
            
            st.executeUpdate();
        } catch (Exception e) {
            
        }
    }
    public void delete(int id) {
        String sql = "DELETE FROM chamcong WHERE id = ?";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
  
    public ArrayList<Emp_ChamCong> timkiem(String hoten){
        ArrayList<Emp_ChamCong> list = new ArrayList<>();
        String sql = "SELECT chamcong.id, nhanvien.manv, nhanvien.hoten, chamcong.ngay, chamcong.giovao, chamcong.giora, chamcong.sogiolam " +
             "FROM chamcong " +
             "INNER JOIN nhanvien ON chamcong.manv = nhanvien.manv " +
             "WHERE nhanvien.hoten LIKE ?";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            st.setString(1,"%" + hoten + "%" );
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                   Emp_ChamCong cc = new Emp_ChamCong();
                   Emp_NhanVien nv = new Emp_NhanVien();

                   cc.setId_chamcong(rs.getInt("id"));
                   nv.setManv(rs.getInt("manv"));
                   nv.setHoten(rs.getString("hoten")); 
                   cc.setNgay(rs.getString("ngay"));
                   cc.setGiovao(rs.getString("giovao"));
                   cc.setGiora(rs.getString("giora"));
                   cc.setSogiolam(rs.getInt("sogiolam"));
   

                    cc.setNhanVien(nv); 
                    list.add(cc); 
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return  list;
    }

  
    public int getTongSoGioLam(int manv){
        String sql ="SELECT SUM(sogiolam) AS tong_sogiolam FROM chamcong WHERE manv = ?";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            st.setInt(1, manv);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int tongSoGioLam = rs.getInt("tong_sogiolam");
                // Đóng ResultSet, PreparedStatement và Connection sau khi sử dụng
           // Giả sử bạn có phương thức này trong lớp DBConnection
                return tongSoGioLam;
            }
        } catch (Exception e) {
            // Xử lý hoặc báo cáo lỗi
            e.printStackTrace();
        }
        return 0;   
    }
     public void sua(Emp_ChamCong cc){
        String sql = "update chamcong set manv=?, ngay=?,giovao=?,giora=?,sogiolam=? where id =?";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            
            st.setInt(1, cc.getManv());
            st.setString(2, cc.getNgay());
            st.setString(3, cc.getGiovao());
            st.setString(4, cc.getGiora());
            st.setInt(5, cc.getSogiolam());
            st.setInt(6, cc.getId_chamcong());
            
            st.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    public boolean kiemTraManv(int manv){
        boolean tonTai = false;
       String sql = "SELECT COUNT(*) AS count FROM chamcong WHERE manv = ?"; 
        try {
            DBConnection db = new DBConnection();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            st.setInt(1, manv);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int count = rs.getInt("count");
                if(count > 0){
                    tonTai= true;
                }
            }
            
        } catch (SQLException e) {
             System.err.println(e);
        }
        return tonTai;
    }
        public void chamCongVao(int maNhanVien, String ngay, String gioVao) {
        String sql = "INSERT INTO chamcong (manv, ngay, giovao) VALUES (?, ?, ?)";
        try{
            DBConnection db = new DBConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, maNhanVien);
            ps.setString(2, ngay);
            ps.setString(3, gioVao);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void chamCongRa(int maNhanVien, String ngay, String gioRa) {
        String sql = "UPDATE chamcong SET giora = ? WHERE manv = ? AND giora IS NULL";
        try{
            DBConnection db = new DBConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, gioRa);
            ps.setInt(2, maNhanVien);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSoGioLam(int maNhanVien, String ngay, int sogiolam) {
        String sql = "UPDATE chamcong SET sogiolam = ? WHERE manv = ? AND sogiolam IS NULL";
        try{
            DBConnection db = new DBConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, sogiolam);
            ps.setInt(2, maNhanVien);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGioVao(int maNhanVien, String ngay) {
        String sql = "SELECT giovao FROM chamcong WHERE manv = ? AND ngay = ?";
        try{
            DBConnection db = new DBConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, maNhanVien);
            ps.setString(2, ngay);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("giovao");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public long calculateWorkingHours(String gioVao, String gioRa) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime startTime = LocalTime.parse(gioVao, formatter);
        LocalTime endTime = LocalTime.parse(gioRa, formatter);

        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours();
    }
    public boolean daChamCongVao(int maNhanVien, String ngay) {
        String sql = "SELECT COUNT(*) AS count FROM chamcong WHERE manv = ? AND ngay = ? AND giovao IS NOT NULL";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, maNhanVien);
            ps.setString(2, ngay);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean daChamCongRa(int maNhanVien, String ngay) {
        String sql = "SELECT COUNT(*) AS count FROM chamcong WHERE manv = ? AND ngay = ? AND giora IS NOT NULL";
        try {
            DBConnection db = new DBConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, maNhanVien);
            ps.setString(2, ngay);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
