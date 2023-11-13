import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class lihatTransaksi {
    public lihatTransaksi() {
        JFrame lihatTransaksiFrame = new JFrame("Lihat Transaksi");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1000, 410);

        // Tabel untuk menampilkan data transaksi
        DefaultTableModel model = new DefaultTableModel();
        JTable transaksiTable = new JTable(model);
        model.addColumn("ID Transaksi");
        model.addColumn("ID Reservasi");
        model.addColumn("Tanggal Transaksi");
        model.addColumn("Metode Pembayaran");
        model.addColumn("Total Harga");

        // Scroll pane agar dapat di-scroll jika ukuran tabel melebihi tampilan
        JScrollPane scrollPane = new JScrollPane(transaksiTable);
        scrollPane.setBounds(30, 30, 850, 250);

        // Tombol Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 290, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil data transaksi terbaru dan menampilkannya di tabel
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM transaksi";
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        // Membersihkan baris yang ada di dalam tabel
                        model.setRowCount(0);

                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getInt("id"),
                                    rs.getInt("id_reservasi"),
                                    rs.getString("tanggal_transaksi"),
                                    rs.getString("metode_pembayaran"),
                                    rs.getDouble("total_bayar")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(lihatTransaksiFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Hapus Transaksi berdasarkan ID
        JTextField idTransaksiField = new JTextField();
        idTransaksiField.setBounds(120, 290, 100, 30);
        JButton deleteButton = new JButton("Hapus Transaksi");
        deleteButton.setBounds(230, 290, 140, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil ID Transaksi yang diinputkan
                String idTransaksi = idTransaksiField.getText();

                // Hapus transaksi berdasarkan ID Transaksi
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String deleteQuery = "DELETE FROM transaksi WHERE id = " + idTransaksi;
                        int rowsAffected = stmt.executeUpdate(deleteQuery);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(lihatTransaksiFrame, "Transaksi dengan ID " + idTransaksi + " berhasil dihapus.");
                            idTransaksiField.setText(""); // Reset field setelah penghapusan
                            refreshButton.doClick(); // Refresh tabel
                        } else {
                            JOptionPane.showMessageDialog(lihatTransaksiFrame, "Transaksi dengan ID " + idTransaksi + " tidak ditemukan.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(lihatTransaksiFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Edit Transaksi
        JButton editButton = new JButton("Edit Transaksi");
        editButton.setBounds(380, 290, 140, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDataTransaksi();
            }
        });

        lihatTransaksiFrame.add(scrollPane);
        lihatTransaksiFrame.add(refreshButton);
        lihatTransaksiFrame.add(idTransaksiField);
        lihatTransaksiFrame.add(deleteButton);
        lihatTransaksiFrame.add(editButton);
        lihatTransaksiFrame.add(backgroundLabel);

        lihatTransaksiFrame.setSize(925, 410);
        lihatTransaksiFrame.setLayout(null);
        lihatTransaksiFrame.setVisible(true);
        lihatTransaksiFrame.setLocationRelativeTo(null);
    }
    public static void editDataTransaksi() {
        JFrame editTransaksiFrame = new JFrame("Edit Data Transaksi");
        ImageIcon backgroundImage = new ImageIcon("src/img/hitam.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 300);

        JLabel labelIDTransaksi, labelMetodePembayaran;
        JTextField idTransaksiField;
        JComboBox<String> metodePembayaranComboBox;
        JButton simpanButton;

        labelIDTransaksi = new JLabel("ID Transaksi:");
        labelIDTransaksi.setBounds(30, 30, 100, 30);
        labelIDTransaksi.setForeground(Color.WHITE);

        idTransaksiField = new JTextField();
        idTransaksiField.setBounds(150, 30, 200, 30);

        labelMetodePembayaran = new JLabel("Metode Pembayaran:");
        labelMetodePembayaran.setBounds(30, 70, 120, 30);
        labelMetodePembayaran.setForeground(Color.WHITE);

        // Opsi metode pembayaran
        String[] metodePembayaranOptions = {"Kredit", "Debit", "Transfer", "Tunai"};
        metodePembayaranComboBox = new JComboBox<>(metodePembayaranOptions);
        metodePembayaranComboBox.setBounds(150, 70, 200, 30);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(150, 150, 80, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idTransaksi = idTransaksiField.getText();
                String metodePembayaran = metodePembayaranComboBox.getSelectedItem().toString();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        // Update data transaksi berdasarkan ID Transaksi
                        String updateQuery = "UPDATE transaksi SET metode_pembayaran=? WHERE id=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, metodePembayaran);
                        preparedStatement.setInt(2, Integer.parseInt(idTransaksi));

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editTransaksiFrame, "Data Transaksi berhasil diperbarui.");
                            editTransaksiFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editTransaksiFrame, "Gagal memperbarui data Transaksi.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editTransaksiFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editTransaksiFrame.add(labelIDTransaksi);
        editTransaksiFrame.add(idTransaksiField);
        editTransaksiFrame.add(labelMetodePembayaran);
        editTransaksiFrame.add(metodePembayaranComboBox);
        editTransaksiFrame.add(simpanButton);
        editTransaksiFrame.add(backgroundLabel);

        editTransaksiFrame.setSize(400, 300);
        editTransaksiFrame.setLayout(null);
        backgroundLabel.setBounds(0, 0, 400, 300);
        editTransaksiFrame.setVisible(true);
        editTransaksiFrame.setLocationRelativeTo(null);
    }
}
