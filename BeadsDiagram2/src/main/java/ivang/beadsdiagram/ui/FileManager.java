package ivang.beadsdiagram.ui;

import com.google.gson.Gson;
import ivang.beadsdiagram.model.Bead;
import ivang.beadsdiagram.model.DiagramManager;
import ivang.beadsdiagram.model.Line;
import ivang.beadsdiagram.translate.JManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

    public static void saveFile(String name, JManager dm) {
        Gson g = new Gson();
        String json = g.toJson(toDM(dm));
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
        return toJM(g.fromJson(json, DiagramManager.class));
    }

    private static DiagramManager toDM(JManager jm) {
        DiagramManager dm = new DiagramManager();
        for (int i = 1; i <= jm.numBeads(); i++) {
            Bead b = jm.getBead(i);
            dm.addBead(b.x,b.y,b.rot);
            dm.getBead(i).upS = b.upS;
            dm.getBead(i).downS = b.downS;
        }
        for (int i = 0; i < jm.numLines(); i++) {
            Line l = jm.getLine(i);
            dm.addLine(toArray(l.beads),toArray(l.offsets));
            dm.getLine(i).setStart(l.getStart());
            dm.getLine(i).setEnd(l.getEnd());
        }
        return dm;
    }
    private static JManager toJM(DiagramManager dm) {
        JManager jm = new JManager();
        for (int i = 1; i <= dm.numBeads(); i++) {
            Bead b = dm.getBead(i);
            jm.addBead(b.x,b.y,b.rot);
            jm.getBead(i).setDownStrength(b.downS);
            jm.getBead(i).setUpStrength(b.upS);
        }
        for (int i = 0; i < dm.numLines(); i++) {
            Line l = dm.getLine(i);
            jm.addLine(toArray(l.beads),toArray(l.offsets));
            jm.getLine(i).setStart(l.getStart());
            jm.getLine(i).setEnd(l.getEnd());
        }
        return jm;
    }
    private static int[] toArray(ArrayList<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
