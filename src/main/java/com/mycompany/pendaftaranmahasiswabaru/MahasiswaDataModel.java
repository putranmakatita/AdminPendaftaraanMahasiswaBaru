/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pendaftaranmahasiswabaru;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 *
 * @author Putra Nurhuda Makatita
 */
public class MahasiswaDataModel {
    private String fileName046 = "mahasiswaData.txt";

    public ArrayList<Map<String, String>> getData() {
        ArrayList<Map<String, String>> data046 = new ArrayList<>();
        try {
            File myObj046 = new File(fileName046);
            Scanner myReader046 = new Scanner(myObj046);
            while (myReader046.hasNextLine()) {
                String line046 = myReader046.nextLine();
                String[] parts046 = line046.split(" \\| ");
                Map<String, String> user046 = new HashMap<>();
                user046.put("nim", parts046[0]);
                user046.put("nama", parts046[1]);
                user046.put("jurusan", parts046[2]);
                user046.put("asalSekolah", parts046[3]);
                user046.put("waktuPendaftaran", parts046[4]);
                user046.put("waktuPenyuntingan", parts046[5]);
                data046.add(user046);
            }
            myReader046.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return data046;
    }

    public boolean saveData(String nama, String jurusan, String asalSekolah) {
        String validation046 = validate("add", "", nama, jurusan, asalSekolah);
        if (!validation046.equals("")) {
            JOptionPane.showMessageDialog(null, validation046);
            return false;
        }

        try {
            String nim046 = generateNim(jurusan);
            if (nim046.equals("")) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat generate NIM.");
                return false;
            }

            FileWriter writer046 = new FileWriter(fileName046, true);
            String line046 = nim046 + " | " + nama + " | " + jurusan + " | " + asalSekolah + " | " + getDateTimeNow()
                    + " | "
                    + "-" + "\n";
            writer046.write(line046);
            writer046.close();
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
            return false;
        }
    }

