import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class inputPelanggan {
    public inputPelanggan() {
        JFrame inputPelangganFrame = new JFrame("Input Pelanggan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg"); // Mengubah gambar latar belakang
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 300);

        JLabel labelNama, labelAlamat, labelTelepon, labelKTP;
        labelNama = new JLabel("Nama:");
        labelNama.setBounds(30, 30, 100, 30);
        labelNama.setForeground(Color.WHITE); // Mengatur warna tulisan menjadi putih

        labelAlamat = new JLabel("Alamat:");
        labelAlamat.setBounds(30, 70, 100, 30);
        labelAlamat.setForeground(Color.WHITE); // Mengatur warna tulisan menjadi putih

        labelTelepon = new JLabel("Telepon:");
        labelTelepon.setBounds(30, 110, 100, 30);
        labelTelepon.setForeground(Color.WHITE); // Mengatur warna tulisan menjadi putih

        labelKTP = new JLabel("No. KTP:");
        labelKTP.setBounds(30, 150, 100, 30);
        labelKTP.setForeground(Color.WHITE); // Mengatur warna tulisan menjadi putih

        JTextField namaField, alamatField, teleponField, ktpField;
        namaField = new JTextField();
        namaField.setBounds(130, 30, 200, 30);

        alamatField = new JTextField();
        alamatField.setBounds(130, 70, 200, 30);

        teleponField = new JTextField();
        teleponField.setBounds(130, 110, 200, 30);

        ktpField = new JTextField();
        ktpField.setBounds(130, 150, 200, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130, 190, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nama = namaField.getText();
                String alamat = alamatField.getText();
                String telepon = teleponField.getText();
                String ktp = ktpField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String insertQuery = "INSERT INTO pelanggan (nama, alamat, telepon, ktp) VALUES (?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, nama);
                        preparedStatement.setString(2, alamat);
                        preparedStatement.setString(3, telepon);
                        preparedStatement.setString(4, ktp);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(inputPelangganFrame, "Data Pelanggan berhasil ditambahkan.");
                            inputPelangganFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(inputPelangganFrame, "Gagal menambahkan data Pelanggan.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(inputPelangganFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        inputPelangganFrame.add(labelNama);
        inputPelangganFrame.add(namaField);
        inputPelangganFrame.add(labelAlamat);
        inputPelangganFrame.add(alamatField);
        inputPelangganFrame.add(labelTelepon);
        inputPelangganFrame.add(teleponField);
        inputPelangganFrame.add(labelKTP);
        inputPelangganFrame.add(ktpField);
        inputPelangganFrame.add(submitButton);
        inputPelangganFrame.add(backgroundLabel);

        inputPelangganFrame.setSize(400, 300);
        inputPelangganFrame.setLayout(null);
        backgroundLabel.setBounds(0, 0, 400, 300);
        inputPelangganFrame.setVisible(true);
        inputPelangganFrame.setLocationRelativeTo(null);
    }
}
