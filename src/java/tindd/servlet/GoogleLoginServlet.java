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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.google.GooglePojo;
import tindd.google.GoogleUtils;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/GoogleLoginServlet"})
public class GoogleLoginServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(GoogleLoginServlet.class);

    private final String LOGIN_PAGE = "login-page";
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

        ServletContext context = request.getServletContext();
        Map<String, String> addressMap;

        String code = request.getParameter("code");

        TblUsersDAO usersDAO;
        TblUsersDTO usersDTO;

        String url = LOGIN_PAGE;

        try {
            if (code != null && !code.isEmpty()) {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

                String userId = googlePojo.getEmail();
                usersDAO = new TblUsersDAO();
                usersDTO = usersDAO.checkLogin(userId, "");

                if (usersDTO == null) {
                    boolean isCreated = usersDAO.createAccount(userId, userId);
                    if (isCreated) {
                        usersDTO = usersDAO.checkLogin(userId, "");
                    } else {
                        return;
                    }
                }

                session = request.getSession(true);
                session.setAttribute("ACCOUNT_INFO", usersDTO);

                url = HOME_CONTROLLER;
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
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
