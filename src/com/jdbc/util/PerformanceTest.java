package com.jdbc.util;

import com.jdbc.dao.PegawaiDAO;
import com.jdbc.dao.PegawaiDAOImpl;
import com.jdbc.model.Pegawai;
import java.util.HashMap;
import java.util.List;

/**
 * Class untuk testing performa JDBC
 * Menggunakan konsep: Collections (HashMap untuk menyimpan hasil)
 */
public class PerformanceTest {
    private PegawaiDAO dao;
    
    public PerformanceTest() {
        this.dao = new PegawaiDAOImpl();
    }
    
    /**
     * Test INSERT dengan 3 metode JDBC
     * @param jumlahData jumlah data yang akan di-insert
     * @return HashMap berisi hasil waktu eksekusi
     */
    public HashMap<String, Double> testInsert(int jumlahData) throws Exception {
        HashMap<String, Double> hasil = new HashMap<>();
        
        // Test Statement
        dao.truncateTable();
        long startTime = System.nanoTime();
        for (int i = 1; i <= jumlahData; i++) {
            Pegawai p = new Pegawai("Pegawai" + i, "Staff", 5000000, "IT");
            dao.insert(p, "Statement");
        }
        long endTime = System.nanoTime();
        double durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("Statement", durasiMs);
        
        // Test PreparedStatement
        dao.truncateTable();
        startTime = System.nanoTime();
        for (int i = 1; i <= jumlahData; i++) {
            Pegawai p = new Pegawai("Pegawai" + i, "Staff", 5000000, "IT");
            dao.insert(p, "PreparedStatement");
        }
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("PreparedStatement", durasiMs);
        
        // Test CallableStatement
        dao.truncateTable();
        startTime = System.nanoTime();
        for (int i = 1; i <= jumlahData; i++) {
            Pegawai p = new Pegawai("Pegawai" + i, "Staff", 5000000, "IT");
            dao.insert(p, "CallableStatement");
        }
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("CallableStatement", durasiMs);
        
        return hasil;
    }
    
    /**
     * Test SELECT dengan 3 metode JDBC
     * @return HashMap berisi hasil waktu eksekusi
     */
    public HashMap<String, Double> testSelect() throws Exception {
        HashMap<String, Double> hasil = new HashMap<>();
        
        // Test Statement
        long startTime = System.nanoTime();
        List<Pegawai> list1 = dao.selectAll("Statement");
        long endTime = System.nanoTime();
        double durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("Statement", durasiMs);
        hasil.put("Statement_Count", (double) list1.size());
        
        // Test PreparedStatement
        startTime = System.nanoTime();
        List<Pegawai> list2 = dao.selectAll("PreparedStatement");
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("PreparedStatement", durasiMs);
        hasil.put("PreparedStatement_Count", (double) list2.size());
        
        // Test CallableStatement
        startTime = System.nanoTime();
        List<Pegawai> list3 = dao.selectAll("CallableStatement");
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("CallableStatement", durasiMs);
        hasil.put("CallableStatement_Count", (double) list3.size());
        
        return hasil;
    }
    
    /**
     * Test UPDATE dengan 3 metode JDBC
     * @param jumlahData jumlah data yang akan di-update
     * @return HashMap berisi hasil waktu eksekusi
     */
    public HashMap<String, Double> testUpdate(int jumlahData) throws Exception {
        HashMap<String, Double> hasil = new HashMap<>();
        
        // Test Statement
        long startTime = System.nanoTime();
        for (int i = 1; i <= jumlahData; i++) {
            Pegawai p = new Pegawai(i, "Updated" + i, "Manager", 8000000, "HR");
            dao.update(p, "Statement");
        }
        long endTime = System.nanoTime();
        double durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("Statement", durasiMs);
        
        // Test PreparedStatement
        startTime = System.nanoTime();
        for (int i = 1; i <= jumlahData; i++) {
            Pegawai p = new Pegawai(i, "Updated" + i, "Manager", 8000000, "HR");
            dao.update(p, "PreparedStatement");
        }
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("PreparedStatement", durasiMs);
        
        // Test CallableStatement
        startTime = System.nanoTime();
        for (int i = 1; i <= jumlahData; i++) {
            Pegawai p = new Pegawai(i, "Updated" + i, "Manager", 8000000, "HR");
            dao.update(p, "CallableStatement");
        }
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("CallableStatement", durasiMs);
        
        return hasil;
    }
    
    /**
     * Test DELETE dengan 3 metode JDBC
     * @param jumlahData jumlah data yang akan di-delete
     * @return HashMap berisi hasil waktu eksekusi
     */
    public HashMap<String, Double> testDelete(int jumlahData) throws Exception {
        HashMap<String, Double> hasil = new HashMap<>();
        
        // Prepare data
        dao.truncateTable();
        for (int i = 1; i <= jumlahData * 3; i++) {
            dao.insert(new Pegawai("Pegawai" + i, "Staff", 5000000, "IT"), "PreparedStatement");
        }
        
        // Test Statement
        long startTime = System.nanoTime();
        for (int i = 1; i <= jumlahData; i++) {
            dao.delete(i, "Statement");
        }
        long endTime = System.nanoTime();
        double durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("Statement", durasiMs);
        
        // Test PreparedStatement
        startTime = System.nanoTime();
        for (int i = jumlahData + 1; i <= jumlahData * 2; i++) {
            dao.delete(i, "PreparedStatement");
        }
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("PreparedStatement", durasiMs);
        
        // Test CallableStatement
        startTime = System.nanoTime();
        for (int i = jumlahData * 2 + 1; i <= jumlahData * 3; i++) {
            dao.delete(i, "CallableStatement");
        }
        endTime = System.nanoTime();
        durasiMs = (endTime - startTime) / 1_000_000.0;
        hasil.put("CallableStatement", durasiMs);
        
        return hasil;
    }
}
