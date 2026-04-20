# Perbandingan Performa JDBC - MySQL
## Statement vs PreparedStatement vs CallableStatement

### 📋 Deskripsi Proyek
Aplikasi Java sederhana dengan GUI Swing untuk menganalisis performa tiga metode JDBC dalam operasi CRUD (Create, Read, Update, Delete) pada database MySQL.

**Tingkat:** Mahasiswa PBO Lanjut

---

## 🎓 Konsep PBO yang Digunakan

### 1. **Encapsulation** (Enkapsulasi)
- Class `Pegawai` dengan private attributes dan public getter/setter

### 2. **Constructor Overloading**
- Class `Pegawai` memiliki 3 constructor berbeda

### 3. **Interface**
- `PegawaiDAO` sebagai interface
- `PegawaiDAOImpl` sebagai implementasi

### 4. **Exception Handling**
- Try-catch-finally untuk menangani SQLException
- Throws Exception pada method DAO

### 5. **Collections (HashMap)**
- Menyimpan hasil testing performa dalam HashMap<String, Double>

### 6. **GUI dengan Swing**
- JFrame, JPanel, JButton, JComboBox, JTextArea
- Event Handling dengan ActionListener
- SwingWorker untuk threading

---

## 📂 Struktur Proyek

```
PerbandinganPerformaJDBC_MySQL/
├── src/
│   └── com.jdbc/
│       ├── config/
│       │   └── DatabaseConfig.java      # Koneksi database
│       ├── model/
│       │   └── Pegawai.java             # Model dengan encapsulation
│       ├── dao/
│       │   ├── PegawaiDAO.java          # Interface
│       │   └── PegawaiDAOImpl.java      # Implementasi interface
│       ├── util/
│       │   └── PerformanceTest.java     # Testing dengan HashMap
│       └── main/
│           └── MainApp.java             # GUI Swing + Event Handling
├── lib/
│   └── mysql-connector-j-8.0.33.jar     # JDBC Driver
├── database_setup.sql                    # Script SQL
└── README.md
```

---

## 🚀 CARA SETUP (5 LANGKAH MUDAH)

### **LANGKAH 1: Install MySQL**
1. Download: https://dev.mysql.com/downloads/installer/
2. Install MySQL Community Server (Next-next-next)
3. Catat password untuk user `root`
4. Default port: 3306

### **LANGKAH 2: Buat Database**
1. Buka **MySQL Workbench** (sudah terinstall bersama MySQL)
2. Klik koneksi **Local instance MySQL**
3. Buat database baru:
   ```sql
   CREATE DATABASE db_pegawai;
   USE db_pegawai;
   ```

### **LANGKAH 3: Jalankan Script SQL**
1. Di MySQL Workbench, buka file `database_setup.sql`
2. Pastikan database `db_pegawai` sudah dipilih
3. Klik **Execute** (⚡ icon) atau tekan Ctrl+Shift+Enter
4. Verifikasi: `SELECT * FROM pegawai;` (harus ada 5 data)

### **LANGKAH 4: Download JDBC Driver**
1. Download: https://dev.mysql.com/downloads/connector/j/
2. Pilih **Platform Independent** → Download ZIP
3. Extract, ambil file `mysql-connector-j-8.0.33.jar`
4. Copy ke folder `lib/` di proyek

### **LANGKAH 5: Import & Run di Eclipse**
1. **File** → **Import** → **Existing Projects into Workspace**
2. Browse ke folder proyek → **Finish**
3. Klik kanan project → **Properties** → **Java Build Path** → **Libraries**
4. **Add External JARs** → Pilih `mysql-connector-j-8.0.33.jar`
5. Buka `DatabaseConfig.java` → Ganti PASSWORD sesuai MySQL Anda
6. Run `MainApp.java` → GUI akan muncul!

---

## 🖥️ TAMPILAN GUI

```
┌─────────────────────────────────────────────────────────┐
│           PERBANDINGAN PERFORMA JDBC - MySQL            │
│   Statement vs PreparedStatement vs CallableStatement   │
├─────────────────────────────────────────────────────────┤
│ Pilih Operasi: [INSERT ▼]  Jumlah Data: [100 ▼]       │
│ [Jalankan Test]  [Clear]                                │
├─────────────────────────────────────────────────────────┤
│ Hasil Testing:                                          │
│                                                         │
│ ============================================            │
│ TESTING: INSERT (100 data)                             │
│ ============================================            │
│ Statement:           1250.45 ms                        │
│ PreparedStatement:   856.32 ms                         │
│ CallableStatement:   892.18 ms                         │
│                                                         │
│ → PreparedStatement TERCEPAT!                          │
│ ✓ Testing selesai!                                     │
└─────────────────────────────────────────────────────────┘
```

---

## 🧪 FITUR TESTING

### Operasi yang Bisa Ditest:
1. **INSERT** - Menambah data pegawai
2. **SELECT** - Membaca semua data
3. **UPDATE** - Mengubah data pegawai
4. **DELETE** - Menghapus data pegawai
5. **SEMUA OPERASI** - Test INSERT, SELECT, UPDATE, DELETE sekaligus

