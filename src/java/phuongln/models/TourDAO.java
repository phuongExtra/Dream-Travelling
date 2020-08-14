/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import phuongln.dtos.TourDTO;
import phuongln.utils.DBConnection;

/**
 *
 * @author nhatp
 */
public class TourDAO implements Serializable {
    
    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private static final DateFormat DISPLAY_ON_WEB = new SimpleDateFormat("MMM dd, yyyy");
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
    
    public boolean insertTour(TourDTO dto) throws Exception {
        boolean check = false;
        try {
            Calendar date = Calendar.getInstance();
            con = DBConnection.getConnection();
            String sql = "INSERT INTO tblTours(tourID, tourName, fromDate, toDate, img, price, quota, dateImport, destination, statusID) VALUES(?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, dto.getTourID());
            ps.setString(2, dto.getTourName());
            ps.setString(3, dto.getFromDate());
            ps.setString(4, dto.getToDate());
            ps.setString(5, dto.getImg());
            ps.setInt(6, dto.getPrice());
            ps.setInt(7, dto.getQuota());
            ps.setString(8, WRITE_IN_DB.format(date.getTime()));
            ps.setString(9, dto.getDestination());
            ps.setInt(10, 1);
            check = ps.executeUpdate() > 0;
            
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public List<TourDTO> searchTour(String startDate, String endDate, int lowerThreshold, int higherThreshold, String search, int page) throws Exception {
        TourDTO dto = null;
        List<TourDTO> result = null;
        String tourID, img, fromDate, toDate, destination, tourName;
        int price, quota, statusID;
        try {
            con = DBConnection.getConnection();
            String priSql = "SELECT TOP(3) tourName, tourID, fromDate, toDate, price, img, destination, quota, statusID FROM tblTours WHERE destination LIKE ? AND (price BETWEEN ? AND ?) ";
            String secSql = "SELECT TOP(?) tourID FROM tblTours WHERE destination LIKE ? AND (price BETWEEN ? AND ?) ";
            String order = "ORDER BY fromDate ";
            if (!startDate.isEmpty()) {
                priSql += "AND fromDate >= '" + startDate + "' ";
                secSql += "AND fromDate >= '" + startDate + "' ";
            }
            if (!endDate.isEmpty()) {
                priSql += "AND toDate <= '" + endDate + "' ";
                secSql += "AND toDate <= '" + endDate + "' ";
            }
            String sql = priSql + "AND tourID NOT IN (" + secSql + order + ") " + order;
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, lowerThreshold);
            ps.setInt(3, higherThreshold);
            
            ps.setInt(4, (page - 1) * 3);
            ps.setString(5, "%" + search + "%");
            ps.setInt(6, lowerThreshold);
            ps.setInt(7, higherThreshold);
            
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                tourID = rs.getString("tourID");
                tourName = rs.getString("tourName");
                fromDate = DISPLAY_ON_WEB.format(WRITE_IN_DB.parse(rs.getString("fromDate")));
                toDate = DISPLAY_ON_WEB.format(WRITE_IN_DB.parse(rs.getString("toDate")));
                price = rs.getInt("price");
                img = rs.getString("img");
                destination = rs.getString("destination");
                quota = rs.getInt("quota");
                statusID = rs.getInt("statusID");
                dto = new TourDTO(tourID, tourName, img, destination, fromDate, toDate, price, quota, statusID);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
        
    }
    
    public List<TourDTO> getTours() throws Exception {
        TourDTO dto = null;
        List<TourDTO> result = null;
        int price = 0;
        String destination = null;
        String tourID = null;
        String tourName = null;
        int quota = 0;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT  tourID, price, destination, tourName, quota FROM tblTours";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                tourID = rs.getString("tourID");
                price = rs.getInt("price");
                destination = rs.getString("destination");
                quota = rs.getInt("quota");
                tourName = rs.getString("tourName");
                dto = new TourDTO();
                dto.setTourID(tourID);
                dto.setPrice(price);
                dto.setDestination(destination);
                dto.setQuota(quota);
                dto.setTourName(tourName);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public TourDTO getTourByTourID(String tourID) throws Exception {
        TourDTO dto = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT tourName, fromDate, toDate, price, destination, quota FROM tblTours WHERE tourID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tourID);
            rs = ps.executeQuery();
            if (rs.next()) {
                String tourName = rs.getString("tourName");
                String fromDate = DISPLAY_ON_WEB.format(WRITE_IN_DB.parse(rs.getString("fromDate")));
                String toDate = DISPLAY_ON_WEB.format(WRITE_IN_DB.parse(rs.getString("toDate")));
                int price = rs.getInt("price");
                String destination = rs.getString("destination");
                int quota = rs.getInt("quota");
                dto = new TourDTO();
                dto.setTourName(tourName);
                dto.setFromDate(fromDate);
                dto.setToDate(toDate);
                dto.setPrice(price);
                dto.setDestination(destination);
                dto.setQuota(quota);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public int getTotalPages() throws Exception {
        int recordsCount = 0;
        int totalpages = 0;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT COUNT(tourID) as total FROM tblTours";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                recordsCount = rs.getInt("total");
            }
            totalpages = recordsCount / 3;
            if (recordsCount % 3 > 0) {
                totalpages += 1;
            }
        } finally {
            closeConnection();
        }
        return totalpages;
    }
    
    public int getTourCount() throws Exception {
        int count = 0;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT COUNT(tourID) as total FROM tblTours";
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
}
