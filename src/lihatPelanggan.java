import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class lihatPelanggan {
    public lihatPelanggan() {
        JFrame dataPelangganFrame = new JFrame("Data Pelanggan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 700, 350);

        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Telepon");
        model.addColumn("No. KTP");

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 600, 200);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 250, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM pelanggan";
                        ResultSet rs = stmt.executeQuery(selectQuery);
                        model.setRowCount(0); // Clear existing rows in the table

                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getString("id"),
                                    rs.getString("nama"),
                                    rs.getString("alamat"),
                                    rs.getString("telepon"),
                                    rs.getString("ktp")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataPelangganFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        JTextField idPelangganField = new JTextField();
        idPelangganField.setBounds(120, 250, 100, 30);

        JButton deleteButton = new JButton("Hapus Pelanggan");
        deleteButton.setBounds(230, 250, 150, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idPelanggan = idPelangganField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String deleteQuery = "DELETE FROM pelanggan WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                        preparedStatement.setString(1, idPelanggan);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(dataPelangganFrame, "Pelanggan dengan ID " + idPelanggan + " berhasil dihapus.");
                            idPelangganField.setText(""); // Reset field setelah penghapusan
                            refreshButton.doClick(); // Refresh tabel
                        } else {
                            JOptionPane.showMessageDialog(dataPelangganFrame, "Pelanggan dengan ID " + idPelanggan + " tidak ditemukan.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataPelangganFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        JButton editButton = new JButton("Edit Pelanggan");
        editButton.setBounds(390, 250, 120, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDataPelanggan();
            }
        });

        dataPelangganFrame.add(scrollPane);
        dataPelangganFrame.add(refreshButton);
        dataPelangganFrame.add(idPelangganField);
        dataPelangganFrame.add(deleteButton);
        dataPelangganFrame.add(editButton);
        dataPelangganFrame.add(backgroundLabel);

        dataPelangganFrame.setSize(700, 350);
        dataPelangganFrame.setLayout(null);
        dataPelangganFrame.setVisible(true);
        dataPelangganFrame.setLocationRelativeTo(null);
    }

    public static void editDataPelanggan() {
        JFrame editPelangganFrame = new JFrame("Edit Data Pelanggan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        JLabel labelIDPelanggan, labelNama, labelAlamat, labelTelepon, labelKTP;
        JTextField idPelangganField, namaField, alamatField, teleponField, ktpField;
        JButton simpanButton;

        labelIDPelanggan = new JLabel("ID Pelanggan:");
        labelIDPelanggan.setBounds(30, 30, 100, 30);
        labelIDPelanggan.setForeground(Color.WHITE);

        labelNama = new JLabel("Nama:");
        labelNama.setBounds(30, 70, 100, 30);
        labelNama.setForeground(Color.WHITE);

        labelAlamat = new JLabel("Alamat:");
        labelAlamat.setBounds(30, 110, 100, 30);
        labelAlamat.setForeground(Color.WHITE);

        labelTelepon = new JLabel("Telepon:");
        labelTelepon.setBounds(30, 150, 100, 30);
        labelTelepon.setForeground(Color.WHITE);

        labelKTP = new JLabel("No. KTP:");
        labelKTP.setBounds(30, 190, 100, 30);
        labelKTP.setForeground(Color.WHITE);

        idPelangganField = new JTextField();
        idPelangganField.setBounds(150, 30, 200, 30);

        namaField = new JTextField();
        namaField.setBounds(150, 70, 200, 30);

        alamatField = new JTextField();
        alamatField.setBounds(150, 110, 200, 30);

        teleponField = new JTextField();
        teleponField.setBounds(150, 150, 200, 30);

        ktpField = new JTextField();
        ktpField.setBounds(150, 190, 200, 30);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(150, 230, 80, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idPelanggan = idPelangganField.getText();
                String nama = namaField.getText();
                String alamat = alamatField.getText();
                String telepon = teleponField.getText();
                String ktp = ktpField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String updateQuery = "UPDATE pelanggan SET nama=?, alamat=?, telepon=?, ktp=? WHERE id=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, nama);
                        preparedStatement.setString(2, alamat);
                        preparedStatement.setString(3, telepon);
                        preparedStatement.setString(4, ktp);
                        preparedStatement.setString(5, idPelanggan);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editPelangganFrame, "Data Pelanggan berhasil diperbarui.");
                            editPelangganFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editPelangganFrame, "Gagal memperbarui data Pelanggan.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editPelangganFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editPelangganFrame.add(labelIDPelanggan);
        editPelangganFrame.add(idPelangganField);
        editPelangganFrame.add(labelNama);
        editPelangganFrame.add(namaField);
        editPelangganFrame.add(labelAlamat);
        editPelangganFrame.add(alamatField);
        editPelangganFrame.add(labelTelepon);
        editPelangganFrame.add(teleponField);
        editPelangganFrame.add(labelKTP);
        editPelangganFrame.add(ktpField);
        editPelangganFrame.add(simpanButton);
        editPelangganFrame.add(backgroundLabel);

        editPelangganFrame.setSize(400, 300);
        editPelangganFrame.setLayout(null);
        backgroundLabel.setBounds(0, 0, 400, 300);
        editPelangganFrame.setVisible(true);
        editPelangganFrame.setLocationRelativeTo(null);
    }
}
