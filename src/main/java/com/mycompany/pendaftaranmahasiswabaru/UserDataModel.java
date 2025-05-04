/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pendaftaranmahasiswabaru;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Putra Nurhuda Makatita
 */
public class UserDataModel {
    private String fileName046 = "userData.txt";

    public ArrayList<Map<String, String>> getData() {
        ArrayList<Map<String, String>> data046 = new ArrayList<>();
        try {
            File myObj046 = new File(fileName046);
            Scanner myReader046 = new Scanner(myObj046);
            while (myReader046.hasNextLine()) {
                String line046 = myReader046.nextLine();
                String[] parts046 = line046.split(" \\| ");
                Map<String, String> user046 = new HashMap<>();
                user046.put("username", parts046[0]);
                user046.put("password", parts046[1]);
                data046.add(user046);
            }
            myReader046.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return data046;
    }
}
