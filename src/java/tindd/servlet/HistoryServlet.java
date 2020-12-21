/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import tindd.history.OrderHistory;
import tindd.tblDiscount.TblDiscountDAO;
import tindd.tblDiscount.TblDiscountDTO;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblOrders.TblOrdersDTO;
import tindd.tblRoles.TblRolesDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/HistoryServlet"})
public class HistoryServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(HistoryServlet.class);

    private final String HISTORY_PAGE = "history-page";

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
        String orderDate = request.getParameter("orderDate");

        TblRolesDAO rolesDAO;
        TblOrdersDAO ordersDAO;
        TblDiscountDAO discountDAO;
        TblDiscountDTO discountDTO;

        String url = HISTORY_PAGE;
        String discountCode;
        String action;
        long afterDiscountPrice;
        float discountPercent;
        OrderHistory orderHistory = null;
        List<TblOrdersDTO> searchList;
        List<OrderHistory> orderList;

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    //Get user full name
                    String name = usersDTO.getName();
                    String userId = usersDTO.getUserId();
                    request.setAttribute("FULL_NAME", name);

                    //Get user role
                    rolesDAO = new TblRolesDAO();
                    int roleId = usersDTO.getRoleId();
                    String roleName = rolesDAO.getRoleName(roleId);

                    if (roleName.equalsIgnoreCase("customer")) {

                        ordersDAO = new TblOrdersDAO();

                        if (orderDate != null || orderId != null) {
                            searchList = ordersDAO.getOrderHistory(userId, orderId, orderDate);
                            request.setAttribute("ORDER_DATE", orderDate);
                            request.setAttribute("SEARCH_ORDER", orderId);
                        } else {
                            searchList = ordersDAO.getOrderHistory(userId);
                        }

                        if (searchList != null) {
                            discountDAO = new TblDiscountDAO();
                            orderList = new ArrayList<>();

                            for (TblOrdersDTO order : searchList) {
                                String checkIn = order.getCheckIn().toString();
                                String checkOut = order.getCheckOut().toString();
                                String now = LocalDate.now().toString();

                                if (now.compareTo(checkIn) < 0) {
                                    action = "Delete";
                                } else if (now.compareTo(checkIn) >= 0 && now.compareTo(checkOut) <= 0) {
                                    action = "Wait feedback";
                                } else {
                                    action = "Feedback";
                                }

                                discountCode = order.getDiscountCode();
                                
                                if (discountCode != null) {
                                    discountDTO = discountDAO.getDiscount(discountCode);
                                    if (discountDTO != null) {
                                        discountPercent = discountDTO.getDiscountPercent();
                                        afterDiscountPrice = order.getTotalPrice();

                                        //Calculate final price after using discount
                                        afterDiscountPrice -= (afterDiscountPrice * (discountPercent / 100));
                                        orderHistory = new OrderHistory(order, afterDiscountPrice, action);
                                    }
                                } else {
                                    orderHistory = new OrderHistory(order, order.getTotalPrice(), action);
                                }

                                orderList.add(orderHistory);
                            }

                            request.setAttribute("ORDER_LIST", orderList);
                            request.setAttribute("CURRENT_DATE", LocalDate.now());
                        }

                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
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
