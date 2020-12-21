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
import tindd.tblTypes.TblTypesDAO;
import tindd.tblTypes.TblTypesDTO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/HomeServlet"})
public class HomeServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(HomeServlet.class);

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

        TblUsersDTO usersDTO;
        TblRoomsDAO roomsDAO;
        TblTypesDAO typesDAO;
        TblTypesDTO typesDTO;

        int quantity;
        String url = HOME_PAGE;
        Map<Integer, Integer> roomsMap;
        Set<Integer> roomType;
        List<HomeRoom> listRooms;
        List<TblTypesDTO> listTypes;
        HomeRoom homeRoom;

        try {
            if (session != null) {
                usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    String name = usersDTO.getName();
                    request.setAttribute("FULL_NAME", name); //1
                }
            }

            typesDAO = new TblTypesDAO();
            roomsDAO = new TblRoomsDAO();
            roomsMap = roomsDAO.getAllRoom();

            if (roomsMap != null) {
                listRooms = new ArrayList<>();

                //Get keys
                roomType = roomsMap.keySet();

                for (Integer type : roomType) {
                    quantity = roomsMap.get(type);
                    typesDTO = typesDAO.getTypeById(type);

                    homeRoom = new HomeRoom(typesDTO, quantity);
                    //Add room to list
                    listRooms.add(homeRoom);
                }

                request.setAttribute("LIST_ROOMS", listRooms); //2
            }

            listTypes = typesDAO.getAllTypes();
            if (listTypes != null) {
                request.setAttribute("LIST_TYPES", listTypes); //3
            }

        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
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
