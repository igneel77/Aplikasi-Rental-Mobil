import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class transaksi {
    public transaksi() {
        JFrame transaksiFrame = new JFrame("Transaksi Pembayaran");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 250);

        JLabel labelIdReservasi, labelHargaTotal, labelMetodePembayaran;
        JTextField idReservasiField;
        JComboBox<String> metodePembayaranComboBox;
        JButton bayarButton;

        labelIdReservasi = new JLabel("ID Reservasi:");
        labelIdReservasi.setBounds(30, 30, 100, 30);
        labelIdReservasi.setForeground(Color.WHITE);

        idReservasiField = new JTextField();
        idReservasiField.setBounds(165, 30, 200, 30);

        labelMetodePembayaran = new JLabel("Metode Pembayaran:");
        labelMetodePembayaran.setBounds(30, 70, 120, 30);
        labelMetodePembayaran.setForeground(Color.WHITE);

        // Opsi metode pembayaran
        String[] metodePembayaranOptions = {"Kredit", "Debit", "Transfer", "Tunai"};
        metodePembayaranComboBox = new JComboBox<>(metodePembayaranOptions);
        metodePembayaranComboBox.setBounds(165, 70, 200, 30);

        bayarButton = new JButton("Bayar");
        bayarButton.setBounds(150, 150, 80, 30);
        bayarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idReservasi = idReservasiField.getText();

                // Validasi input
                if (idReservasi.isEmpty()) {
                    JOptionPane.showMessageDialog(transaksiFrame, "Harap isi ID Reservasi.");
                } else {
                    Connection connection = Koneksi.connect();
                    if (connection != null) {
                        try {
                            // Update status pembayaran dan status pengambilan
                            String updateReservasiQuery = "UPDATE reservasi SET status_pembayaran='Lunas', status_pengambilan='Diambil' WHERE id=?";
                            PreparedStatement updateReservasiStatement = connection.prepareStatement(updateReservasiQuery);
                            updateReservasiStatement.setInt(1, Integer.parseInt(idReservasi));

                            int rowsAffectedReservasi = updateReservasiStatement.executeUpdate();
                            updateReservasiStatement.close();

                            if (rowsAffectedReservasi > 0) {
                                // Mengambil harga total dari tabel reservasi
                                String selectHargaTotalQuery = "SELECT harga_total FROM reservasi WHERE id=?";
                                PreparedStatement selectHargaTotalStatement = connection.prepareStatement(selectHargaTotalQuery);
                                selectHargaTotalStatement.setInt(1, Integer.parseInt(idReservasi));
                                ResultSet hargaTotalResult = selectHargaTotalStatement.executeQuery();

                                if (hargaTotalResult.next()) {
                                    double hargaTotal = hargaTotalResult.getDouble("harga_total");

                                    // Menyimpan informasi pembayaran ke dalam tabel "transaksi"
                                    String insertTransaksiQuery = "INSERT INTO transaksi (id_reservasi, metode_pembayaran, total_bayar) VALUES (?, ?, ?)";
                                    PreparedStatement insertTransaksiStatement = connection.prepareStatement(insertTransaksiQuery);
                                    insertTransaksiStatement.setInt(1, Integer.parseInt(idReservasi));
                                    insertTransaksiStatement.setString(2, metodePembayaranComboBox.getSelectedItem().toString());
                                    insertTransaksiStatement.setDouble(3, hargaTotal);

                                    int rowsInsertedTransaksi = insertTransaksiStatement.executeUpdate();
                                    insertTransaksiStatement.close();

                                    if (rowsInsertedTransaksi > 0) {
                                        JOptionPane.showMessageDialog(transaksiFrame, "Pembayaran berhasil dilakukan.");
                                    } else {
                                        JOptionPane.showMessageDialog(transaksiFrame, "Gagal mencatat informasi transaksi.");
                                    }
                                }
                                selectHargaTotalStatement.close();
                            } else {
                                JOptionPane.showMessageDialog(transaksiFrame, "Gagal melakukan pembayaran.");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(transaksiFrame, "Terjadi kesalahan: " + ex.getMessage());
                        }
                    }
                }
            }
        });

        transaksiFrame.add(labelIdReservasi);
        transaksiFrame.add(idReservasiField);
        transaksiFrame.add(labelMetodePembayaran);
        transaksiFrame.add(metodePembayaranComboBox);
        transaksiFrame.add(bayarButton);
        transaksiFrame.add(backgroundLabel);

        transaksiFrame.setSize(400, 250);
        transaksiFrame.setLayout(null);
        backgroundLabel.setBounds(0, 0, 400, 250);
        transaksiFrame.setVisible(true);
        transaksiFrame.setLocationRelativeTo(null);


    }
}
