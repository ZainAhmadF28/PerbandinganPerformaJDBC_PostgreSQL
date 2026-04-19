package com.jdbc.model;

/**
 * Class Model Pegawai
 * Menggunakan konsep: Encapsulation, Constructor Overloading
 */
public class Pegawai {
    private int id;
    private String nama;
    private String jabatan;
    private double gaji;
    private String departemen;

    // Constructor default
    public Pegawai() {
    }

    // Constructor overloading - untuk insert (tanpa id)
    public Pegawai(String nama, String jabatan, double gaji, String departemen) {
        this.nama = nama;
        this.jabatan = jabatan;
        this.gaji = gaji;
        this.departemen = departemen;
    }

    // Constructor overloading - untuk select/update (dengan id)
    public Pegawai(int id, String nama, String jabatan, double gaji, String departemen) {
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
        this.gaji = gaji;
        this.departemen = departemen;
    }

    // Getter dan Setter (Encapsulation)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public double getGaji() {
        return gaji;
    }

    public void setGaji(double gaji) {
        this.gaji = gaji;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nama + " | " + jabatan + " | Rp " + gaji + " | " + departemen;
    }
}
