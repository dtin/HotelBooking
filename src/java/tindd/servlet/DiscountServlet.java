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
import tindd.tblDiscount.TblDiscountDAO;
import tindd.tblDiscount.TblDiscountDTO;
import tindd.tblOrders.TblOrdersDAO;
import tindd.tblOrders.TblOrdersError;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "DiscountServlet", urlPatterns = {"/DiscountServlet"})
public class DiscountServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DiscountServlet.class);

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

        String discountCodeStr = request.getParameter("discountCode");

        TblDiscountDAO discountDAO;
        TblDiscountDTO discountDTO;
        TblOrdersDAO ordersDAO;
        TblUsersDTO usersDTO;

        boolean isUsedBefore;
        boolean isError = false;
        String userId;
        String discountCode;
        String url = CART_CONTROLLER;

        TblOrdersError error = new TblOrdersError();

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    userId = usersDTO.getUserId();
                    if (userId != null && discountCodeStr != null && !discountCodeStr.isEmpty()) {
                        discountCode = discountCodeStr.trim().toUpperCase();

                        discountDAO = new TblDiscountDAO();
                        discountDTO = discountDAO.getDiscount(discountCode);
                        if (discountDTO != null) {
                            Date availableTo = discountDTO.getAvailableTo();
                            int compareDate = new java.util.Date().compareTo(availableTo);

                            if (compareDate <= 0) {
                                //Check if user was already use this discount before
                                ordersDAO = new TblOrdersDAO();
                                userId = usersDTO.getUserId();

                                isUsedBefore = ordersDAO.isDiscountBefore(userId, discountCode);

                                if (!isUsedBefore) {
                                    session.setAttribute("DISCOUNT", discountDTO);
                                } else {
                                    error.setAlreadyUsed("You already used this discount code before!");
                                    isError = true;
                                }
                            } else {
                                error.setExpiredDiscount("Discount code '" + discountCode + "' was expired!");
                                isError = true;
                            }
                        } else {
                            error.setDiscountNotValid("Discount code '" + discountCodeStr + "' is not valid!");
                            isError = true;
                        }
                    } else {
                        error.setEmptyDiscount("Can not apply empty code!");
                        isError = true;
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
