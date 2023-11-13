import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class inputKendaraan {
    public inputKendaraan() {
        JFrame inputKendaraanFrame = new JFrame("Input Kendaraan");
        ImageIcon backgroundImage = new ImageIcon("src/img/birubgpolos.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 400);

        JLabel labelMerek, labelModel, labelTahun, labelPlat, labelWarna, labelHargaSewa, labelStatus;
        JTextField merekField, modelField, tahunField, platField, warnaField, hargaSewaField;
        JComboBox<String> statusComboBox;
        JButton submitButton;

        labelMerek = new JLabel("Merek:");
        labelMerek.setBounds(30, 30, 100, 30);
        labelMerek.setForeground(Color.WHITE);

        labelModel = new JLabel("Model:");
        labelModel.setBounds(30, 70, 100, 30);
        labelModel.setForeground(Color.WHITE);

        labelTahun = new JLabel("Tahun:");
        labelTahun.setBounds(30, 110, 100, 30);
        labelTahun.setForeground(Color.WHITE);

        labelPlat = new JLabel("Plat:");
        labelPlat.setBounds(30, 150, 100, 30);
        labelPlat.setForeground(Color.WHITE);

        labelWarna = new JLabel("Warna:");
        labelWarna.setBounds(30, 190, 100, 30);
        labelWarna.setForeground(Color.WHITE);

        labelHargaSewa = new JLabel("Harga Sewa:");
        labelHargaSewa.setBounds(30, 230, 100, 30);
        labelHargaSewa.setForeground(Color.WHITE);

        labelStatus = new JLabel("Status Ketersediaan:");
        labelStatus.setBounds(30, 270, 150, 30);
        labelStatus.setForeground(Color.WHITE);

        merekField = new JTextField();
        merekField.setBounds(180, 30, 200, 30);

        modelField = new JTextField();
        modelField.setBounds(180, 70, 200, 30);

        tahunField = new JTextField();
        tahunField.setBounds(180, 110, 200, 30);

        platField = new JTextField();
        platField.setBounds(180, 150, 200, 30);

        warnaField = new JTextField();
        warnaField.setBounds(180, 190, 200, 30);

        hargaSewaField = new JTextField();
        hargaSewaField.setBounds(180, 230, 200, 30);

        String[] statusOptions = { "Tersedia", "Tidak Tersedia" };
        statusComboBox = new JComboBox<>(statusOptions);
        statusComboBox.setBounds(180, 270, 200, 30);

        submitButton = new JButton("Submit");
        submitButton.setBounds(180, 310, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                        String insertQuery = "INSERT INTO kendaraan (merek, model, tahun, plat, warna, harga_sewa, status_ketersediaan) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, merek);
                        preparedStatement.setString(2, model);
                        preparedStatement.setInt(3, tahun);
                        preparedStatement.setString(4, plat);
                        preparedStatement.setString(5, warna);
                        preparedStatement.setDouble(6, hargaSewa);
                        preparedStatement.setString(7, status);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(inputKendaraanFrame, "Data Kendaraan berhasil ditambahkan.");
                            inputKendaraanFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(inputKendaraanFrame, "Gagal menambahkan data Kendaraan.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(inputKendaraanFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        inputKendaraanFrame.add(labelMerek);
        inputKendaraanFrame.add(merekField);
        inputKendaraanFrame.add(labelModel);
        inputKendaraanFrame.add(modelField);
        inputKendaraanFrame.add(labelTahun);
        inputKendaraanFrame.add(tahunField);
        inputKendaraanFrame.add(labelPlat);
        inputKendaraanFrame.add(platField);
        inputKendaraanFrame.add(labelWarna);
        inputKendaraanFrame.add(warnaField);
        inputKendaraanFrame.add(labelHargaSewa);
        inputKendaraanFrame.add(hargaSewaField);
        inputKendaraanFrame.add(labelStatus);
        inputKendaraanFrame.add(statusComboBox);
        inputKendaraanFrame.add(submitButton);
        inputKendaraanFrame.add(backgroundLabel);

        inputKendaraanFrame.setSize(420, 400);
        inputKendaraanFrame.setLayout(null);
        inputKendaraanFrame.setVisible(true);
        inputKendaraanFrame.setLocationRelativeTo(null);
    }
}
