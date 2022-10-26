package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JLine;
import ivang.beadsdiagram.translate.JManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
    The UI under the main workspace. Mostly for line manipulation.
    All sizes of buttons/fields are hardcoded for now.
 */
public class Layout extends JPanel {
    JButton addLineButton;
    JButton deleteLineButton;
    JComboBox<String> lines;
    JTextField beadsIn;
    JTextField offsetsIn;
    JButton applyChangesButton;
    JButton saveButton;
    JButton loadButton;
    JManager dm;
    public Layout(JManager dm) {
        this.dm = dm;

        //set up all the buttons and other UI in the lower layout
        addLineButton = new JButton("New String");
        addLineButton.setFocusPainted(false);
        addLineButton.setContentAreaFilled(false);
        this.add(addLineButton);

        deleteLineButton = new JButton("Delete Selected");
        deleteLineButton.setFocusPainted(false);
        deleteLineButton.setContentAreaFilled(false);
        this.add(deleteLineButton);

        lines = new JComboBox<>(new String[]{"No Selection"});
        this.add(lines);

        beadsIn = new JTextField();
        beadsIn.setPreferredSize(new Dimension(100,25));
        this.add(beadsIn);

        offsetsIn = new JTextField();
        offsetsIn.setPreferredSize(new Dimension(100,25));
        this.add(offsetsIn);

        applyChangesButton = new JButton("Apply");
        applyChangesButton.setFocusPainted(false);
        applyChangesButton.setContentAreaFilled(false);
        this.add(applyChangesButton);

        saveButton = new JButton("Save");
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(false);
        this.add(saveButton);

        loadButton = new JButton("Load");
        loadButton.setFocusPainted(false);
        loadButton.setContentAreaFilled(false);
        this.add(loadButton);

        //add event listeners (so that something happens when you click buttons or change selected line)
        addLineButton.addActionListener(e -> addLine());
        lines.addActionListener(e -> updateLineFields());
        applyChangesButton.addActionListener(e -> updateLine());
        deleteLineButton.addActionListener(e -> deleteLine());
        saveButton.addActionListener(e -> saveDM());
        loadButton.addActionListener(e -> loadDM());
    }

    private void saveDM() {
        FileManager.saveFile("test", dm);
    }

    private void loadDM() {
        dm.load(FileManager.loadFile("test"));
        selectLine(0);
        updateLines();
    }

    private void updateLines() {
        for (int i = 1; i < lines.getItemCount(); i++) {
            lines.removeItemAt(1);
        }
        for (int i = 0; i < dm.numLines(); i++) {
            lines.addItem("String " + (i+1));
        }
    }

    //update beadsIn and offsetsIn (when the user changes the selected line)
    private void updateLineFields() {
        int i = getSelectedLine();
        if(i < 1) {
            setInputFields("","");
        } else {
            JLine line = dm.getLine(i-1);
            setInputFields(toString(line.beads), toString(line.offsets));
        }
    }

    //add a new line (button)
    private void addLine() {
        dm.addLine();
        lines.addItem("String " + dm.numLines());
        selectLine(lines.getItemCount()-1);
    }

    //delete the selected line (button)
    private void deleteLine() {
        int i = getSelectedLine();
        if(i == 0) return;
        dm.deleteLine(i-1);
        selectLine(0);
        lines.removeItemAt(lines.getItemCount()-1);
    }

    //apply changes button
    private void updateLine() {
        int i = getSelectedLine();
        if(i == 0) return;
        int[] beads = parse(getBeadsIn());
        int[] offsets = parse(getOffsetsIn());
        if(beads == null || offsets == null) return;
        //index-1 because index 0 is Nothing selected; If offsets is empty, assuming all 0's
        if(offsets.length == 0)
            dm.getLine(i-1).set(beads,dm.getBead(beads[0]),dm.getBead(beads[beads.length-1]));
        else if(beads.length == offsets.length)
            dm.getLine(i-1).set(beads,offsets,dm.getBead(beads[0]),dm.getBead(beads[beads.length-1]));
    }

    private void selectLine(int n) {
        lines.setSelectedIndex(n);
//        updateLineFields();
    }

    private int getSelectedLine() {
        return lines.getSelectedIndex();
    }

    private String getBeadsIn() {
        return beadsIn.getText();
    }

    private String getOffsetsIn() {
        return offsetsIn.getText();
    }

    private void setInputFields(String beads, String offsets) {
        beadsIn.setText(beads);
        offsetsIn.setText(offsets);
    }

    //String to int[]; if not a valid string, return null
    private int[] parse(String in) {
        if(in.equals("")) return new int[]{};
        String[] s = in.split(",");
        int[] n = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            try {
                n[i] = Integer.parseInt(s[i]);
            } catch (Exception e) {
                return null;
            }
        }
        return n;
    }

    //ArrayList to String, but without []
    private String toString(ArrayList<Integer> n) {
        if(n.size() == 0) return "";
        if(n.size() == 1) return "" + n.get(0);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n.size() - 1; i++) {
            s.append(n.get(i)).append(",");
        }
        s.append(n.get(n.size() - 1));
        return s.toString();
    }
}
