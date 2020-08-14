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
import phuongln.dtos.AccountInvalidObj;
import phuongln.dtos.DiscountDTO;
import phuongln.dtos.UserDTO;
import phuongln.models.DiscountDAO;
import phuongln.models.UserDAO;
import phuongln.utils.CartObject;

/**
 *
 * @author nhatp
 */
public class AccountManageController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "TourSearchController";
    private static final String INVALID = "login.jsp";
    static Logger log = Logger.getLogger(AccountManageController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (action.equals("Login")) {
                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");
                AccountInvalidObj obj = new AccountInvalidObj();
                UserDTO dto = null;
                UserDAO dao = new UserDAO();

                boolean valid = true;
                if (username == null) {
                    obj.setUsernameErr("Username must not empty!");
                    valid = false;
                }
                if (password == null) {
                    obj.setPasswordErr("Password must not empty!");
                    valid = false;
                }
                if (valid) {
                    dto = dao.checkLogin(username, password);
                    int roleID = dto.getRoleID();
                    int statusID = dto.getStatusID();
                    if (roleID == 0) {
                        obj.setLoginErr("Incorrect Username or Password!");
                        url = INVALID;
                    } else {
                        if (statusID == 3) {
                            obj.setLoginErr("Your acount has not been activated yet!");
                            url = INVALID;
                        } else if (statusID == 1) {
                            dto.setUsername(username);
                            HttpSession session = request.getSession();
                            session.setAttribute("USER", dto);

                            if (roleID == 1) {
                                url = SUCCESS;
                            } else if (roleID == 2) {
                                DiscountDAO dDao = new DiscountDAO();
                                CartObject cart = new CartObject();
                                List<DiscountDTO> discountList = dDao.getDiscountList();
                                List<DiscountDTO> usedDiscountList = dDao.getUsedDiscountList(username);
                                for (DiscountDTO coupon : discountList) {
                                    for (DiscountDTO usedCoupon : usedDiscountList) {
                                        if (coupon.getId().equals(usedCoupon.getId())) {
                                            discountList.remove(coupon);
                                        }
                                    }
                                }
                                session.setAttribute("DISCOUNT_LIST", discountList);
                                session.setAttribute("CART", cart);
                                session.setAttribute("CART_SIZE", 0);
                                session.setAttribute("CART_TOTAL_COST", 0);
                                url = SUCCESS;
                            } else {
                                request.setAttribute("ERROR", "Your role is invalid");
                            }

                        } else {
                            obj.setLoginErr("Your acount is not available");
                            url = INVALID;
                        }
                    }
                } else {
                    request.setAttribute("INVALID", obj);
                    url = INVALID;
                }
            } else {
                request.setAttribute("ERROR", "Your action is invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error at AccountManageController: " + e.getMessage());
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
