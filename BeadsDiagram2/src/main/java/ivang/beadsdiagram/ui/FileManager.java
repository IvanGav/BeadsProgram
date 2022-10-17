package ivang.beadsdiagram.ui;

import com.google.gson.Gson;
import ivang.beadsdiagram.model.DiagramManager;
import ivang.beadsdiagram.translate.JManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
    Save a JManager to a file/load JManager from a file
 */
public class FileManager {

    private static String path = "";

    public static void createFile(String name) {
        try {
            File file = new File(path + name + ".json");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void saveFile(String name, DiagramManager dm) {
        Gson g = new Gson();
        String json = g.toJson(dm);
        try {
            FileWriter myWriter = new FileWriter(path + name + ".json");
            myWriter.write(json);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static JManager loadFile(String name) {
        String json = "";
        try {
            File myObj = new File(path + name + ".json");
            Scanner reader = new Scanner(myObj);
            json = reader.next();
            reader.close();
            System.out.println("Successfully loaded the file.");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Gson g = new Gson();
        return g.fromJson(json, JManager.class);
    }
}
