/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tindd.cart.CartManager;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private final String HOME_CONTROLLER = "home";
    private final String SEARCH_CONTROLLER = "search";

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
        CartManager cartManager;

        String addType = request.getParameter("addType");

        //For redirect purpose
        String pageBefore = request.getParameter("pageBefore");
        String roomTypeStr = request.getParameter("typesId");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String amountRoomStr = request.getParameter("amountRoom");

        String url = HOME_CONTROLLER;

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                //Only login user allow to add to cart
                if (usersDTO != null) {
                    url = HOME_CONTROLLER;

                    if (pageBefore != null) {
                        switch (pageBefore) {
                            case "Search":
                                //Redirect to search page
                                url = SEARCH_CONTROLLER;
                                //Add search value to parameter
                                url += "?typesId=" + roomTypeStr;
                                url += "&checkIn=" + checkIn;
                                url += "&checkOut=" + checkOut;
                                url += "&amountRoom=" + amountRoomStr;
                                break;
                        }
                    }

                    if (addType != null) {
                        int roomType = Integer.parseInt(addType);
                        //Get cart from session scope
                        cartManager = (CartManager) session.getAttribute("CART_MANAGER");

                        //Add product to cart
                        if (cartManager != null) {
                            cartManager.addToCart(roomType);
                        } else {
                            cartManager = new CartManager();
                            cartManager.addToCart(roomType);
                        }

                        cartManager.setCheckInDate(Date.valueOf(checkIn));
                        cartManager.setCheckOutDate(Date.valueOf(checkOut));

                        //Update new cart to session scope
                        session.setAttribute("CART_MANAGER", cartManager);
                    }
                }
            }
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