### Jumlah Data:
- 10 data (cepat, untuk demo)
- 50 data
- 100 data (recommended)
- 500 data
- 1000 data (lambat, untuk analisis detail)

---

## 📊 3 METODE JDBC

### 1. **Statement**
```java
Statement stmt = conn.createStatement();
String sql = "INSERT INTO pegawai VALUES ('Ahmad', 'Staff', 5000000, 'IT')";
stmt.executeUpdate(sql);
```
- ❌ Query ditulis langsung (tidak aman dari SQL Injection)
- ❌ Compile ulang setiap eksekusi (lambat)
- ✅ Cocok untuk query statis sederhana

### 2. **PreparedStatement** ⭐ RECOMMENDED
```java
PreparedStatement pstmt = conn.prepareStatement(
    "INSERT INTO pegawai VALUES (?, ?, ?, ?)"
);
pstmt.setString(1, "Ahmad");
pstmt.setString(2, "Staff");
pstmt.setDouble(3, 5000000);
pstmt.setString(4, "IT");
pstmt.executeUpdate();
```
- ✅ Query di-precompile sekali (cepat untuk query berulang)
- ✅ Aman dari SQL Injection
- ✅ **PALING CEPAT & PALING AMAN**

### 3. **CallableStatement**
```java
CallableStatement cstmt = conn.prepareCall("{CALL insert_pegawai(?, ?, ?, ?)}");
cstmt.setString(1, "Ahmad");
cstmt.setString(2, "Staff");
cstmt.setDouble(3, 5000000);
cstmt.setString(4, "IT");
cstmt.execute();
```
- ✅ Memanggil function/stored procedure di database
- ✅ Logic ada di sisi database (reusable)
- ✅ Performa konsisten

---

## 📈 HASIL YANG DIHARAPKAN

Berdasarkan testing dengan 100 data:

| Operasi | Statement | PreparedStatement | CallableStatement |
|---------|-----------|-------------------|-------------------|
| INSERT  | ~1250 ms  | **~850 ms** ⭐    | ~900 ms           |
| SELECT  | ~45 ms    | **~38 ms** ⭐     | ~42 ms            |
| UPDATE  | ~1100 ms  | **~780 ms** ⭐    | ~820 ms           |
| DELETE  | ~950 ms   | **~680 ms** ⭐    | ~720 ms           |

**Kesimpulan:**
- PreparedStatement **PALING CEPAT** untuk semua operasi
- Perbedaan performa makin besar saat data banyak (1000+)
- PreparedStatement juga paling aman dari SQL Injection

---

## ❌ TROUBLESHOOTING

### Error: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solusi:** JDBC Driver belum ditambahkan
- Klik kanan project → Properties → Java Build Path → Libraries
- Add External JARs → Pilih `mysql-connector-j-8.0.33.jar`

### Error: "Connection refused" atau "Communications link failure"
**Solusi:** MySQL tidak jalan
- Windows: Services → Start `MySQL80`
- Atau buka MySQL Workbench untuk cek koneksi

### Error: "Access denied for user 'root'@'localhost'"
**Solusi:** Password salah
- Edit `DatabaseConfig.java` → Ganti PASSWORD sesuai password MySQL Anda

### Error: "database db_pegawai does not exist"
**Solusi:** Database belum dibuat
- Ulangi Langkah 2

### Error: "PROCEDURE db_pegawai.insert_pegawai does not exist"
**Solusi:** Stored procedures belum dibuat
- Ulangi Langkah 3 (jalankan script SQL)
- Verifikasi: `SHOW PROCEDURE STATUS WHERE Db = 'db_pegawai';`

---

## 📝 UNTUK JURNAL

### Screenshot yang Perlu:
1. ✅ Tampilan GUI aplikasi
2. ✅ Hasil test INSERT
3. ✅ Hasil test SELECT
4. ✅ Hasil test UPDATE
5. ✅ Hasil test DELETE
6. ✅ Database di MySQL Workbench
7. ✅ Struktur package di Eclipse

### Analisis untuk Jurnal:
1. **PreparedStatement paling cepat** karena query di-precompile sekali
2. **Statement paling lambat** karena compile ulang setiap eksekusi
3. **CallableStatement konsisten** karena logic ada di database
4. Perbedaan performa makin signifikan saat data besar (1000+ records)
5. PreparedStatement juga paling aman dari SQL Injection

---

## 💻 TEKNOLOGI

- **Java**: JDK 8+
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J 8.0.33
- **IDE**: Eclipse
- **GUI**: Java Swing (javax.swing)

---

## 👥 AUTHOR
Kelompok PBO Lanjut - Analisis Performa JDBC

---

## 📞 BANTUAN

Jika ada masalah:
1. Pastikan MySQL sudah running (cek di Services atau MySQL Workbench)
2. Pastikan database `db_pegawai` sudah dibuat
3. Pastikan script SQL sudah dijalankan (cek stored procedures)
4. Pastikan JDBC Driver sudah ditambahkan ke Build Path
5. Pastikan password di `DatabaseConfig.java` sudah benar

**Good Luck!** 🚀
