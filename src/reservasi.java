import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reservasi {
    public reservasi() {
        JFrame reservasiFrame = new JFrame("Reservasi Kendaraan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 350);

        JLabel labelIdPelanggan, labelIdKendaraan, labelTanggalMulai, labelTanggalSelesai;
        labelIdPelanggan = new JLabel("ID Pelanggan:");
        labelIdPelanggan.setBounds(30, 30, 100, 30);
        labelIdPelanggan.setForeground(Color.WHITE);

        labelIdKendaraan = new JLabel("ID Kendaraan:");
        labelIdKendaraan.setBounds(30, 70, 100, 30);
        labelIdKendaraan.setForeground(Color.WHITE);

        labelTanggalMulai = new JLabel("Tanggal Mulai (YYYY-MM-DD):");
        labelTanggalMulai.setBounds(30, 110, 200, 30);
        labelTanggalMulai.setForeground(Color.WHITE);

        labelTanggalSelesai = new JLabel("Tanggal Selesai (YYYY-MM-DD):");
        labelTanggalSelesai.setBounds(30, 150, 200, 30);
        labelTanggalSelesai.setForeground(Color.WHITE);

        JTextField idPelangganField, idKendaraanField, tanggalMulaiField, tanggalSelesaiField;
        idPelangganField = new JTextField();
        idPelangganField.setBounds(230, 30, 200, 30);

        idKendaraanField = new JTextField();
        idKendaraanField.setBounds(230, 70, 200, 30);

        tanggalMulaiField = new JTextField();
        tanggalMulaiField.setBounds(230, 110, 200, 30);

        tanggalSelesaiField = new JTextField();
        tanggalSelesaiField.setBounds(230, 150, 200, 30);

        JButton submitButton = new JButton("Reservasi");
        submitButton.setBounds(230, 190, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idPelanggan = idPelangganField.getText();
                String idKendaraan = idKendaraanField.getText();
                String tanggalMulai = tanggalMulaiField.getText();
                String tanggalSelesai = tanggalSelesaiField.getText();

                // Validasi input
                if (idPelanggan.isEmpty() || idKendaraan.isEmpty() || tanggalMulai.isEmpty() || tanggalSelesai.isEmpty()) {
                    JOptionPane.showMessageDialog(reservasiFrame, "Harap isi semua kolom yang diperlukan.");
                } else {
                    Connection connection = Koneksi.connect();
                    if (connection != null) {
                        try {
                            // Validasi ketersediaan kendaraan
                            String cekKetersediaanQuery = "SELECT status_ketersediaan FROM kendaraan WHERE id = ?";
                            PreparedStatement cekKetersediaanStatement = connection.prepareStatement(cekKetersediaanQuery);
                            cekKetersediaanStatement.setInt(1, Integer.parseInt(idKendaraan));
                            ResultSet ketersediaanResult = cekKetersediaanStatement.executeQuery();
                            if (ketersediaanResult.next() && ketersediaanResult.getString("status_ketersediaan").equals("Tersedia")) {
                                // Hitung harga total berdasarkan tanggal mulai dan tanggal selesai
                                PreparedStatement hargaStatement = connection.prepareStatement("SELECT harga_sewa FROM kendaraan WHERE id = ?");
                                hargaStatement.setInt(1, Integer.parseInt(idKendaraan));
                                ResultSet hargaResult = hargaStatement.executeQuery();
                                double hargaSewa = 0;
                                if (hargaResult.next()) {
                                    hargaSewa = hargaResult.getDouble("harga_sewa");
                                }

                                // Menghitung durasi sewa dalam hari
                                java.util.Date tanggalMulaiDate = java.sql.Date.valueOf(tanggalMulai);
                                java.util.Date tanggalSelesaiDate = java.sql.Date.valueOf(tanggalSelesai);
                                long durasiSewa = (tanggalSelesaiDate.getTime() - tanggalMulaiDate.getTime()) / (24 * 60 * 60 * 1000);

                                // Menghitung harga total
                                double hargaTotal = hargaSewa * durasiSewa;

                                // Memasukkan data reservasi ke database
                                String reservasiQuery = "INSERT INTO reservasi (id_pelanggan, id_kendaraan, tanggal_mulai, tanggal_selesai, harga_total, status_pembayaran, status_pengambilan) VALUES (?, ?, ?, ?, ?, 'Belum Dibayar', 'Belum Diambil')";
                                PreparedStatement reservasiStatement = connection.prepareStatement(reservasiQuery);
                                reservasiStatement.setInt(1, Integer.parseInt(idPelanggan));
                                reservasiStatement.setInt(2, Integer.parseInt(idKendaraan));
                                reservasiStatement.setDate(3, java.sql.Date.valueOf(tanggalMulai));
                                reservasiStatement.setDate(4, java.sql.Date.valueOf(tanggalSelesai));
                                reservasiStatement.setDouble(5, hargaTotal);

                                int rowsAffected = reservasiStatement.executeUpdate();

                                // Jika berhasil, update status ketersediaan kendaraan
                                if (rowsAffected > 0) {
                                    PreparedStatement updateKetersediaanStatement = connection.prepareStatement("UPDATE kendaraan SET status_ketersediaan = 'Tidak Tersedia' WHERE id = ?");
                                    updateKetersediaanStatement.setInt(1, Integer.parseInt(idKendaraan));
                                    updateKetersediaanStatement.executeUpdate();

                                    JOptionPane.showMessageDialog(reservasiFrame, "Reservasi berhasil! Harga Total: Rp " + hargaTotal);
                                    reservasiFrame.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(reservasiFrame, "Gagal melakukan reservasi.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(reservasiFrame, "Kendaraan tidak tersedia.");
                            }

                            cekKetersediaanStatement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(reservasiFrame, "Terjadi kesalahan: " + ex.getMessage());
                        }
                    }
                }
            }
        });

        reservasiFrame.add(labelIdPelanggan);
        reservasiFrame.add(idPelangganField);
        reservasiFrame.add(labelIdKendaraan);
        reservasiFrame.add(idKendaraanField);
        reservasiFrame.add(labelTanggalMulai);
        reservasiFrame.add(tanggalMulaiField);
        reservasiFrame.add(labelTanggalSelesai);
        reservasiFrame.add(tanggalSelesaiField);
        reservasiFrame.add(submitButton);
        reservasiFrame.add(backgroundLabel);

        reservasiFrame.setSize(470, 300);
        reservasiFrame.setLayout(null);
        reservasiFrame.setVisible(true);
        reservasiFrame.setLocationRelativeTo(null);
    }
}
