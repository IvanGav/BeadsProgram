package ivang.beadsdiagram.model;

import java.util.ArrayList;

/*
    Stores array of beads and lines. All interactions with the diagram should be done through it.
 */
public class DiagramManager {
    protected ArrayList<Bead> beads; //bead index in array is bead number-1 ALWAYS
    protected ArrayList<Line> lines;

    public DiagramManager() {
        beads = new ArrayList<>();
        lines = new ArrayList<>();
    }

    //temporary?
    public void addEndsToLines() {
        for (Line line : lines) {
            line.addEnds();
        }
    }

    public void addLine() {
        lines.add(new Line());
        lines.get(lines.size()-1).addEnds();
    }

    public void addLine(int[] b) {
        lines.add(new Line(b));
        lines.get(lines.size()-1).addEnds();
    }

    public void addLine(int[] b, int[] o) {
        lines.add(new Line(b,o));
        lines.get(lines.size()-1).addEnds();
    }

    public Line getLine(int i) {
        return lines.get(i);
    }

    public int numLines() {
        return lines.size();
    }

    //create a bead at the end
    public void createBead(int x, int y, double rot) {
        beads.add(new Bead(x,y,rot));
    }

    //create a bead with number n
    public void createBead(int x, int y, double rot, int n) {
        beads.add(n-1, new Bead(x,y,rot));
        for(Line l: lines)
            l.shift(n, 1);
    }

    //delete bead by index
    public void deleteBead(int n) {
        for(Line l: lines)
            l.delete(n);
        beads.remove(n-1);
    }

    //get a bead from a list
    public Bead getBead(int n) {
        return beads.get(n-1);
    }

    public int numBeads() {
        return beads.size();
    }
}
