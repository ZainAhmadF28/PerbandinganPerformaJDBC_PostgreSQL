package com.jdbc.main;

import com.jdbc.dao.PegawaiDAO;
import com.jdbc.dao.PegawaiDAOImpl;
import com.jdbc.model.Pegawai;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Aplikasi CRUD Pegawai dengan Perbandingan Performa JDBC
 * Konsep PBO: GUI, Event Handling, Exception Handling, SwingWorker, Interface
 */
public class MainApp extends JFrame {

    private static final long serialVersionUID = 1L;

    // DAO
    private PegawaiDAO dao = new PegawaiDAOImpl();

    // Komponen Form
    private JTextField txtNama, txtJabatan, txtGaji, txtDepartemen, txtId;
    private JComboBox<String> comboMetode;
    private JButton btnTambah, btnUpdate, btnHapus, btnBersihkan, btnRefresh;

    // Tabel
    private JTable tabelPegawai;
    private DefaultTableModel tableModel;

    // Log
    private JTextArea logArea;

    // Warna tema
    private static final Color WARNA_HEADER  = new Color(26, 82, 118);
    private static final Color WARNA_TAMBAH  = new Color(39, 174, 96);
    private static final Color WARNA_UPDATE  = new Color(41, 128, 185);
    private static final Color WARNA_HAPUS   = new Color(192, 57, 43);
    private static final Color WARNA_BERSIH  = new Color(127, 140, 141);
    private static final Color WARNA_REFRESH = new Color(142, 68, 173);

    public MainApp() {
        initComponents();
        muatDataKeTabel("PreparedStatement");
    }

    // =========================================================
    // INISIALISASI UI
    // =========================================================
    private void initComponents() {
        setTitle("Aplikasi CRUD Pegawai — Perbandingan Performa JDBC");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        add(buatPanelHeader(), BorderLayout.NORTH);
        add(buatPanelKiri(),   BorderLayout.WEST);
        add(buatPanelTengah(), BorderLayout.CENTER);
        add(buatPanelLog(),    BorderLayout.SOUTH);
    }

    // --- Header ---
    private JPanel buatPanelHeader() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(WARNA_HEADER);
        panel.setPreferredSize(new Dimension(1100, 65));
        panel.setBorder(new EmptyBorder(8, 20, 8, 20));

        JLabel lblJudul = new JLabel("APLIKASI CRUD DATA PEGAWAI");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Perbandingan Performa: Statement  |  PreparedStatement  |  CallableStatement");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSub.setForeground(new Color(174, 214, 241));

