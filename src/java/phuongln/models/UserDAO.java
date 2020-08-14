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
import phuongln.dtos.UserDTO;
import phuongln.utils.DBConnection;

/**
 *
 * @author nhatp
 */
public class UserDAO implements Serializable {

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

    public UserDTO checkLogin(String name, String password) throws Exception {
        int roleID = 0;
        String fullname = "";
        int statusID = 0;
        UserDTO dto = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT roleID, statusID, fullname FROM tblUsers " + "WHERE username = ? and password = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                roleID = rs.getInt("roleID");
                statusID = rs.getInt("statusID");
                fullname = rs.getString("fullname");
                dto = new UserDTO(fullname, roleID, statusID);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public String getFullname(String name, String password) throws Exception {
        String fullname = "";
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT fullname FROM tblUsers " + "WHERE username = ? and password = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                fullname = rs.getString("fullname");
            }
        } finally {
            closeConnection();
        }
        return fullname;
    }
}
