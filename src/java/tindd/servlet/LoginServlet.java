/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.google.GoogleUtils;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    private final String LOGIN_FAIL = "login-fail";
    private final String HOME_CONTROLLER = "home";

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

        HttpSession session;

        String url = LOGIN_FAIL;
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String recaptcha = request.getParameter("g-recaptcha-response");

        TblUsersDTO usersDTO;
        TblUsersDAO usersDAO;
        TblRolesDAO rolesDAO;

        try {
            if (recaptcha != null && !recaptcha.isEmpty()) {
                if (GoogleUtils.verifyRecaptcha(recaptcha)) {
                    if (password != null) {
                        usersDAO = new TblUsersDAO();
                        usersDTO = usersDAO.checkLogin(email, password);
                    } else {
                        return;
                    }

                    if (usersDTO != null) {
                        //Get roleId
                        rolesDAO = new TblRolesDAO();
                        String roleName = rolesDAO.getRoleName(usersDTO.getRoleId());

                        if (roleName.equalsIgnoreCase("customer")) {
                            session = request.getSession(true);
                            session.setAttribute("ACCOUNT_INFO", usersDTO);
                            url = HOME_CONTROLLER;
                        }
                    } else {
                        url = LOGIN_FAIL;
                    }
                }
            }

        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