        panel.add(lblJudul);
        panel.add(lblSub);
        return panel;
    }

    // --- Panel Kiri: Form Input ---
    private JPanel buatPanelKiri() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(280, 600));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(new Color(245, 248, 250));

        // ---- Judul Form ----
        JLabel lblForm = new JLabel("FORM DATA PEGAWAI");
        lblForm.setFont(new Font("Arial", Font.BOLD, 13));
        lblForm.setForeground(WARNA_HEADER);
        lblForm.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(lblForm);
        panel.add(Box.createVerticalStrut(12));

        // ---- ID (readonly) ----
        panel.add(buatLabel("ID (otomatis)"));
        txtId = buatTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(236, 240, 241));
        panel.add(txtId);
        panel.add(Box.createVerticalStrut(8));

        // ---- Nama ----
        panel.add(buatLabel("Nama Pegawai *"));
        txtNama = buatTextField();
        panel.add(txtNama);
        panel.add(Box.createVerticalStrut(8));

        // ---- Jabatan ----
        panel.add(buatLabel("Jabatan *"));
        txtJabatan = buatTextField();
        panel.add(txtJabatan);
        panel.add(Box.createVerticalStrut(8));

        // ---- Gaji ----
        panel.add(buatLabel("Gaji (Rp) *"));
        txtGaji = buatTextField();
        panel.add(txtGaji);
        panel.add(Box.createVerticalStrut(8));

        // ---- Departemen ----
        panel.add(buatLabel("Departemen *"));
        txtDepartemen = buatTextField();
        panel.add(txtDepartemen);
        panel.add(Box.createVerticalStrut(15));

        // ---- Pilih Metode JDBC ----
        panel.add(buatLabel("Metode JDBC"));
        String[] metodeList = {"Statement", "PreparedStatement", "CallableStatement"};
        comboMetode = new JComboBox<>(metodeList);
        comboMetode.setSelectedIndex(1);
        comboMetode.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        comboMetode.setAlignmentX(LEFT_ALIGNMENT);
        comboMetode.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(comboMetode);
        panel.add(Box.createVerticalStrut(20));

        // ---- Tombol CRUD ----
        panel.add(buatLabel("AKSI"));
        panel.add(Box.createVerticalStrut(6));

        btnTambah   = buatTombol("+ Tambah",   WARNA_TAMBAH);
        btnUpdate   = buatTombol("✎ Update",   WARNA_UPDATE);
        btnHapus    = buatTombol("✕ Hapus",    WARNA_HAPUS);
        btnBersihkan= buatTombol("↺ Bersihkan", WARNA_BERSIH);
        btnRefresh  = buatTombol("⟳ Refresh Tabel", WARNA_REFRESH);

        panel.add(btnTambah);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnUpdate);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnHapus);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnBersihkan);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnRefresh);

        // ---- Event Handling ----
        btnTambah.addActionListener(e -> aksiTambah());
        btnUpdate.addActionListener(e -> aksiUpdate());
        btnHapus.addActionListener(e -> aksiHapus());
        btnBersihkan.addActionListener(e -> bersihkanForm());
        btnRefresh.addActionListener(e -> muatDataKeTabel((String) comboMetode.getSelectedItem()));

        return panel;
    }

    // --- Panel Tengah: Tabel ---
    private JPanel buatPanelTengah() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 5, 10));

        JLabel lblTabel = new JLabel("DATA PEGAWAI");
        lblTabel.setFont(new Font("Arial", Font.BOLD, 13));
        lblTabel.setForeground(WARNA_HEADER);
        panel.add(lblTabel, BorderLayout.NORTH);

        // Kolom tabel
        String[] kolom = {"ID", "Nama", "Jabatan", "Gaji", "Departemen"};
        tableModel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tabelPegawai = new JTable(tableModel);
        tabelPegawai.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelPegawai.setRowHeight(26);
        tabelPegawai.setSelectionBackground(new Color(174, 214, 241));
        tabelPegawai.setGridColor(new Color(220, 220, 220));

        // Custom renderer untuk header agar warna tidak ditimpa Look and Feel
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(WARNA_HEADER);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Arial", Font.BOLD, 12));
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(52, 120, 170)));
                lbl.setOpaque(true);
                return lbl;
            }
        };
        tabelPegawai.getTableHeader().setDefaultRenderer(headerRenderer);
        tabelPegawai.getTableHeader().setPreferredSize(new Dimension(0, 32));
        tabelPegawai.getTableHeader().setReorderingAllowed(false);

        // Lebar kolom
        tabelPegawai.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelPegawai.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabelPegawai.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelPegawai.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabelPegawai.getColumnModel().getColumn(4).setPreferredWidth(110);

        // Klik baris → isi form
        tabelPegawai.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                isiFormDariTabel();
            }
        });

        JScrollPane scroll = new JScrollPane(tabelPegawai);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // --- Panel Bawah: Log Terminal ---
    private JPanel buatPanelLog() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setPreferredSize(new Dimension(1100, 160));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
            new EmptyBorder(5, 10, 8, 10)
        ));

        JLabel lblLog = new JLabel("LOG EKSEKUSI JDBC");
        lblLog.setFont(new Font("Arial", Font.BOLD, 12));
        lblLog.setForeground(WARNA_HEADER);
        panel.add(lblLog, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setEditable(false);
        logArea.setBackground(new Color(30, 39, 46));
        logArea.setForeground(new Color(162, 217, 206));
        logArea.setMargin(new Insets(6, 8, 6, 8));
        logArea.setText("[LOG] Aplikasi siap. Silakan pilih metode JDBC dan lakukan operasi CRUD.\n");

        JScrollPane scrollLog = new JScrollPane(logArea);
        scrollLog.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        panel.add(scrollLog, BorderLayout.CENTER);

        // Tombol clear log
        JButton btnClearLog = new JButton("Clear Log");
        btnClearLog.setFont(new Font("Arial", Font.PLAIN, 11));
        btnClearLog.addActionListener(e -> logArea.setText(""));
        JPanel panelClear = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 2));
        panelClear.add(btnClearLog);
        panel.add(panelClear, BorderLayout.EAST);

        return panel;
    }

    // =========================================================
    // HELPER BUILDER
    // =========================================================
    private JLabel buatLabel(String teks) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField buatTextField() {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        tf.setAlignmentX(LEFT_ALIGNMENT);
        tf.setFont(new Font("Arial", Font.PLAIN, 12));
        return tf;
    }

    private JButton buatTombol(String teks, Color warna) {
        JButton btn = new JButton(teks);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setBackground(warna);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // =========================================================
    // OPERASI CRUD
    // =========================================================
    private void aksiTambah() {
        if (!validasiForm()) return;
        String metode = (String) comboMetode.getSelectedItem();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() throws Exception {
                Pegawai p = ambilDataForm();
                long start = System.nanoTime();
                dao.insert(p, metode);
                long durasi = System.nanoTime() - start;
                log("INSERT", metode, durasi);
                return null;
            }
            protected void done() {
                try {
                    get();
                    muatDataKeTabel(metode);
                    bersihkanForm();
                } catch (Exception ex) {
                    logError("INSERT", ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void aksiUpdate() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel dulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasiForm()) return;
        String metode = (String) comboMetode.getSelectedItem();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() throws Exception {
                Pegawai p = ambilDataForm();
                p.setId(Integer.parseInt(txtId.getText()));
                long start = System.nanoTime();
                dao.update(p, metode);
                long durasi = System.nanoTime() - start;
                log("UPDATE", metode, durasi);
                return null;
            }
            protected void done() {
                try {
                    get();
                    muatDataKeTabel(metode);
                    bersihkanForm();
                } catch (Exception ex) {
                    logError("UPDATE", ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void aksiHapus() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel dulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Hapus pegawai ID " + txtId.getText() + "?", "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        String metode = (String) comboMetode.getSelectedItem();
        int id = Integer.parseInt(txtId.getText());

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() throws Exception {
                long start = System.nanoTime();
                dao.delete(id, metode);
                long durasi = System.nanoTime() - start;
                log("DELETE", metode, durasi);
                return null;
            }
            protected void done() {
                try {
                    get();
                    muatDataKeTabel(metode);
                    bersihkanForm();
                } catch (Exception ex) {
                    logError("DELETE", ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    // =========================================================
    // MUAT DATA KE TABEL
    // =========================================================
    private void muatDataKeTabel(String metode) {
        SwingWorker<List<Pegawai>, Void> worker = new SwingWorker<>() {
            protected List<Pegawai> doInBackground() throws Exception {
                long start = System.nanoTime();
                List<Pegawai> list = dao.selectAll(metode);
                long durasi = System.nanoTime() - start;
                log("SELECT", metode, durasi);
                return list;
            }
            protected void done() {
                try {
                    List<Pegawai> list = get();
                    tableModel.setRowCount(0);
                    NumberFormat nf = NumberFormat.getNumberInstance(new Locale("id", "ID"));
                    for (Pegawai p : list) {
                        tableModel.addRow(new Object[]{
                            p.getId(),
                            p.getNama(),
                            p.getJabatan(),
                            "Rp " + nf.format(p.getGaji()),
                            p.getDepartemen()
                        });
                    }
                    logInfo("Tabel diperbarui. Total " + list.size() + " data.");
                } catch (Exception ex) {
                    logError("SELECT", ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    // =========================================================
    // HELPER FORM
    // =========================================================
    private void isiFormDariTabel() {
        int baris = tabelPegawai.getSelectedRow();
        if (baris < 0) return;
        txtId.setText(tableModel.getValueAt(baris, 0).toString());
        txtNama.setText(tableModel.getValueAt(baris, 1).toString());
        txtJabatan.setText(tableModel.getValueAt(baris, 2).toString());
        // ambil nilai gaji — strip "Rp " dan titik pemisah ribuan
        String gajiStr = tableModel.getValueAt(baris, 3).toString()
            .replace("Rp ", "").replaceAll("[^0-9]", "");
        txtGaji.setText(gajiStr);
        txtDepartemen.setText(tableModel.getValueAt(baris, 4).toString());
    }

    private Pegawai ambilDataForm() {
        return new Pegawai(
            txtNama.getText().trim(),
            txtJabatan.getText().trim(),
            Double.parseDouble(txtGaji.getText().trim()),
            txtDepartemen.getText().trim()
        );
    }

    private boolean validasiForm() {
        if (txtNama.getText().trim().isEmpty() ||
            txtJabatan.getText().trim().isEmpty() ||
            txtGaji.getText().trim().isEmpty() ||
            txtDepartemen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtGaji.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gaji harus berupa angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtJabatan.setText("");
        txtGaji.setText("");
        txtDepartemen.setText("");
        tabelPegawai.clearSelection();
    }

    // =========================================================
    // LOG
    // =========================================================
    private void log(String operasi, String metode, long nanoseconds) {
        double ms = nanoseconds / 1_000_000.0;
        String msg = String.format(
            "[%s] %-22s | Metode: %-20s | Waktu: %.3f ms%n",
            java.time.LocalTime.now().toString().substring(0, 12),
            operasi, metode, ms
        );
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
        // Cetak juga ke console Eclipse
        System.out.print(msg);
    }

    private void logInfo(String pesan) {
        String msg = "[INFO] " + pesan + "\n";
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
        System.out.print(msg);
    }

    private void logError(String operasi, String pesan) {
        String msg = "[ERROR] " + operasi + " - " + pesan + "\n";
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
        System.err.print(msg);
        JOptionPane.showMessageDialog(this, pesan, "Error " + operasi, JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================
    // MAIN
    // =========================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}
