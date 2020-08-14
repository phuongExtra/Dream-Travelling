/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phuongln.dtos.BookingDTO;
import phuongln.dtos.TourDTO;
import phuongln.models.BookingDAO;
import phuongln.models.TourDAO;
import phuongln.utils.CartObject;

/**
 *
 * @author nhatp
 */
public class TourSearchController extends HttpServlet {

    private static final String[] PRICE_TAG = {"All", "Below 500", "500 to 1000", "1000 to 1500", "1500 to 2000", "More than 2000"};
    static Logger log = Logger.getLogger(TourSearchController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String tmp = request.getParameter("page");
            int page = 0;
            if (tmp == null) {
                page = 1;
            } else {
                page = Integer.parseInt(tmp);
            }
            String action = request.getParameter("action");
            if (action == null) {
                action = "default";
            }
            if (action.equalsIgnoreCase("Next Page")) {
                page += 1;
            } else if (action.equalsIgnoreCase("Previous Page")) {
                page -= 1;
            }

            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }
            String price = request.getParameter("price");
            int lowerThreshold = 0;
            int higherThreshold = 0;
            if (price == null) {
                price = "All";
            }
            switch (price) {
                case "All":
                    higherThreshold = 10000;
                    break;
                case "Below 500":
                    higherThreshold = 499;
                    break;
                case "500 to 1000":
                    lowerThreshold = 500;
                    higherThreshold = 1000;
                    break;
                case "1000 to 1500":
                    lowerThreshold = 1000;
                    higherThreshold = 1500;
                    break;
                case "1500 to 2000":
                    lowerThreshold = 1500;
                    higherThreshold = 2000;
                    break;
                case "More than 2000":
                    lowerThreshold = 2000;
                    higherThreshold = 10000;
                    break;
            }
            String startDate = request.getParameter("startDate");
            if (startDate == null) {
                startDate = "";
            }
            String endDate = request.getParameter("endDate");
            if (endDate == null) {
                endDate = "";
            }

            int available = 0;

            TourDAO tDao = new TourDAO();
            BookingDAO bDao = new BookingDAO();
            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CART");
            List<TourDTO> result = tDao.searchTour(startDate, endDate, lowerThreshold, higherThreshold, search, page);
            for (int i = 0; i < result.size(); i++) {
                if (cart == null) {
                    available = result.get(i).getQuota() - bDao.getBookedSlots(result.get(i).getTourID());
                    result.get(i).setAvailableSlots(available);

                } else {
                    if (cart.getCart().containsKey(result.get(i).getTourID())) {
                        BookingDTO tmp2 = (BookingDTO) cart.getCart().get(result.get(i).getTourID());
                        available = result.get(i).getQuota() - (bDao.getBookedSlots(result.get(i).getTourID()) + tmp2.getAmount());
                        result.get(i).setAvailableSlots(available);

                    } else {
                        available = result.get(i).getQuota() - bDao.getBookedSlots(result.get(i).getTourID());
                        result.get(i).setAvailableSlots(available);

                    }
                }
                LocalDate now = LocalDate.now();
                LocalDate date = LocalDate.parse(result.get(i).getFromDate(), DateTimeFormatter.ofPattern("MMM dd, yyyy"));
                if (now.isAfter(date)) {
                    result.get(i).setStatusID(2);
                } else {
                    result.get(i).setStatusID(1);
                }
            }
            request.setAttribute("TPAGE", tDao.getTotalPages());
            request.setAttribute("PAGE", page);
            request.setAttribute("SEARCH", search);
            request.setAttribute("PRICE", price);
            request.setAttribute("START_DATE", startDate);
            request.setAttribute("END_DATE", endDate);
            request.setAttribute("TOUR_LIST", result);
            request.setAttribute("PRICE_TAG", PRICE_TAG);
        } catch (Exception e) {
            log.error("Error at TourSearchController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("tourSearch.jsp").forward(request, response);
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
