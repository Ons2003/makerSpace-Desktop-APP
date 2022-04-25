package com.example.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Wissem {

    public static void create() throws IOException {

        File youssef = new File("C:\\Users\\GMC informatique\\IdeaProjects\\test\\src\\main\\resources\\com\\example\\test\\youssef.txt");
        youssef.createNewFile();

    }

    public static void write(String text) throws IOException{

        FileWriter myFileWriter = new FileWriter("C:\\Users\\GMC informatique\\IdeaProjects\\test\\src\\main\\resources\\com\\example\\test\\youssef.txt");
        myFileWriter.write(text);
        System.out.println(text);
        myFileWriter.close();
    }

    public static void write(HashMap<String, String> dataMap) throws IOException{
        FileWriter myFileWriter = new FileWriter("C:\\Users\\GMC informatique\\IdeaProjects\\test\\src\\main\\resources\\com\\example\\test\\youssef.txt");
        for (String i : dataMap.keySet()) {
            myFileWriter.write("key: " + i + " value: " + dataMap.get(i) + "\n");
            System.out.println("key: " + i + " value: " + dataMap.get(i) + "\n");
            myFileWriter.close();

        }
    }

    public static void main(String[] args) throws IOException{
        StylishController sc = new StylishController();
       //write(sc.userLoginDataBase);
    }




}
