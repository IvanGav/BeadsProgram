package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JLine;
import ivang.beadsdiagram.translate.JManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
    The UI under the main workspace. Mostly for line manipulation.
 */
public class Layout extends JPanel {
    JButton addLineButton;
    JButton deleteLineButton;
    JComboBox<String> lines;
    JTextField beadsIn;
    JTextField offsetsIn;
    JButton applyChangesButton;
    JManager dm;
    public Layout(JManager dm) {
        this.dm = dm;

        addLineButton = new JButton("New String");
        addLineButton.setFocusPainted(false);
        addLineButton.setContentAreaFilled(false);
        this.add(addLineButton);

        deleteLineButton = new JButton("Delete Selected String");
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

        addLineButton.addActionListener(e -> addLine());
        lines.addActionListener(e -> updateLineFields());
        applyChangesButton.addActionListener(e -> updateLine());
        deleteLineButton.addActionListener(e -> deleteLine());
    }

    //update beadsIn and offsetsIn (when the user changes the selected line)
    private void updateLineFields() {
        int i = getSelectedLine();
        if(i == 0) {
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
        //index-1 because index 0 is Nothing selected
        dm.getLine(i-1).set(beads,offsets,dm.getBead(beads[0]),dm.getBead(beads[beads.length-1]));
    }

    private void selectLine(int n) {
        lines.setSelectedIndex(n);
        updateLineFields();
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
