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
import tindd.room.HomeRoom;
import tindd.tblRooms.TblRoomsDAO;
import tindd.tblRooms.TblRoomsError;
import tindd.tblTypes.TblTypesDAO;
import tindd.tblTypes.TblTypesDTO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(SearchServlet.class);

    private final String HOME_PAGE = "home-page";

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

        TblRoomsDAO roomsDAO;
        TblUsersDTO usersDTO;
        TblTypesDAO typesDAO;
        TblTypesDTO typesDTO;

        String roomTypeStr = request.getParameter("typesId");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String amountRoomStr = request.getParameter("amountRoom");

        Integer amountRoom;
        Integer roomType;
        String url = HOME_PAGE;
        boolean isError = false;
        Map<Integer, Integer> roomsMap;
        List<HomeRoom> listRooms;
        List<TblTypesDTO> listTypes;
        Set<Integer> setTypes;
        HomeRoom homeRoom;

        TblRoomsError error = new TblRoomsError();

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    String name = usersDTO.getName();
                    request.setAttribute("FULL_NAME", name); //1
                }
            }

            if (roomTypeStr != null && checkIn != null && checkOut != null && amountRoomStr != null) {

                try {
                    amountRoom = Integer.parseInt(amountRoomStr);

                    if (amountRoom < 1) {
                        isError = true;
                        error.setAmountPositive("Amount room must be positive!");
                        return;
                    }

                    //After valid is completed
                    request.setAttribute("AMOUNT_ROOM", amountRoom);
                } catch (NumberFormatException ex) {
                    isError = true;
                    error.setRoomNumberNumberic("Amount room must be numberic!");
                    return;
                }

                try {
                    roomType = Integer.parseInt(roomTypeStr);

                    //After parse is completed
                    request.setAttribute("ROOM_TYPE", roomType);
                } catch (NumberFormatException ex) {
                    isError = true;
                    error.setRoomTypeNumberic("Room type must be numberic!");
                    return;
                }

                typesDAO = new TblTypesDAO();
                roomsDAO = new TblRoomsDAO();

                //Check valid checkin and checkout date
                int compareValue = checkIn.compareTo(checkOut);
                if (compareValue > 0) {
                    //Swap value if checkIn is larger than checkOut
                    String tempDate = checkIn;
                    checkIn = checkOut;
                    checkOut = tempDate;
                }

                if (roomType == 0) {
                    roomsMap = roomsDAO.searchRoom(checkIn, checkOut, null, amountRoom);
                } else {
                    roomsMap = roomsDAO.searchRoom(checkIn, checkOut, roomType, amountRoom);
                }

                if (roomsMap != null) {
                    listRooms = new ArrayList<>();

                    //Get keys
                    setTypes = roomsMap.keySet();

                    for (Integer type : setTypes) {
                        int quantity = roomsMap.get(type);
                        typesDTO = typesDAO.getTypeById(type);

                        homeRoom = new HomeRoom(typesDTO, quantity);
                        //Add room to list
                        listRooms.add(homeRoom);
                    }

                    request.setAttribute("LIST_ROOMS", listRooms); //2
                }

                request.setAttribute("CHECK_IN", checkIn); //5
                request.setAttribute("CHECK_OUT", checkOut); //6

                listTypes = typesDAO.getAllTypes();
                if (listTypes != null) {
                    request.setAttribute("LIST_TYPES", listTypes); //3
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isError) {
                request.setAttribute("ERROR", error); //4
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
