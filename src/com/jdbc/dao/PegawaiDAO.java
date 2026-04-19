package com.jdbc.dao;

import com.jdbc.model.Pegawai;
import java.util.List;

/**
 * Interface DAO untuk operasi CRUD Pegawai
 * Menggunakan konsep: Interface
 */
public interface PegawaiDAO {
    
    // Method untuk INSERT
    void insert(Pegawai pegawai, String metode) throws Exception;
    
    // Method untuk SELECT ALL
    List<Pegawai> selectAll(String metode) throws Exception;
    
    // Method untuk UPDATE
    void update(Pegawai pegawai, String metode) throws Exception;
    
    // Method untuk DELETE
    void delete(int id, String metode) throws Exception;
    
    // Method untuk TRUNCATE (hapus semua data)
    void truncateTable() throws Exception;
}
