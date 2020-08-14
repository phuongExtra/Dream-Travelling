/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import phuongln.dtos.TourDTO;
import phuongln.dtos.TourInvalidObj;
import phuongln.models.TourDAO;
import phuongln.utils.Utilities;

/**
 *
 * @author nhatp
 */
public class CreateTourController extends HttpServlet {

    private static final String PAGE = "createTour.jsp";
    private static final String INVALID = "createTour.jsp";
    static Logger log = Logger.getLogger(CreateTourController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = PAGE;
        try {
            String action = request.getParameter("action");
            if (action.equals("Create")) {
                String tourName = request.getParameter("tourName");
                String destination = request.getParameter("destination");
                String tmp1 = request.getParameter("price");
                String tmp2 = request.getParameter("quota");
                String from = request.getParameter("fromDate");
                String to = request.getParameter("toDate");
                String img = request.getParameter("img");
                boolean valid = true;
                int price = 0;
                int quota = 0;
                TourInvalidObj obj = new TourInvalidObj();
                if (tourName.isEmpty()) {
                    valid = false;
                    obj.setNameErr("Name must not be empty");
                } else if (tourName.length() > 30) {
                    valid = false;
                    obj.setNameErr("Name is too long");
                }
                if (destination.isEmpty()) {
                    valid = false;
                    obj.setDesErr("Place must not be empty");
                } else if (destination.length() > 30) {
                    valid = false;
                    obj.setDesErr("Place is too long");
                }
                if (tmp1.isEmpty()) {
                    price = 0;
                    obj.setPriceErr("Price must not be empty");
                    valid = false;
                }
                if (tmp2.isEmpty()) {
                    quota = 0;
                    obj.setQuotaErr("Quota must not be empty");
                    valid = false;
                }
                price = Integer.parseInt(tmp1);
                quota = Integer.parseInt(tmp2);
                if (price <= 0) {
                    valid = false;
                    obj.setPriceErr("Price must be higher than zero");
                }
                if (quota <= 0) {
                    valid = false;
                    obj.setQuotaErr("Quota must be higher than zero");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fromDate = sdf.parse(from);
                Date toDate = sdf.parse(to);
                if (fromDate.after(toDate)) {
                    valid = false;
                    obj.setDateErr("From date must be lesser than To date");
                } else if (fromDate.before(new Date())) {
                    valid = false;
                    obj.setDateErr("Must be after today");
                }
                if (valid) {
                    TourDAO dao = new TourDAO();
                    int count = dao.getTourCount();
                    TourDTO dto = new TourDTO("T" + (count + 1), tourName, "img/" + img, destination, from, to, price, quota, 1);
                    if (dao.insertTour(dto)) {
                        request.setAttribute("IMG", Utilities.getImageList());
                        request.setAttribute("SUCCESS", "Insert successfull!");
                    } else {
                        request.setAttribute("IMG", Utilities.getImageList());
                        request.setAttribute("INVALID", "Insert failed!");
                    }
                } else {
                    url = INVALID;
                    request.setAttribute("INVALID", obj);
                    request.setAttribute("IMG", Utilities.getImageList());

                }
            }
            request.setAttribute("IMG", Utilities.getImageList());
        } catch (Exception e) {
            log.error("Error At CreateTourcontroller: " + e.getMessage());
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
     * @throws ServletException if a servlet-specific obj occurs
     * @throws IOException if an I/O obj occurs
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
     * @throws ServletException if a servlet-specific obj occurs
     * @throws IOException if an I/O obj occurs
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
