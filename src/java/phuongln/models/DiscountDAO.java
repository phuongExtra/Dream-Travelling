/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import phuongln.dtos.DiscountDTO;
import phuongln.utils.DBConnection;

/**
 *
 * @author nhatp
 */
public class DiscountDAO {

    private Connection con = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

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

    public List<DiscountDTO> getDiscountList() throws Exception {
        List<DiscountDTO> result = null;
        String discountCode = null;
        String id = null;
        DiscountDTO dto = null;
        int value = 0;
        String expireDate = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT discountID, discountCode, value, expireDate FROM tblDiscount";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("discountID");
                discountCode = rs.getString("discountCode");
                value = rs.getInt("value");
                expireDate = rs.getString("expireDate");
                dto = new DiscountDTO(discountCode, expireDate, value);
                dto.setId(id);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<DiscountDTO> getUsedDiscountList(String username) throws Exception {
        List<DiscountDTO> result = null;
        String discountID = null;
        DiscountDTO dto = null;

        try {
            con = DBConnection.getConnection();
            String sql = "SELECT discountID FROM tblUsedDiscount WHERE username = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                discountID = rs.getString("discountID");
                dto = new DiscountDTO();
                dto.setId(discountID);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalUsedDiscount() throws Exception {
        int total = 0;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT COUNT(id) as total FROM tblUsedDiscount";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public boolean insertUsedDiscount(String id, String username) throws Exception {
        boolean check = false;
        try {
            int total = getTotalUsedDiscount();
            con = DBConnection.getConnection();
            String sql = "INSERT INTO tblUsedDiscount(id, discountID, username) values(?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, "U" + (total + 1));
            ps.setString(2, id);
            ps.setString(3, username);
            check = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
