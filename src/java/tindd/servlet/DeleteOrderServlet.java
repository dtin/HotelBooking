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
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblOrders.TblOrdersError;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "DeleteOrderServlet", urlPatterns = {"/DeleteOrderServlet"})
public class DeleteOrderServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DeleteOrderServlet.class);

    private final String HISTORY_PAGE = "history";

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

        HttpSession session = request.getSession(false);

        ServletContext context = request.getServletContext();
        Map<String, String> addressMap;

        String orderId = request.getParameter("orderId");

        TblOrdersDAO ordersDAO;

        TblOrdersError error = new TblOrdersError();
        boolean isDeleted;
        boolean isError = false;

        String url = HISTORY_PAGE;

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    String userId = usersDTO.getUserId();
                    if (userId != null && orderId != null) {
                        ordersDAO = new TblOrdersDAO();
                        isDeleted = ordersDAO.deleteOrder(orderId, userId);

                        if (!isDeleted) {
                            error.setDeleteOrderFail("You are not having authorize to delete or this order does not existed.");
                            isError = true;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isError) {
                request.setAttribute("ERROR", error);

                addressMap = (Map<String, String>) context.getAttribute("ADDRESS_MAP");
                String realAddress = addressMap.get(url);

                RequestDispatcher rd = request.getRequestDispatcher(realAddress);
                rd.forward(request, response);
            } else {
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
