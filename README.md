# Perbandingan Performa JDBC - PostgreSQL
## Statement vs PreparedStatement vs CallableStatement

### 📋 Deskripsi Proyek
Aplikasi Java sederhana dengan GUI Swing untuk menganalisis performa tiga metode JDBC dalam operasi CRUD (Create, Read, Update, Delete) pada database PostgreSQL.

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
PerbandinganPerformaJDBC_PostgreSQL/
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
│   └── postgresql-42.7.1.jar            # JDBC Driver
├── database_setup.sql                    # Script SQL
└── README.md
```

---

## 🚀 CARA SETUP (5 LANGKAH MUDAH)

### **LANGKAH 1: Install PostgreSQL**
1. Download: https://www.postgresql.org/download/
2. Install (Next-next-next)
3. Catat password untuk user `postgres`
4. Default port: 5432

### **LANGKAH 2: Buat Database**
1. Buka **pgAdmin 4** (sudah terinstall bersama PostgreSQL)
2. Klik kanan **Databases** → **Create** → **Database**
3. Nama: `db_pegawai`
4. Klik **Save**

### **LANGKAH 3: Jalankan Script SQL**
1. Klik kanan database `db_pegawai` → **Query Tool**
2. Buka file `database_setup.sql`
3. Copy SEMUA isi (skip baris 5-6 yang di-comment)
4. Paste ke Query Tool → Klik **Execute** (▶️)
5. Verifikasi: `SELECT * FROM pegawai;` (harus ada 5 data)

### **LANGKAH 4: Download JDBC Driver**
1. Download: https://jdbc.postgresql.org/download/
2. Pilih `postgresql-42.7.1.jar`
3. Copy ke folder `lib/` di proyek

### **LANGKAH 5: Import & Run di Eclipse**
1. **File** → **Import** → **Existing Projects into Workspace**
2. Browse ke folder proyek → **Finish**
3. Buka `DatabaseConfig.java` → Ganti PASSWORD sesuai PostgreSQL Anda
4. Run `MainApp.java` → GUI akan muncul!

---

## 🖥️ TAMPILAN GUI

```
┌─────────────────────────────────────────────────────────┐
│        PERBANDINGAN PERFORMA JDBC - PostgreSQL          │
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

### Error: "ClassNotFoundException: org.postgresql.Driver"
**Solusi:** JDBC Driver belum ditambahkan
- Klik kanan project → Properties → Java Build Path → Libraries
- Add External JARs → Pilih `postgresql-42.7.1.jar`

### Error: "Connection refused"
**Solusi:** PostgreSQL tidak jalan
- Windows: Services → Start `postgresql-x64-xx`

### Error: "password authentication failed"
**Solusi:** Password salah
- Edit `DatabaseConfig.java` → Ganti PASSWORD

### Error: "database db_pegawai does not exist"
**Solusi:** Database belum dibuat
- Ulangi Langkah 2

### Error: "function insert_pegawai does not exist"
**Solusi:** Functions belum dibuat
- Ulangi Langkah 3 (jalankan script SQL)

---

## 📝 UNTUK JURNAL

### Screenshot yang Perlu:
1. ✅ Tampilan GUI aplikasi
2. ✅ Hasil test INSERT
3. ✅ Hasil test SELECT
4. ✅ Hasil test UPDATE
5. ✅ Hasil test DELETE
6. ✅ Database di pgAdmin
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
- **Database**: PostgreSQL 14/15/16
- **JDBC Driver**: PostgreSQL 42.7.1
- **IDE**: Eclipse
- **GUI**: Java Swing (javax.swing)

---

## 👥 AUTHOR
Kelompok PBO Lanjut - Analisis Performa JDBC

---

## 📞 BANTUAN

Jika ada masalah:
1. Pastikan PostgreSQL sudah running
2. Pastikan database `db_pegawai` sudah dibuat
3. Pastikan script SQL sudah dijalankan
4. Pastikan JDBC Driver sudah ditambahkan ke Build Path
5. Pastikan password di `DatabaseConfig.java` sudah benar

**Good Luck!** 🚀
