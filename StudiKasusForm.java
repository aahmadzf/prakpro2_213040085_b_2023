/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudiKasusPP;


import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author Ahmad Faturrahman
 */

public class StudiKasusForm extends JFrame{
   private void simpanFile(ArrayList<ArrayList<String>> data) {
        // Menampilkan pesan konfirmasi
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menyimpan file '.txt' ?",
        "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if(konfirmasi == JOptionPane.YES_OPTION) {
            try {
                // Menulis lokasi file
                BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            
                for(ArrayList<String> row : data) {
                    // Memisahkan beberapa data dengan melalui tab
                    for (String s : row) {
                        writer.write(s + "\t");
                    }
                    // Memindahkan data ke baris yang baru
                    writer.newLine();
                }
                writer.close();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan ke file ini");
            }
            catch(IOException e) {
                JOptionPane.showMessageDialog(null, "Data gagal disimpan ke file ini.");
                e.printStackTrace();
            }
        }
    }

    public StudiKasusForm() {
        // Mengatur layout frame menjadi null
        this.setLayout(null);

        // Membuat label 
        JLabel labelNama = new JLabel("Nama:");
        labelNama.setBounds(15, 40, 100, 10);

        JTextField textnama = new JTextField();
        textnama.setBounds(15, 60, 350, 30);

        JLabel labelnomor = new JLabel("Nomor HP:");
        labelnomor.setBounds(15, 100, 100, 10);

        JTextField textnomor = new JTextField();
        textnomor.setBounds(15, 120, 350, 30);

        JLabel labelRadio = new JLabel("Jenis Kelamin:");
        labelRadio.setBounds(15, 160, 350, 10);

        JRadioButton radioButton1 = new JRadioButton("Laki-Laki");
        radioButton1.setBounds(15, 180, 350, 30);

        JRadioButton radioButton2 = new JRadioButton("Perempuan");
        radioButton2.setBounds(15, 210, 350, 30);

        JLabel labelAlamat = new JLabel("Alamat:");
        labelAlamat.setBounds(15, 240, 350, 30);

        JTextArea textalamat = new JTextArea(5, 20);
        textalamat.setBounds(15, 270, 350, 100);

        
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButton1);
        bg.add(radioButton2);

        // Membuat tombol simpan, edit, hapus, dan simpan file
        JButton buttonSimpan = new JButton("Simpan");
        buttonSimpan.setBounds(15, 380, 100, 40);

        JButton buttonEdit = new JButton("Edit");
        buttonEdit.setBounds(120, 380, 100, 40);

        JButton buttonHapus = new JButton("Hapus");
        buttonHapus.setBounds(225, 380, 100, 40);

        JButton buttonSimpanFile = new JButton("Simpan Ke File");
        buttonSimpanFile.setBounds(330, 380, 120, 40);

        // Membuat tabel JTable
        javax.swing.JTable table = new JTable();
        JScrollPane scrollableTable = new JScrollPane(table);
        scrollableTable.setBounds(15, 430, 560, 150);

        // Membuat model tabel
        FormInputKasus tableModel = new FormInputKasus();
        table.setModel(tableModel);

        // Menambahkan ActionListener pada tombol "Simpan"
        buttonSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengambil data dari inputan Nama, Nomor HP, Jenis Kelamin, dan Alamat
                String jenisKelamin = "";
                String nama = textnama.getText();
                String nomorHP = textnomor.getText();
                String alamat = textalamat.getText();

                if(radioButton1.isSelected() && radioButton2.isSelected()) {
                    JOptionPane.showMessageDialog(StudiKasusForm.this, "Hanya satu jenis kelamin dipilih!",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                else if (radioButton1.isSelected()) {
                    jenisKelamin = radioButton1.getText();
                }
                else if(radioButton2.isSelected()) {
                    jenisKelamin = radioButton2.getText();
                }
                else {
                    // Memberikan notifikasi jika jenis kelamin belum dipilih
                    JOptionPane.showMessageDialog(StudiKasusForm.this, "Pilih jenis kelamin terlebih dahulu!",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validasi data kosong
                if(nama.isEmpty() || nomorHP.isEmpty() || alamat.isEmpty()) {
                    JOptionPane.showMessageDialog(StudiKasusForm.this, "Semua input harus diisi!",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    // Mengonfirmasi sebelum melakukan penyimpanan data
                    int konfirmasi = JOptionPane.showConfirmDialog(StudiKasusForm.this, "Apakah anda yakin menyimpan data ?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);

                    if(konfirmasi == JOptionPane.YES_OPTION) {
                        // Menambahkan data
                        tableModel.add(new ArrayList<>(Arrays.asList(nama, nomorHP, jenisKelamin, alamat)));
                        // Membersihkan input setelah penyimpanan
                        textnama.setText("");
                        textnomor.setText("");
                        textalamat.setText("");
                        labelRadio.setText("Jenis Kelamin: ");
                    }
                }
            }
        });

       

        // Menambahkan ActionListener pada tombol "Hapus"
        buttonHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0) {
                    int option = JOptionPane.showConfirmDialog(StudiKasusForm.this, "Apakah anda yakin ingin menghapus data ?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    
                    if(option == JOptionPane.YES_OPTION) {
                        // Memanggil metode deleteRow untuk menghapus isi data tabel
                        tableModel.delete(selectedRow);
                        JOptionPane.showMessageDialog(StudiKasusForm.this, "Data berhasil dihapus!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(StudiKasusForm.this, "Pilih baris yang ingin dihapus!",
                    "Konfirmasi", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Menambahkan ActionListener pada tombol "Simpan ke File"
        buttonSimpanFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanFile(tableModel.getData());
            }
        });

        // Konfirmasi penutupan Jendela
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int konfirmasi = JOptionPane.showConfirmDialog(StudiKasusForm.this,
                        "Anda yakin ingin keluar ?", "Konfirmasi",JOptionPane.YES_NO_OPTION);
                if(konfirmasi == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        // Menambahkan komponen-komponen ke frame
        this.add(buttonSimpan);
        this.add(buttonEdit);
        this.add(buttonHapus);
        this.add(buttonSimpanFile);
        this.add(labelNama);
        this.add(textnama);
        this.add(labelnomor);
        this.add(textnomor);
        this.add(labelRadio);
        this.add(radioButton1);
        this.add(radioButton2);
        this.add(labelAlamat);
        this.add(textalamat);
        this.add(scrollableTable);

        // Mengatur ukuran dan tata letak frame
        this.setSize(600, 650);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudiKasusForm m = new StudiKasusForm();
                m.setVisible(true);
            }
        });
    }
}
