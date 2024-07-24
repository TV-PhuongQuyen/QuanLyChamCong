/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.Emp_ChamCongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Emp_NhanVien;
import Model.Emp_ChamCong;
import java.awt.geom.Arc2D;
import java.text.ParseException;
import DAO.Emp_LuongDAO;
import Model.Emp_Luong;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author PC
 */
public class Emp_ChamCongServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Emp_ChamCongServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Emp_ChamCongServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
//        processRequest(request, response);
          
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
//        processRequest(request, response)
                int maNhanVien = Integer.parseInt(request.getParameter("manv"));
        String ngay = request.getParameter("ngay");
        String gioVao = request.getParameter("giovao");
        String gioRa = request.getParameter("giora");
        String action = request.getParameter("action");

        Emp_ChamCongDAO dao = new Emp_ChamCongDAO();

        if ("vao".equals(action)) {
            // Kiểm tra nếu nhân viên đã chấm công vào trong ngày hôm nay
            if (dao.daChamCongVao(maNhanVien, ngay)) {
                response.sendRedirect("Emp_ChamCong.jsp?error=DaChamCongVao");
                return;
            }
            if (gioVao.length() == 5) {
                gioVao += ":00";
            }
            dao.chamCongVao(maNhanVien, ngay, gioVao);
            response.sendRedirect("Emp_ChamCong.jsp?success=GioVao");
        } else if ("ra".equals(action)) {
            // Kiểm tra nếu nhân viên đã chấm công vào trước khi chấm công ra
            if (!dao.daChamCongVao(maNhanVien, ngay)) {
                response.sendRedirect("Emp_ChamCong.jsp?error=ChuaChamCongVao");
                return;
            }
            // Kiểm tra nếu nhân viên đã chấm công ra trong ngày hôm nay
            if (dao.daChamCongRa(maNhanVien, ngay)) {
                response.sendRedirect("Emp_ChamCong.jsp?error=DaChamCongRa");
                return;
            }
            if (gioRa.length() == 5) {
                gioRa += ":00";
            }
            dao.chamCongRa(maNhanVien, ngay, gioRa);

            String storedGioVao = dao.getGioVao(maNhanVien, ngay);
            int sogiolam = (int) dao.calculateWorkingHours(storedGioVao, gioRa);
            dao.updateSoGioLam(maNhanVien, ngay, sogiolam);
            response.sendRedirect("Emp_ChamCong.jsp?success=GioRa");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
