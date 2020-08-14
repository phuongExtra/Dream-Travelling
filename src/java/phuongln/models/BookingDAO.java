/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import phuongln.dtos.BookingDTO;
import phuongln.utils.DBConnection;

/**
 *
 * @author nhatp
 */
public class BookingDAO {
    
    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private static final DateFormat WRITE_IN_DB = new SimpleDateFormat("yyyy-MM-dd");
    
    public void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (con != null) {
            con.close();
        }
    }
    
    public List<BookingDTO> getBookedToursByTourID(String tourID) throws Exception {
        List<BookingDTO> result = null;
        BookingDTO dto = null;
        String bookingID;
        int amount;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT BookingID, amount FROM tblBooking WHERE statusID = 1 AND TourID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tourID);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                bookingID = rs.getString("bookingID");
                amount = rs.getInt("amount");
                dto = new BookingDTO();
                dto.setBookingID(bookingID);
                dto.setAmount(amount);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public int getBookedSlots(String tourID) throws Exception {
        int amount = 0;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT amount FROM tblBooking WHERE tourID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tourID);
            rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("amount");
            }
        } finally {
            closeConnection();
        }
        return amount;
    }
    
    public int getBookingIDCount() throws Exception {
        int count = 0;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT COUNT(bookingID) as total FROM tblBooking";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } finally {
            closeConnection();
        }
        return count;
    }
    
    public boolean insertBookingTour(BookingDTO dto) throws Exception {
        boolean check = false;
        int bookingcount = getBookingIDCount();
        Calendar date = Calendar.getInstance();
        
        try {
            con = DBConnection.getConnection();
            String sql = "INSERT INTO tblBooking(BookingID, amount, total, date, username, tourID, statusID, discountID) values(?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, "B" + (bookingcount + 1));
            ps.setInt(2, dto.getAmount());
            ps.setInt(3, dto.getTotal());
            ps.setString(4, WRITE_IN_DB.format(date.getTime()));
            ps.setString(5, dto.getUsername());
            ps.setString(6, dto.getTourID());
            ps.setInt(7, 1);
            ps.setString(8, dto.getDiscountID());
            check = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
