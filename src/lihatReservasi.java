import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class lihatReservasi {
    public lihatReservasi() {
        JFrame lihatReservasiFrame = new JFrame("Lihat Reservasi");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1000, 410);

        // Tabel untuk menampilkan data reservasi
        DefaultTableModel model = new DefaultTableModel();
        JTable reservasiTable = new JTable(model);
        model.addColumn("ID Reservasi");
        model.addColumn("ID Pelanggan");
        model.addColumn("ID Kendaraan");
        model.addColumn("Tanggal Reservasi");
        model.addColumn("Harga Total");
        model.addColumn("Status Pembayaran");
        model.addColumn("Status Pengambilan");

        // Scroll pane agar dapat di-scroll jika ukuran tabel melebihi tampilan
        JScrollPane scrollPane = new JScrollPane(reservasiTable);
        scrollPane.setBounds(30, 30, 850, 250);

        // Tombol Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 290, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil data reservasi terbaru dan menampilkannya di tabel
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM reservasi";
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        // Membersihkan baris yang ada di dalam tabel
                        model.setRowCount(0);

                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getInt("id"),
                                    rs.getInt("id_pelanggan"),
                                    rs.getInt("id_kendaraan"),
                                    rs.getString("tanggal_pemesanan"),
                                    rs.getDouble("harga_total"),
                                    rs.getString("status_pembayaran"),
                                    rs.getString("status_pengambilan")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(lihatReservasiFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Hapus Reservasi berdasarkan ID
        JTextField idReservasiField = new JTextField();
        idReservasiField.setBounds(120, 290, 100, 30);
        JButton deleteButton = new JButton("Hapus Reservasi");
        deleteButton.setBounds(230, 290, 140, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil ID Reservasi yang diinputkan
                String idReservasi = idReservasiField.getText();

                // Hapus reservasi berdasarkan ID Reservasi
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String deleteQuery = "DELETE FROM reservasi WHERE id = " + idReservasi;
                        int rowsAffected = stmt.executeUpdate(deleteQuery);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(lihatReservasiFrame, "Reservasi dengan ID " + idReservasi + " berhasil dihapus.");
                            idReservasiField.setText(""); // Reset field setelah penghapusan
                            refreshButton.doClick(); // Refresh tabel
                        } else {
                            JOptionPane.showMessageDialog(lihatReservasiFrame, "Reservasi dengan ID " + idReservasi + " tidak ditemukan.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(lihatReservasiFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Edit Reservasi
        JButton editButton = new JButton("Edit Reservasi");
        editButton.setBounds(380, 290, 140, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDataReservasi();
            }
        });

        lihatReservasiFrame.add(scrollPane);
        lihatReservasiFrame.add(refreshButton);
        lihatReservasiFrame.add(idReservasiField);
        lihatReservasiFrame.add(deleteButton);
        lihatReservasiFrame.add(editButton);
        lihatReservasiFrame.add(backgroundLabel);

        lihatReservasiFrame.setSize(925, 410);
        lihatReservasiFrame.setLayout(null);
        lihatReservasiFrame.setVisible(true);
        lihatReservasiFrame.setLocationRelativeTo(null);
    }


    public static void editDataReservasi() {
        JFrame editReservasiFrame = new JFrame("Edit Data Reservasi");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 300);

        JLabel labelIDReservasi, labelStatusPembayaran, labelStatusPengambilan;
        JTextField idReservasiField;
        JComboBox<String> statusPembayaranComboBox, statusPengambilanComboBox;
        JButton simpanButton;

        labelIDReservasi = new JLabel("ID Reservasi:");
        labelIDReservasi.setBounds(30, 30, 100, 30);
        labelIDReservasi.setForeground(Color.WHITE);

        idReservasiField = new JTextField();
        idReservasiField.setBounds(150, 30, 200, 30);

        labelStatusPembayaran = new JLabel("Status Pembayaran:");
        labelStatusPembayaran.setBounds(30, 70, 120, 30);
        labelStatusPembayaran.setForeground(Color.WHITE);

        // Opsi status pembayaran
        String[] statusPembayaranOptions = {"Belum Lunas", "Lunas"};
        statusPembayaranComboBox = new JComboBox<>(statusPembayaranOptions);
        statusPembayaranComboBox.setBounds(150, 70, 200, 30);

        labelStatusPengambilan = new JLabel("Status Pengambilan:");
        labelStatusPengambilan.setBounds(30, 110, 120, 30);
        labelStatusPengambilan.setForeground(Color.WHITE);

        // Opsi status pengambilan
        String[] statusPengambilanOptions = {"Belum Diambil", "Diambil"};
        statusPengambilanComboBox = new JComboBox<>(statusPengambilanOptions);
        statusPengambilanComboBox.setBounds(150, 110, 200, 30);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(150, 150, 80, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idReservasi = idReservasiField.getText();
                String statusPembayaran = statusPembayaranComboBox.getSelectedItem().toString();
                String statusPengambilan = statusPengambilanComboBox.getSelectedItem().toString();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        // Update data reservasi berdasarkan ID Reservasi
                        String updateQuery = "UPDATE reservasi SET status_pembayaran=?, status_pengambilan=? WHERE id=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, statusPembayaran);
                        preparedStatement.setString(2, statusPengambilan);
                        preparedStatement.setInt(3, Integer.parseInt(idReservasi));

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editReservasiFrame, "Data Reservasi berhasil diperbarui.");
                            editReservasiFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editReservasiFrame, "Gagal memperbarui data Reservasi.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editReservasiFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editReservasiFrame.add(labelIDReservasi);
        editReservasiFrame.add(idReservasiField);
        editReservasiFrame.add(labelStatusPembayaran);
        editReservasiFrame.add(statusPembayaranComboBox);
        editReservasiFrame.add(labelStatusPengambilan);
        editReservasiFrame.add(statusPengambilanComboBox);
        editReservasiFrame.add(simpanButton);
        editReservasiFrame.add(backgroundLabel);

        editReservasiFrame.setSize(400, 300);
        editReservasiFrame.setLayout(null);
        backgroundLabel.setBounds(0, 0, 400, 300);
        editReservasiFrame.setVisible(true);
        editReservasiFrame.setLocationRelativeTo(null);
    }
}