    public boolean updateData(String nim, String nama, String jurusan, String asalSekolah) {
        String validation046 = validate("update", nim, nama, jurusan, asalSekolah);
        if (!validation046.equals("")) {
            JOptionPane.showMessageDialog(null, validation046);
            return false;
        }
        try {
            File file046 = new File(fileName046);
            Scanner reader046 = new Scanner(file046);
            ArrayList<String> updatedLines046 = new ArrayList<>();
            boolean found046 = false;

            while (reader046.hasNextLine()) {
                String line046 = reader046.nextLine();
                String[] parts046 = line046.split(" \\| ");
                if (parts046[0].equals(nim)) {
                    // jika ada perubahan jurusan maka nim perlu dibuat baru sesuai dengan
                    // jurusannya
                    if (!jurusan.equals(parts046[2])) {
                        String newNim046 = generateNim(jurusan);
                        if (newNim046.equals("")) {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat generate NIM baru.");
                            return false;
                        }
                        nim = newNim046;
                    }
                    found046 = true;
                    String newLine046 = nim + " | " + nama + " | " + jurusan + " | " + asalSekolah + " | " + parts046[4]
                            + " | " + getDateTimeNow();
                    updatedLines046.add(newLine046);
                } else {
                    updatedLines046.add(line046);
                }
            }
            reader046.close();
            if (!found046) {
                JOptionPane.showMessageDialog(null, "Data dengan NIM " + nim + " tidak ditemukan.");
                return false;
            }
            FileWriter writer046 = new FileWriter(fileName046, false);
            for (String updatedLine046 : updatedLines046) {
                writer046.write(updatedLine046 + "\n");
            }
            writer046.close();
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteData(String nim) {
        String validation046 = validate("delete", nim, "", "", "");
        if (!validation046.equals("")) {
            JOptionPane.showMessageDialog(null, validation046);
            return false;
        }

        try {
            File file046 = new File(fileName046);
            Scanner reader046 = new Scanner(file046);
            ArrayList<String> updatedLines046 = new ArrayList<>();

            boolean found046 = false;

            while (reader046.hasNextLine()) {
                String line046 = reader046.nextLine();
                String[] parts046 = line046.split(" \\| ");

                if (parts046[0].equals(nim)) {
                    found046 = true;
                    continue; // skip baris yang ingin dihapus
                }
                updatedLines046.add(line046);
            }
            reader046.close();

            if (!found046) {
                JOptionPane.showMessageDialog(null, "Data dengan NIM " + nim + " tidak ditemukan.");
                return false;
            }

            FileWriter writer046 = new FileWriter(fileName046, false);
            for (String updatedLine046 : updatedLines046) {
                writer046.write(updatedLine046 + "\n");
            }
            writer046.close();
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
            return false;
        }
    }

    public Map<String, String> getDataByNim(String nim) {
        Map<String, String> data046 = null;
        try {
            File myObj046 = new File(fileName046);
            Scanner myReader046 = new Scanner(myObj046);
            while (myReader046.hasNextLine()) {
                String line046 = myReader046.nextLine();
                String[] parts046 = line046.split(" \\| ");
                if (parts046.length >= 6 && parts046[0].equals(nim)) {
                    data046 = new HashMap<>();
                    data046.put("nim", parts046[0]);
                    data046.put("nama", parts046[1]);
                    data046.put("jurusan", parts046[2]);
                    data046.put("asalSekolah", parts046[3]);
                    data046.put("waktuPendaftaran", parts046[4]);
                    data046.put("waktuPenyuntingan", parts046[5]);
                    break; // sudah ketemu, keluar dari loop
                }
            }
            myReader046.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return data046;
    }

    // private String validate(String method, String nim, String nama, String
    // jurusan, String asalSekolah) {
    // String errorMessage = "";
    // if ((method.equals("update") || method.equals("delete")) && nim.equals("auto
    // generated")) {
    // errorMessage += "Silahkan pilih dulu NIM yang hendak diupdate." + "\n";
    // }

    // if (method.equals("update") || method.equals("add")) {
    // if (nama.equals("")) {
    // errorMessage += "Nama tidak boleh kosong." + "\n";
    // }
    // if (jurusan.equals("")) {
    // errorMessage += "Jurusan tidak boleh kosong." + "\n";
    // }
    // if (asalSekolah.equals("")) {
    // errorMessage += "Asal Sekolah tidak boleh kosong." + "\n";
    // }
    // }

    // return errorMessage;
    // }

    private String validate(String method, String nim, String nama, String jurusan, String asalSekolah) {
        List<String> checks046 = new ArrayList<>();
        checks046.add("nim");
        if (method.equals("update") || method.equals("add")) {
            checks046.add("nama");
            checks046.add("jurusan");
            checks046.add("asalSekolah");
        }

        return validateRecursive(method, nim, nama, jurusan, asalSekolah, checks046, 0, "");
    }

    private String validateRecursive(String method, String nim, String nama, String jurusan, String asalSekolah,
            List<String> checks, int index, String errorMessage046) {
        if (index >= checks.size()) {
            return errorMessage046;
        }

        String field046 = checks.get(index);

        switch (field046) {
            case "nim":
                if ((method.equals("update") || method.equals("delete")) && nim.equals("auto generated")) {
                    errorMessage046 += "Silahkan pilih dulu NIM yang hendak diupdate.\n";
                }
                break;
            case "nama":
                if (nama.equals("")) {
                    errorMessage046 += "Nama tidak boleh kosong.\n";
                }
                break;
            case "jurusan":
                if (jurusan.equals("")) {
                    errorMessage046 += "Jurusan tidak boleh kosong.\n";
                }
                break;
            case "asalSekolah":
                if (asalSekolah.equals("")) {
                    errorMessage046 += "Asal Sekolah tidak boleh kosong.\n";
                }
                break;
        }

        return validateRecursive(method, nim, nama, jurusan, asalSekolah, checks, index + 1, errorMessage046);
    }

    private String generateNim(String jurusan) {
        String kode046 = getKodeJurusan(jurusan);
        String urutan046 = getLastUrutan(kode046);

        if (urutan046.equals("") || kode046.equals("")) {
            return "";
        }

        return kode046 + urutan046;
    }

    private String getKodeJurusan(String jurusan) {
        String result046 = "";
        if (jurusan.equals("Teknik Informatika")) {
            result046 = "TIF";
        } else if (jurusan.equals("Teknik Industri")) {
            result046 = "TID";
        } else if (jurusan.equals("Desain Komunikasi Visual")) {
            result046 = "DKV";
        }
        return result046;
    }

    private String getLastUrutan(String kode) {
        String urutan046 = "";
        try {
            File myObj046 = new File(fileName046);
            Scanner myReader046 = new Scanner(myObj046);
            int max046 = 0;
            while (myReader046.hasNextLine()) {
                String line046 = myReader046.nextLine();
                String[] parts046 = line046.split(" \\| ");
                int urut046 = Integer.valueOf(parts046[0].substring(3)); // ambil angka urutan dimulai setelah kode
                                                                         // jurusan

                // hanya jika nilainya lebih besar dari maximum dan kode jurusan sama
                if (urut046 > max046 && parts046[0].substring(0, 3).equals(kode)) {
                    max046 = urut046;
                }
            }
            max046++; // naikan urutan +1;
            myReader046.close();
            urutan046 = leftPadWithZeros(String.valueOf(max046), 4);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return urutan046;
    }

    private static String leftPadWithZeros(String input, int length) {
        return String.format("%" + length + "s", input).replace(' ', '0');
    }

    private static String getDateTimeNow() {
        LocalDateTime dateTime046 = LocalDateTime.now();
        DateTimeFormatter formatter046 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted046 = dateTime046.format(formatter046);

        return formatted046;
    }

}
