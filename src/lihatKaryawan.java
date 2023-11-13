import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class lihatKaryawan {
    public lihatKaryawan() {
        JFrame dataKaryawanFrame = new JFrame("Data Karyawan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 700, 350);

        // Tabel untuk menampilkan data karyawan
        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("ID Karyawan");
        model.addColumn("Nama");
        model.addColumn("Jabatan");
        model.addColumn("Telepon");
        model.addColumn("Email");

        // Scroll pane agar bisa di-scroll jika data banyak
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 600, 200);

        // Tombol Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 250, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil data karyawan terbaru dan menampilkannya di tabel
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM karyawan";
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        // Clear existing rows in the table
                        model.setRowCount(0);

                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getString("id"),
                                    rs.getString("nama_karyawan"),
                                    rs.getString("jabatan"),
                                    rs.getString("telepon_karyawan"),
                                    rs.getString("email_karyawan")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataKaryawanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Hapus Karyawan berdasarkan ID
        JTextField idKaryawanField = new JTextField();
        idKaryawanField.setBounds(120, 250, 100, 30);
        JButton deleteButton = new JButton("Hapus Karyawan");
        deleteButton.setBounds(230, 250, 150, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil ID Karyawan yang diinputkan
                String idKaryawan = idKaryawanField.getText();

                // Hapus karyawan berdasarkan ID Karyawan
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String deleteQuery = "DELETE FROM karyawan WHERE id = '" + idKaryawan + "'";
                        int rowsAffected = stmt.executeUpdate(deleteQuery);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(dataKaryawanFrame, "Karyawan dengan ID " + idKaryawan + " berhasil dihapus.");
                            idKaryawanField.setText(""); // Reset field setelah penghapusan
                            refreshButton.doClick(); // Refresh tabel
                        } else {
                            JOptionPane.showMessageDialog(dataKaryawanFrame, "Karyawan dengan ID " + idKaryawan + " tidak ditemukan.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataKaryawanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Edit Karyawan
        JButton editButton = new JButton("Edit Karyawan");
        editButton.setBounds(390, 250, 180, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDataKaryawan();
            }
        });

        dataKaryawanFrame.add(scrollPane);
        dataKaryawanFrame.add(refreshButton);
        dataKaryawanFrame.add(idKaryawanField);
        dataKaryawanFrame.add(deleteButton);
        dataKaryawanFrame.add(editButton);
        dataKaryawanFrame.add(backgroundLabel);

        dataKaryawanFrame.setSize(700, 350);
        dataKaryawanFrame.setLayout(null);
        dataKaryawanFrame.setVisible(true);
        dataKaryawanFrame.setLocationRelativeTo(null);
    }

    public static void editDataKaryawan() {
        JFrame editKaryawanFrame = new JFrame("Edit Data Karyawan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        JLabel labelIDKaryawan, labelNama, labelJabatan, labelTelepon, labelEmail;
        JTextField idKaryawanField, namaField, jabatanField, teleponField, emailField;
        JButton simpanButton;

        labelIDKaryawan = new JLabel("ID Karyawan:");
        labelIDKaryawan.setBounds(30, 30, 100, 30);
        labelIDKaryawan.setForeground(Color.WHITE);

        labelNama = new JLabel("Nama:");
        labelNama.setBounds(30, 70, 100, 30);
        labelNama.setForeground(Color.WHITE);

        labelJabatan = new JLabel("Jabatan:");
        labelJabatan.setBounds(30, 110, 100, 30);
        labelJabatan.setForeground(Color.WHITE);

        labelTelepon = new JLabel("Telepon:");
        labelTelepon.setBounds(30, 150, 100, 30);
        labelTelepon.setForeground(Color.WHITE);

        labelEmail = new JLabel("Email:");
        labelEmail.setBounds(30, 190, 100, 30);
        labelEmail.setForeground(Color.WHITE);

        idKaryawanField = new JTextField();
        idKaryawanField.setBounds(150, 30, 200, 30);

        namaField = new JTextField();
        namaField.setBounds(150, 70, 200, 30);

        jabatanField = new JTextField();
        jabatanField.setBounds(150, 110, 200, 30);

        teleponField = new JTextField();
        teleponField.setBounds(150, 150, 200, 30);

        emailField = new JTextField();
        emailField.setBounds(150, 190, 200, 30);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(150, 230, 80, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idKaryawan = idKaryawanField.getText();
                String nama = namaField.getText();
                String jabatan = jabatanField.getText();
                String telepon = teleponField.getText();
                String email = emailField.getText();

                // Simpan perubahan data karyawan ke database
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String updateQuery = "UPDATE karyawan SET nama_karyawan=?, jabatan=?, telepon_karyawan=?, email_karyawan=? WHERE id=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, nama);
                        preparedStatement.setString(2, jabatan);
                        preparedStatement.setString(3, telepon);
                        preparedStatement.setString(4, email);
                        preparedStatement.setString(5, idKaryawan);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editKaryawanFrame, "Data Karyawan berhasil diperbarui.");
                            editKaryawanFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editKaryawanFrame, "Gagal memperbarui data Karyawan.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editKaryawanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editKaryawanFrame.add(labelIDKaryawan);
        editKaryawanFrame.add(labelNama);
        editKaryawanFrame.add(labelJabatan);
        editKaryawanFrame.add(labelTelepon);
        editKaryawanFrame.add(labelEmail);
        editKaryawanFrame.add(idKaryawanField);
        editKaryawanFrame.add(namaField);
        editKaryawanFrame.add(jabatanField);
        editKaryawanFrame.add(teleponField);
        editKaryawanFrame.add(emailField);
        editKaryawanFrame.add(simpanButton);
        editKaryawanFrame.add(backgroundLabel);

        editKaryawanFrame.setSize(400, 300);
        editKaryawanFrame.setLayout(null);
        backgroundLabel.setBounds(0, 0, 400, 300);
        editKaryawanFrame.setVisible(true);
        editKaryawanFrame.setLocationRelativeTo(null);
    }
}
