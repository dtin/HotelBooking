/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersError;

/**
 *
 * @author Tin
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(RegisterServlet.class);

    private final String REGISTER_ERROR = "register";
    private final String LOGIN_PAGE = "login-page";
    private final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ServletContext context = request.getServletContext();
        String url = REGISTER_ERROR;
        TblUsersDAO usersDAO;

        Map<String, String> addressMap;

        TblUsersError error = new TblUsersError();
        boolean isError = false;

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String name = request.getParameter("txtName");
        String address = request.getParameter("txtAddress");
        String phone = request.getParameter("txtPhone");

        try {
            if (name == null || name.isEmpty()) {
                isError = true;
                error.setLengthName("Full name can not empty");
                return;
            }

            if (email == null || !email.matches(EMAIL_PATTERN)) {
                isError = true;
                error.setFormatEmail("Email you entered was not the correct format!");
                return;
            }

            if (password == null || password.isEmpty()) {
                isError = true;
                error.setLengthPassword("Password can not empty!");
                return;
            }

            if (confirm == null || !confirm.equals(password)) {
                isError = true;
                error.setConfirmPassword("Confirm password must be the same with password!");
                return;
            }

            if (address == null || address.isEmpty()) {
                isError = true;
                error.setLengthAddress("Address can not empty!");
                return;
            }

            if (phone == null || phone.isEmpty()) {
                isError = true;
                error.setLengthPhone("Phone can not empty!");
            } else { //Pass all the checking process
                usersDAO = new TblUsersDAO();
                usersDAO.createAccount(email, password, name, address, phone);
            }
        } catch (SQLException ex) {
            if (ex.getMessage().contains("duplicate key")) {
                isError = true;
                error.setDuplicateEmail("The email you entered was registered before!");
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isError) {
                request.setAttribute("ERR", error);

                addressMap = (Map<String, String>) context.getAttribute("ADDRESS_MAP");
                String realAddress = addressMap.get(url);

                RequestDispatcher rd = request.getRequestDispatcher(realAddress);
                rd.forward(request, response);
            } else {
                url = LOGIN_PAGE;
                response.sendRedirect(url);
            }

            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
