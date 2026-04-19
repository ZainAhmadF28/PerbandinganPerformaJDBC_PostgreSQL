-- ========================================
-- MYSQL DATABASE SETUP SCRIPT
-- ========================================

-- 1. Buat Database
CREATE DATABASE IF NOT EXISTS db_pegawai;
USE db_pegawai;

-- 2. Buat Tabel Pegawai
CREATE TABLE IF NOT EXISTS pegawai (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    jabatan VARCHAR(50) NOT NULL,
    gaji DECIMAL(15,2) NOT NULL,
    departemen VARCHAR(50) NOT NULL
);

-- 3. Hapus procedure lama jika ada
DROP PROCEDURE IF EXISTS insert_pegawai;
DROP PROCEDURE IF EXISTS select_all_pegawai;
DROP PROCEDURE IF EXISTS update_pegawai;
DROP PROCEDURE IF EXISTS delete_pegawai;

-- 4. Procedure untuk INSERT
DELIMITER //
CREATE PROCEDURE insert_pegawai(
    IN p_nama VARCHAR(100),
    IN p_jabatan VARCHAR(50),
    IN p_gaji DECIMAL(15,2),
    IN p_departemen VARCHAR(50)
)
BEGIN
    INSERT INTO pegawai (nama, jabatan, gaji, departemen)
    VALUES (p_nama, p_jabatan, p_gaji, p_departemen);
END //

-- 5. Procedure untuk SELECT ALL
CREATE PROCEDURE select_all_pegawai()
BEGIN
    SELECT * FROM pegawai;
END //

-- 6. Procedure untuk UPDATE
CREATE PROCEDURE update_pegawai(
    IN p_id INT,
    IN p_nama VARCHAR(100),
    IN p_jabatan VARCHAR(50),
    IN p_gaji DECIMAL(15,2),
    IN p_departemen VARCHAR(50)
)
BEGIN
    UPDATE pegawai
    SET nama = p_nama,
        jabatan = p_jabatan,
        gaji = p_gaji,
        departemen = p_departemen
    WHERE id = p_id;
END //

-- 7. Procedure untuk DELETE
CREATE PROCEDURE delete_pegawai(IN p_id INT)
BEGIN
    DELETE FROM pegawai WHERE id = p_id;
END //

DELIMITER ;

-- 8. Insert Sample Data
INSERT INTO pegawai (nama, jabatan, gaji, departemen) VALUES
('Ahmad Zain',    'Manager',    10000000, 'IT'),
('Siti Nurhaliza','Staff',       5000000, 'HR'),
('Budi Santoso',  'Supervisor',  7500000, 'Finance'),
('Dewi Lestari',  'Staff',       5000000, 'Marketing'),
('Eko Prasetyo',  'Manager',    10000000, 'Operations');

-- 9. Verifikasi
SELECT * FROM pegawai;

-- 10. Lihat semua procedure
SHOW PROCEDURE STATUS WHERE Db = 'db_pegawai';
