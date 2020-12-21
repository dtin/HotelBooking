/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
import tindd.cart.CartRoom;
import tindd.tblDiscount.TblDiscountDTO;
import tindd.tblTypes.TblTypesDAO;
import tindd.tblTypes.TblTypesDTO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(CartServlet.class);

    private final String LOGIN_PAGE = "login-page";
    private final String CART_PAGE = "cart-page";

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

        TblUsersDTO usersDTO;
        TblTypesDAO typesDAO;
        TblTypesDTO typesDTO;

        ServletContext context = request.getServletContext();
        Map<String, String> addressMap;

        TblDiscountDTO discountDTO;
        CartRoom cartRoom;
        ArrayList<CartRoom> roomsList;

        String url = LOGIN_PAGE;
        boolean isRedirect = true;
        long totalPrice = 0;
        CartManager cartManager;

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    //Get user full name
                    String name = usersDTO.getName();
                    request.setAttribute("FULL_NAME", name);

                    cartManager = (CartManager) session.getAttribute("CART_MANAGER");
                    //Create a list having all information for cart page
                    if (cartManager != null) {
                        Set<Integer> roomTypeKeys = cartManager.getCartManager().keySet();
                        if (!roomTypeKeys.isEmpty()) {
                            roomsList = new ArrayList<>();
                            typesDAO = new TblTypesDAO();

                            for (Integer roomType : roomTypeKeys) {
                                int quantity = cartManager.getCartManager().get(roomType);
                                typesDTO = typesDAO.getCartType(roomType);
                                if (typesDTO != null) {
                                    //Update total price
                                    totalPrice += typesDTO.getPrice() * quantity;

                                    //Add room types to list
                                    cartRoom = new CartRoom(typesDTO, quantity);
                                    roomsList.add(cartRoom);
                                }
                            }

                            discountDTO = (TblDiscountDTO) session.getAttribute("DISCOUNT");

                            if (discountDTO != null) {
                                String discountCode = discountDTO.getDiscountCode();
                                float discountPercent = discountDTO.getDiscountPercent();
                                Date availableTo = discountDTO.getAvailableTo();
                                //Compare voucher's available date with today
                                int compareDate = new java.util.Date().compareTo(availableTo);
                                if (compareDate <= 0) {
                                    //Discount is still available (Before or equal to today)
                                    request.setAttribute("DISCOUNT_PERCENT", discountPercent);
                                    request.setAttribute("DISCOUNT_CODE", discountCode);
                                    totalPrice -= (totalPrice * (discountPercent / 100));
                                } else {
                                    //Discount is expired
                                    session.removeAttribute("DISCOUNT");
                                }
                            }

                            request.setAttribute("ROOMS_LIST", roomsList);
                            request.setAttribute("TOTAL_PRICE", totalPrice);
                            request.setAttribute("CHECK_IN", cartManager.getCheckInDate());
                            request.setAttribute("CHECK_OUT", cartManager.getCheckOutDate());
                        }
                    }
                    url = CART_PAGE;
                    isRedirect = false;
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
