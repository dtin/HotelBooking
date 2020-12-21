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
@WebServlet(name = "FeedbackServlet", urlPatterns = {"/FeedbackServlet"})
public class FeedbackServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(FeedbackServlet.class);

    private final String FEEDBACK_PAGE = "feedback-page";

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

        String orderId = request.getParameter("orderId");
        String feedbackStar = request.getParameter("feedbackStar");

        TblOrdersDAO ordersDAO;

        ServletContext context = request.getServletContext();
        Map<String, String> addressMap;

        String url = FEEDBACK_PAGE;
        Integer star;
        boolean isValid;
        boolean isError = false;

        TblOrdersError error = new TblOrdersError();

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    String userId = usersDTO.getUserId();
                    String userName = usersDTO.getName();

                    request.setAttribute("FULL_NAME", userName);

                    if (orderId != null) {
                        request.setAttribute("ORDER_ID", orderId);

                        ordersDAO = new TblOrdersDAO();
                        isValid = ordersDAO.validFeedback(orderId, userId);
                        if (isValid) {
                            star = ordersDAO.getFeedbackBefore(orderId);
                            request.setAttribute("USER_FEEDBACK", star);

                            if (feedbackStar != null) {
                                //Not rate before
                                if (star == 0) {
                                    try {
                                        star = Integer.parseInt(feedbackStar);
                                        if (star < 1 || star > 5) {
                                            isError = true;
                                            error.setStarNotLessThanOne("Star allows from 1 to 5 only!");
                                            return;
                                        }
                                    } catch (NumberFormatException ex) {
                                        error.setStarNotLessThanOne("Star must be numberic!");
                                        isError = true;
                                        return;
                                    }

                                    isValid = ordersDAO.setFeedback(orderId, star);
                                    request.setAttribute("USER_FEEDBACK", star);
                                    request.setAttribute("FEEDBACK_RESULT", isValid);
                                } else { //Already rated before
                                    isError = true;
                                    error.setFeedbackBefore("You already made this feedback before!");
                                }
                            }

                        } else {
                            isError = true;
                            error.setFeedbackNotAuthorized("You are not authorized to do feedback OR not yet checkout day!");
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
            }
            addressMap = (Map<String, String>) context.getAttribute("ADDRESS_MAP");
            String realAddress = addressMap.get(url);

            RequestDispatcher rd = request.getRequestDispatcher(realAddress);
            rd.forward(request, response);

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
