package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JLine;
import ivang.beadsdiagram.translate.JManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Layout extends JPanel {
    JButton addLineButton;
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
    }

    //when the user changes the selected line
    private void updateLineFields() {
        int i = lines.getSelectedIndex();
        if(i == 0) {
            beadsIn.setText("");
            offsetsIn.setText("");
        } else {
            JLine l = dm.getLine(i-1);
            beadsIn.setText(toString(l.beads));
            offsetsIn.setText(toString(l.offsets));
        }
    }

    //add a new line (button)
    private void addLine() {
        dm.addLine();
        lines.addItem("String " + dm.numLines());
    }

    //apply changes button
    private void updateLine() {
        if(lines.getSelectedIndex() == 0) return;
        int[] beads = parse(beadsIn.getText());
        int[] offsets = parse(offsetsIn.getText());
        if(beads == null || offsets == null) return;
        //index-1 because index 0 is Nothing selected
        dm.getLine(lines.getSelectedIndex()-1).set(beads,offsets);
    }

    //String to int[]
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
