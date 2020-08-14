/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phuongln.dtos.BookingDTO;
import phuongln.dtos.DiscountDTO;
import phuongln.dtos.TourDTO;
import phuongln.dtos.UserDTO;
import phuongln.models.BookingDAO;
import phuongln.models.DiscountDAO;
import phuongln.models.TourDAO;
import phuongln.utils.CartObject;

/**
 *
 * @author nhatp
 */
public class BookingController extends HttpServlet {

    private static final String DEFAULT = "TourSearchController";
    private static final String BACK_TO_CART = "cart.jsp";
    static Logger log = Logger.getLogger(BookingController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = DEFAULT;
        try {
            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CART");
            String action = request.getParameter("action");

            if (action.equals("Booking")) {
                String tourID = request.getParameter("tourID");
                int amount = Integer.parseInt(request.getParameter("amount"));
                TourDAO dao = new TourDAO();
                TourDTO tour = dao.getTourByTourID(tourID);
                UserDTO user = (UserDTO) session.getAttribute("USER");
                int count = new BookingDAO().getBookingIDCount();
                String price = request.getParameter("price");
                int available = tour.getQuota() - (new BookingDAO().getBookedSlots(tourID));
                BookingDTO dto = new BookingDTO("B" + count + 1, tour.getDestination(), user.getUsername(), tourID, tour.getFromDate(), tour.getToDate(), amount, tour.getPrice(), amount * tour.getPrice());
                dto.setAvailable(available);
                cart.addToCart(dto);
                session.setAttribute("TOTAL_COST", cart.getTotal());
                request.setAttribute("PRICE", price);
                session.setAttribute("CART", cart);
                session.setAttribute("CART_SIZE", cart.size());
            } else if (action.equals("Update")) {
                int size = (int) session.getAttribute("CART_SIZE");
                for (int i = 1; i <= size; i++) {
                    String tourID = request.getParameter("tourID" + i);
                    int amount = Integer.parseInt(request.getParameter("amount" + tourID));
                    if (cart.update(tourID, amount)) {
                        request.setAttribute("SUCCESS", "Update successfully");
                        session.setAttribute("TOTAL_COST", cart.getTotal());
                    } else {
                        request.setAttribute("ERROR", "Update failed");
                    }
                }
                url = BACK_TO_CART;
            } else if (action.equals("Discount")) {
                List<DiscountDTO> discountList = (List<DiscountDTO>) session.getAttribute("DISCOUNT_LIST");
                String code = request.getParameter("code");
                String id = (String) session.getAttribute("DISCOUNT_ID");
                if (id == null) {
                    for (int i = 0; i < discountList.size(); i++) {

                        if (discountList.get(i).getCode().equals(code)) {
                            int total = cart.getTotal() - (cart.getTotal() * discountList.get(i).getValue() / 100);
                            session.setAttribute("DISCOUNT_VALUE", discountList.get(i).getValue());
                            session.setAttribute("DISCOUNT_ID", discountList.get(i).getId());
                            request.setAttribute("SUCCESS", "Use discount successfully!");
                            session.setAttribute("TOTAL_COST", total);
                        }
                    }
                } else {
                    request.setAttribute("ERROR", "Only 1 code per payment!");
                }
                url = BACK_TO_CART;
            } else if (action.equals("Confirm")) {
                String tourID = request.getParameter("tourID");
                BookingDAO dao = new BookingDAO();
                int booked = 0;
                int available = 0;
                boolean checkValid = true;
                List<TourDTO> tourList = new TourDAO().getTours();
                for (int i = 0; i < tourList.size(); i++) {
                    if (cart.getCart().containsKey(tourList.get(i).getTourID())) {
                        List<BookingDTO> bookedList = dao.getBookedToursByTourID(tourID);
                        for (int j = 0; j < bookedList.size(); j++) {
                            if (tourList.get(i).getTourID().equals(bookedList.get(j).getTourID())) {
                                booked += bookedList.get(i).getAmount();
                            }
                        }

                        available = tourList.get(i).getQuota() - booked;
                        if (cart.getCart().get(tourList.get(i).getTourID()).getAmount() > available) {
                            request.setAttribute("ERROR", "Booking failed at " + tourList.get(i).getTourName() + "!");
                            checkValid = false;
                        }

                    }
                }
                if (checkValid) {
                    UserDTO user = (UserDTO) session.getAttribute("USER");
                    String discountID = (String) session.getAttribute("DISCOUNT_ID");
                    int value = (int) session.getAttribute("DISCOUNT_VALUE");
                    for (int i = 0; i < tourList.size(); i++) {
                        if (cart.getCart().containsKey(tourList.get(i).getTourID())) {
                            cart.getCart().get(tourList.get(i).getTourID()).setDiscountID(discountID);
                            int total = (cart.getCart().get(tourList.get(i).getTourID()).getTotal()) - (cart.getCart().get(tourList.get(i).getTourID()).getTotal() * value / 100);
                            cart.getCart().get(tourList.get(i).getTourID()).setTotal(total);
                            if (dao.insertBookingTour(cart.getCart().get(tourList.get(i).getTourID()))) {
                                List<DiscountDTO> list = (List<DiscountDTO>) session.getAttribute("DISCOUNT_LIST");
                                for (int j = 0; j < list.size(); j++) {
                                    if (list.get(i).getId().equals(cart.getCart().get(tourList.get(i).getTourID()).getDiscountID())) {
                                        list.remove(j);
                                    }
                                }
                                session.setAttribute("DISCOUNT_LIST", list);
                                request.setAttribute("SUCCESS", "Booking successfully!");
                                cart.getCart().remove(tourList.get(i).getTourID());
                                session.setAttribute("CART_SIZE", cart.size());
                                i--;
                            } else {
                                request.setAttribute("ERROR", "Booking failed!");
                            }
                        }
                    }
                    new DiscountDAO().insertUsedDiscount(discountID, user.getUsername());
                    session.setAttribute("TOTAL_COST", 0);
                    session.setAttribute("DISCOUNT_VALUE", null);
                } else {
                    request.setAttribute("ERROR", "Booking failed!");
                }
                url = BACK_TO_CART;
            }
        } catch (Exception e) {
            log.error("Error at BookingController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
