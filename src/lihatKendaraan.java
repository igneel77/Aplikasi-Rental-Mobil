import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class lihatKendaraan {
    public lihatKendaraan() {
        JFrame dataKendaraanFrame = new JFrame("Data Kendaraan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1050, 350);

        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Merek");
        model.addColumn("Model");
        model.addColumn("Tahun");
        model.addColumn("Plat");
        model.addColumn("Warna");
        model.addColumn("Harga Sewa");
        model.addColumn("Status Ketersediaan");

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 950, 200);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(205, 250, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM kendaraan";
                        ResultSet rs = stmt.executeQuery(selectQuery);
                        model.setRowCount(0);

                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getInt("id"),
                                    rs.getString("merek"),
                                    rs.getString("model"),
                                    rs.getInt("tahun"),
                                    rs.getString("plat"),
                                    rs.getString("warna"),
                                    rs.getDouble("harga_sewa"),
                                    rs.getString("status_ketersediaan")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataKendaraanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        JTextField idKendaraanField = new JTextField();
        idKendaraanField.setBounds(295, 250, 100, 30);

        JButton deleteButton = new JButton("Hapus Kendaraan");
        deleteButton.setBounds(405, 250, 150, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idKendaraan = idKendaraanField.getText();
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String deleteQuery = "DELETE FROM kendaraan WHERE id = " + idKendaraan;
                        int rowsAffected = stmt.executeUpdate(deleteQuery);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(dataKendaraanFrame, "Kendaraan dengan ID " + idKendaraan + " berhasil dihapus.");
                            idKendaraanField.setText("");
                            refreshButton.doClick();
                        } else {
                            JOptionPane.showMessageDialog(dataKendaraanFrame, "Kendaraan dengan ID " + idKendaraan + " tidak ditemukan.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataKendaraanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        JButton editButton = new JButton("Edit Kendaraan");
        editButton.setBounds(565, 250, 150, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDataKendaraan();
            }
        });

        dataKendaraanFrame.add(scrollPane);
        dataKendaraanFrame.add(refreshButton);
        dataKendaraanFrame.add(idKendaraanField);
        dataKendaraanFrame.add(deleteButton);
        dataKendaraanFrame.add(editButton);
        dataKendaraanFrame.add(backgroundLabel);

        dataKendaraanFrame.setSize(1020, 350);
        dataKendaraanFrame.setLayout(null);
        dataKendaraanFrame.setVisible(true);
        dataKendaraanFrame.setLocationRelativeTo(null);
    }

    public static void editDataKendaraan() {
        JFrame editKendaraanFrame = new JFrame("Edit Data Kendaraan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        JLabel labelIDKendaraan, labelMerek, labelModel, labelTahun, labelPlat, labelWarna, labelHargaSewa, labelStatus;
        JTextField idKendaraanField, merekField, modelField, tahunField, platField, warnaField, hargaSewaField;
        JComboBox<String> statusComboBox;
        JButton simpanButton;

        labelIDKendaraan = new JLabel("ID Kendaraan:");
        labelIDKendaraan.setBounds(30, 30, 120, 30);
        labelIDKendaraan.setForeground(Color.WHITE);

        labelMerek = new JLabel("Merek:");
        labelMerek.setBounds(30, 70, 100, 30);
        labelMerek.setForeground(Color.WHITE);

        labelModel = new JLabel("Model:");
        labelModel.setBounds(30, 110, 100, 30);
        labelModel.setForeground(Color.WHITE);

        labelTahun = new JLabel("Tahun:");
        labelTahun.setBounds(30, 150, 100, 30);
        labelTahun.setForeground(Color.WHITE);

        labelPlat = new JLabel("Plat:");
        labelPlat.setBounds(30, 190, 100, 30);
        labelPlat.setForeground(Color.WHITE);

        labelWarna = new JLabel("Warna:");
        labelWarna.setBounds(30, 230, 100, 30);
        labelWarna.setForeground(Color.WHITE);

        labelHargaSewa = new JLabel("Harga Sewa:");
        labelHargaSewa.setBounds(30, 270, 100, 30);
        labelHargaSewa.setForeground(Color.WHITE);

        labelStatus = new JLabel("Status Ketersediaan:");
        labelStatus.setBounds(30, 310, 150, 30);
        labelStatus.setForeground(Color.WHITE);

        idKendaraanField = new JTextField();
        idKendaraanField.setBounds(180, 30, 200, 30);
        idKendaraanField.setEditable(false);

        merekField = new JTextField();
        merekField.setBounds(180, 70, 200, 30);

        modelField = new JTextField();
        modelField.setBounds(180, 110, 200, 30);

        tahunField = new JTextField();
        tahunField.setBounds(180, 150, 200, 30);

        platField = new JTextField();
        platField.setBounds(180, 190, 200, 30);

        warnaField = new JTextField();
        warnaField.setBounds(180, 230, 200, 30);

        hargaSewaField = new JTextField();
        hargaSewaField.setBounds(180, 270, 200, 30);

        String[] statusOptions = { "Tersedia", "Tidak Tersedia" };
        statusComboBox = new JComboBox<>(statusOptions);
        statusComboBox.setBounds(180, 310, 200, 30);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(180, 350, 100, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int idKendaraan = Integer.parseInt(idKendaraanField.getText());
                String merek = merekField.getText();
                String model = modelField.getText();
                int tahun = Integer.parseInt(tahunField.getText());
                String plat = platField.getText();
                String warna = warnaField.getText();
                double hargaSewa = Double.parseDouble(hargaSewaField.getText());
                String status = (String) statusComboBox.getSelectedItem();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String updateQuery = "UPDATE kendaraan SET merek=?, model=?, tahun=?, plat=?, warna=?, harga_sewa=?, status_ketersediaan=? WHERE id=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, merek);
                        preparedStatement.setString(2, model);
                        preparedStatement.setInt(3, tahun);
                        preparedStatement.setString(4, plat);
                        preparedStatement.setString(5, warna);
                        preparedStatement.setDouble(6, hargaSewa);
                        preparedStatement.setString(7, status);
                        preparedStatement.setInt(8, idKendaraan);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editKendaraanFrame, "Data Kendaraan berhasil diperbarui.");
                            editKendaraanFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editKendaraanFrame, "Gagal memperbarui data Kendaraan.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editKendaraanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editKendaraanFrame.add(labelIDKendaraan);
        editKendaraanFrame.add(idKendaraanField);
        editKendaraanFrame.add(labelMerek);
        editKendaraanFrame.add(merekField);
        editKendaraanFrame.add(labelModel);
        editKendaraanFrame.add(modelField);
        editKendaraanFrame.add(labelTahun);
        editKendaraanFrame.add(tahunField);
        editKendaraanFrame.add(labelPlat);
        editKendaraanFrame.add(platField);
        editKendaraanFrame.add(labelWarna);
        editKendaraanFrame.add(warnaField);
        editKendaraanFrame.add(labelHargaSewa);
        editKendaraanFrame.add(hargaSewaField);
        editKendaraanFrame.add(labelStatus);
        editKendaraanFrame.add(statusComboBox);
        editKendaraanFrame.add(simpanButton);
        editKendaraanFrame.add(backgroundLabel);

        editKendaraanFrame.setSize(425, 450);
        editKendaraanFrame.setLayout(null);
        backgroundLabel.setBounds(0, 0, 500, 450);
        editKendaraanFrame.setVisible(true);
        editKendaraanFrame.setLocationRelativeTo(null);
    }
}
