/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import tindd.cart.CartManager;
import tindd.tblDiscount.TblDiscountDTO;
import tindd.tblOrderDetails.TblOrderDetailsDAO;
import tindd.tblOrderDetails.TblOrderDetailsError;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblRooms.TblRoomsDAO;
import tindd.tblTypes.TblTypesDAO;
import tindd.tblUsers.TblUsersDTO;
import tindd.utils.ActivationHelper;
import tindd.utils.MailSender;

/**
 *
 * @author Tin
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(CheckoutServlet.class);

    private final String ORDER_RESULT_PAGE = "order-result";
    private final String CART_CONTROLLER = "cart";

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

        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");

        CartManager cartManager;
        Map<Integer, Integer> cartProducts;
        TblTypesDAO typesDAO;
        TblOrdersDAO ordersDAO;
        TblRoomsDAO roomsDAO;
        TblOrderDetailsDAO orderDetailsDAO;
        TblDiscountDTO discountDTO;
        TblUsersDTO usersDTO;

        TblOrderDetailsError error = new TblOrderDetailsError();

        String url = CART_CONTROLLER;
        boolean isError = false;
        String userId;
        String discountCode = null;
        String activationCode;
        Integer typeOutOfStock = null;
        int currentQuantity = -1;
        int buyQuantity;
        int resultQuantity;
        long currentPrice;
        long totalPrice = 0;
        Map<Integer, Long> roomsPrice = new HashMap<>();
        List<Integer> roomsList;

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    userId = usersDTO.getUserId();
                    String userName = usersDTO.getName();
                    request.setAttribute("FULL_NAME", userName);

                    cartManager = (CartManager) session.getAttribute("CART_MANAGER");
                    if (cartManager != null) {
                        typesDAO = new TblTypesDAO();
                        roomsDAO = new TblRoomsDAO();

                        cartProducts = cartManager.getCartManager();
                        Set<Integer> typeIdKeys = cartProducts.keySet();

                        //Swap values if check in is later than check out
                        int compareValue = checkIn.compareTo(checkOut);
                        if (compareValue > 0) {
                            //Swap value if checkIn is larger than checkOut
                            String tempDate = checkIn;
                            checkIn = checkOut;
                            checkOut = tempDate;
                        }

                        //Map of ProductId - Quantity
                        for (Integer typeId : typeIdKeys) {
                            //Checking quantity before checkout
                            currentQuantity = roomsDAO.getQuantityAvailable(typeId, checkIn, checkOut);

                            buyQuantity = cartProducts.get(typeId);
                            resultQuantity = currentQuantity - buyQuantity;

                            if (resultQuantity < 0) {
                                typeOutOfStock = typeId;
                                break;
                            }

                            //Get current price for checkout
                            currentPrice = typesDAO.getPrice(typeId);
                            totalPrice += currentPrice * buyQuantity;

                            //Map of ProductId - currentPrice
                            roomsPrice.put(typeId, currentPrice);
                        }

                        //If there is an out of stock product
                        if (typeOutOfStock != null) {
                            isError = true;
                        } else {
                            discountDTO = (TblDiscountDTO) session.getAttribute("DISCOUNT");
                            if (discountDTO != null) {
                                discountCode = discountDTO.getDiscountCode();
                            }

                            ordersDAO = new TblOrdersDAO();
                            activationCode = ActivationHelper.randomActivationNumber() + "";
                            String orderId = ordersDAO.createOrder(userId, checkIn, checkOut, totalPrice, discountCode, activationCode);

                            if (orderId != null) {
                                orderDetailsDAO = new TblOrderDetailsDAO();

                                boolean orderDetailAdded = false;

                                url = ORDER_RESULT_PAGE;

                                for (Integer typeId : typeIdKeys) {
                                    buyQuantity = cartProducts.get(typeId);
                                    roomsList = roomsDAO.getListRoomAvailable(typeId, buyQuantity, checkIn, checkOut);

                                    for (Integer roomId : roomsList) {
                                        currentPrice = roomsPrice.get(typeId);
                                        orderDetailAdded = orderDetailsDAO.createOrderDetails(orderId, roomId, currentPrice);
                                        if (!orderDetailAdded) {
                                            return;
                                        }
                                    }
                                }

                                if (orderDetailAdded) {
                                    request.setAttribute("ORDER_ID", orderId);
                                    session.removeAttribute("DISCOUNT");
                                    session.removeAttribute("CART_MANAGER");

                                    int lastIndex = request.getRequestURL().lastIndexOf("/");
                                    String activationLink = request.getRequestURL().substring(0, lastIndex + 1)
                                            .concat("activation?userId=").concat(userId)
                                            .concat("&orderId=").concat(orderId)
                                            .concat("&activationCode=").concat(activationCode);
                                    MailSender.sendActivationLink(userId, activationLink);
                                }
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
            if (isError) {
                error.setRoomOutOfStock("Only " + currentQuantity + " room(s) left!");
                request.setAttribute("TYPE_ID_ERROR", typeOutOfStock);
                request.setAttribute("QUANTITY_CHECKOUT_ERROR", error);
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
