/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import tindd.history.OrderDetailHistory;
import tindd.tblOrderDetails.TblOrderDetailsDAO;
import tindd.tblOrderDetails.TblOrderDetailsDTO;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblRooms.TblRoomsDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "OrderDetailServlet", urlPatterns = {"/OrderDetailServlet"})
public class OrderDetailServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(OrderDetailServlet.class);

    private final String HOME_CONTROLLER = "home";
    private final String ORDER_DETAIL_PAGE = "order-detail-page";

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

        TblOrdersDAO ordersDAO;
        TblOrderDetailsDAO orderDetailsDAO;
        TblRoomsDAO roomsDAO;

        ServletContext context = request.getServletContext();
        Map<String, String> addressMap;

        String url = HOME_CONTROLLER;
        boolean isRedirect = true;
        OrderDetailHistory orderDetailHistory;

        List<TblOrderDetailsDTO> detailList;
        List<OrderDetailHistory> displayList;

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    //Get user full name
                    String name = usersDTO.getName();
                    String userId = usersDTO.getUserId();
                    request.setAttribute("FULL_NAME", name);

                    if (orderId != null) {
                        url = ORDER_DETAIL_PAGE;
                        isRedirect = false;

                        ordersDAO = new TblOrdersDAO();
                        //Check if user is allow to see this history order detail
                        boolean isAllow = ordersDAO.isAllowHistory(orderId, userId);

                        if (isAllow) {
                            orderDetailsDAO = new TblOrderDetailsDAO();
                            detailList = orderDetailsDAO.getOrder(orderId);

                            if (detailList != null) {
                                displayList = new ArrayList<>();
                                roomsDAO = new TblRoomsDAO();

                                for (TblOrderDetailsDTO orderRoom : detailList) {
                                    int productId = orderRoom.getRoomId();
                                    String roomName = roomsDAO.getRoomNumber(productId);

                                    orderDetailHistory = new OrderDetailHistory(orderRoom, roomName);
                                    displayList.add(orderDetailHistory);
                                }

                                request.setAttribute("ORDER_LIST", displayList);
                                request.setAttribute("ORDER_ID", orderId);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isRedirect) {
                response.sendRedirect(url);
            } else {
                addressMap = (Map<String, String>) context.getAttribute("ADDRESS_MAP");
                String realAddress = addressMap.get(url);

                RequestDispatcher rd = request.getRequestDispatcher(realAddress);
                rd.forward(request, response);
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
