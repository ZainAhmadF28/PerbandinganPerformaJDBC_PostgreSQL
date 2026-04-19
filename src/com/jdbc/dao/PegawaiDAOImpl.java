package com.jdbc.dao;

import com.jdbc.config.DatabaseConfig;
import com.jdbc.model.Pegawai;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementasi Interface PegawaiDAO
 * Menggunakan konsep: Interface Implementation, Exception Handling
 */
public class PegawaiDAOImpl implements PegawaiDAO {
    
    @Override
    public void insert(Pegawai p, String metode) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            
            if (metode.equals("Statement")) {
                // Menggunakan Statement (query langsung)
                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO pegawai (nama, jabatan, gaji, departemen) VALUES ('" 
                           + p.getNama() + "', '" + p.getJabatan() + "', " 
                           + p.getGaji() + ", '" + p.getDepartemen() + "')";
                stmt.executeUpdate(sql);
                stmt.close();
                
            } else if (metode.equals("PreparedStatement")) {
                // Menggunakan PreparedStatement (query dengan parameter)
                String sql = "INSERT INTO pegawai (nama, jabatan, gaji, departemen) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, p.getNama());
                pstmt.setString(2, p.getJabatan());
                pstmt.setDouble(3, p.getGaji());
                pstmt.setString(4, p.getDepartemen());
                pstmt.executeUpdate();
                pstmt.close();
                
            } else if (metode.equals("CallableStatement")) {
                // Menggunakan CallableStatement (MySQL PROCEDURE)
                CallableStatement cstmt = conn.prepareCall("{CALL insert_pegawai(?, ?, ?, ?)}");
                cstmt.setString(1, p.getNama());
                cstmt.setString(2, p.getJabatan());
                cstmt.setDouble(3, p.getGaji());
                cstmt.setString(4, p.getDepartemen());
                cstmt.execute();
                cstmt.close();
            }
        } catch (SQLException e) {
            throw new Exception("Error INSERT: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public List<Pegawai> selectAll(String metode) throws Exception {
        List<Pegawai> list = new ArrayList<>();
        Connection conn = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            ResultSet rs = null;
            
            if (metode.equals("Statement")) {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM pegawai");
                
            } else if (metode.equals("PreparedStatement")) {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM pegawai");
                rs = pstmt.executeQuery();
                
            } else if (metode.equals("CallableStatement")) {
                CallableStatement cstmt = conn.prepareCall("{CALL select_all_pegawai()}");
                rs = cstmt.executeQuery();
            }
            
            // Proses ResultSet
            while (rs != null && rs.next()) {
                Pegawai p = new Pegawai(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("jabatan"),
                    rs.getDouble("gaji"),
                    rs.getString("departemen")
                );
                list.add(p);
            }
            
            if (rs != null) rs.close();
        } catch (SQLException e) {
            throw new Exception("Error SELECT: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
        
        return list;
    }
    
    @Override
    public void update(Pegawai p, String metode) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            
            if (metode.equals("Statement")) {
                Statement stmt = conn.createStatement();
                String sql = "UPDATE pegawai SET nama='" + p.getNama() + "', jabatan='" 
                           + p.getJabatan() + "', gaji=" + p.getGaji() 
                           + ", departemen='" + p.getDepartemen() + "' WHERE id=" + p.getId();
                stmt.executeUpdate(sql);
                stmt.close();
                
            } else if (metode.equals("PreparedStatement")) {
                String sql = "UPDATE pegawai SET nama=?, jabatan=?, gaji=?, departemen=? WHERE id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, p.getNama());
                pstmt.setString(2, p.getJabatan());
                pstmt.setDouble(3, p.getGaji());
                pstmt.setString(4, p.getDepartemen());
                pstmt.setInt(5, p.getId());
                pstmt.executeUpdate();
                pstmt.close();
                
            } else if (metode.equals("CallableStatement")) {
                CallableStatement cstmt = conn.prepareCall("{CALL update_pegawai(?, ?, ?, ?, ?)}");
                cstmt.setInt(1, p.getId());
                cstmt.setString(2, p.getNama());
                cstmt.setString(3, p.getJabatan());
                cstmt.setDouble(4, p.getGaji());
                cstmt.setString(5, p.getDepartemen());
                cstmt.execute();
                cstmt.close();
            }
        } catch (SQLException e) {
            throw new Exception("Error UPDATE: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public void delete(int id, String metode) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            
            if (metode.equals("Statement")) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM pegawai WHERE id=" + id);
                stmt.close();
                
            } else if (metode.equals("PreparedStatement")) {
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pegawai WHERE id=?");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                pstmt.close();
                
            } else if (metode.equals("CallableStatement")) {
                CallableStatement cstmt = conn.prepareCall("{CALL delete_pegawai(?)}");
                cstmt.setInt(1, id);
                cstmt.execute();
                cstmt.close();
            }
        } catch (SQLException e) {
            throw new Exception("Error DELETE: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public void truncateTable() throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("TRUNCATE TABLE pegawai");
                stmt.close();
        } catch (SQLException e) {
            throw new Exception("Error TRUNCATE: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }
    }
}
