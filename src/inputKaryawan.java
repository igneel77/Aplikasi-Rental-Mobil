import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class inputKaryawan {
    public inputKaryawan() {
        JFrame inputKaryawanFrame = new JFrame("Input Karyawan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 350);

        JLabel labelIdKaryawan, labelNama, labelJabatan, labelTelepon, labelEmail;
        labelIdKaryawan = new JLabel("ID Karyawan:");
        labelIdKaryawan.setBounds(30, 15, 100, 30);
        labelIdKaryawan.setForeground(Color.WHITE);

        labelNama = new JLabel("Nama:");
        labelNama.setBounds(30, 55, 100, 30);
        labelNama.setForeground(Color.WHITE);

        labelJabatan = new JLabel("Jabatan:");
        labelJabatan.setBounds(30, 95, 100, 30);
        labelJabatan.setForeground(Color.WHITE);

        labelTelepon = new JLabel("Telepon:");
        labelTelepon.setBounds(30, 135, 100, 30);
        labelTelepon.setForeground(Color.WHITE);

        labelEmail = new JLabel("Email:");
        labelEmail.setBounds(30, 175, 100, 30);
        labelEmail.setForeground(Color.WHITE);


        JTextField idKaryawanField, namaField, jabatanField, teleponField, emailField;
        idKaryawanField = new JTextField();
        idKaryawanField.setBounds(130, 15, 200, 30);

        namaField = new JTextField();
        namaField.setBounds(130, 55, 200, 30);

        jabatanField = new JTextField();
        jabatanField.setBounds(130, 95, 200, 30);

        teleponField = new JTextField();
        teleponField.setBounds(130, 135, 200, 30);

        emailField = new JTextField();
        emailField.setBounds(130, 175, 200, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130, 215, 100, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idKaryawan = idKaryawanField.getText();
                String nama = namaField.getText();
                String jabatan = jabatanField.getText();
                String telepon = teleponField.getText();
                String email = emailField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String insertQuery = "INSERT INTO karyawan (id, nama_karyawan, jabatan, telepon_karyawan, email_karyawan) VALUES ('"
                                + idKaryawan + "', '" + nama + "', '" + jabatan + "', '" + telepon + "', '" + email + "')";
                        int rowsAffected = stmt.executeUpdate(insertQuery);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(inputKaryawanFrame, "Data Karyawan berhasil ditambahkan.");
                            inputKaryawanFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(inputKaryawanFrame, "Gagal menambahkan data Karyawan.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(inputKaryawanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        inputKaryawanFrame.add(labelIdKaryawan);
        inputKaryawanFrame.add(idKaryawanField);
        inputKaryawanFrame.add(labelNama);
        inputKaryawanFrame.add(namaField);
        inputKaryawanFrame.add(labelJabatan);
        inputKaryawanFrame.add(jabatanField);
        inputKaryawanFrame.add(labelTelepon);
        inputKaryawanFrame.add(teleponField);
        inputKaryawanFrame.add(labelEmail);
        inputKaryawanFrame.add(emailField);
        inputKaryawanFrame.add(submitButton);
        inputKaryawanFrame.add(backgroundLabel);

        inputKaryawanFrame.setSize(400, 300);
        inputKaryawanFrame.setLayout(null);
        inputKaryawanFrame.setVisible(true);
        inputKaryawanFrame.setLocationRelativeTo(null);
    }
}
