import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainMenu {
    public mainMenu() {
        JFrame mainFrame = new JFrame("Menu Utama Rental Mobil");
        ImageIcon backgroundImage = new ImageIcon("src/img/ca.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 755, 260);

        JButton inputKaryawanButton = new JButton("Input Karyawan");
        inputKaryawanButton.setBounds(30, 15, 150, 30);
        inputKaryawanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new inputKaryawan();
            }
        });

        JButton inputPelangganButton = new JButton("Input Pelanggan");
        inputPelangganButton.setBounds(30, 55, 150, 30);
        inputPelangganButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new inputPelanggan();
            }
        });

        JButton inputKendaraanButton = new JButton("Input Kendaraan");
        inputKendaraanButton.setBounds(30, 95, 150, 30);
        inputKendaraanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new inputKendaraan();
            }
        });

        JButton lihatKaryawanButton = new JButton("Lihat Karyawan");
        lihatKaryawanButton.setBounds(250, 15, 150, 30);
        lihatKaryawanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new lihatKaryawan();
            }
        });

        JButton lihatPelangganButton = new JButton("Lihat Pelanggan");
        lihatPelangganButton.setBounds(250, 55, 150, 30);
        lihatPelangganButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new lihatPelanggan();
            }
        });

        JButton lihatKendaraanButton = new JButton("Lihat Kendaraan");
        lihatKendaraanButton.setBounds(250, 95, 150, 30);
        lihatKendaraanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new lihatKendaraan();
            }
        });

        JButton reservasiButton = new JButton("Reservasi");
        reservasiButton.setBounds(30, 135, 150, 30);
        reservasiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new reservasi();
            }
        });

        JButton transaksiButton = new JButton("Transaksi");
        transaksiButton.setBounds(30, 175, 150, 30);
        transaksiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new transaksi();
            }
        });

        JButton lihatReservasiButton = new JButton("Lihat Reservasi");
        lihatReservasiButton.setBounds(250, 135, 150, 30);
        lihatReservasiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new lihatReservasi();
            }
        });

        JButton lihatTransaksiButton = new JButton("Lihat Transaksi");
        lihatTransaksiButton.setBounds(250, 175, 150, 30);
        lihatTransaksiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new lihatTransaksi();
            }
        });

        mainFrame.add(lihatReservasiButton);
        mainFrame.add(lihatTransaksiButton);
        mainFrame.add(inputPelangganButton);
        mainFrame.add(inputKendaraanButton);
        mainFrame.add(lihatPelangganButton);
        mainFrame.add(lihatKendaraanButton);
        mainFrame.add(reservasiButton);
        mainFrame.add(transaksiButton);
        mainFrame.add(inputKaryawanButton);
        mainFrame.add(lihatKaryawanButton);
        mainFrame.add(backgroundLabel);

        mainFrame.setSize(700, 260);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
