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

    //delete line by index
    public void deleteLine(int n) {
        lines.remove(n);
    }

    public Line getLine(int i) {
        return lines.get(i);
    }

    public int numLines() {
        return lines.size();
    }

    //create a bead at the end
    public void addBead(int x, int y, double rot) {
        beads.add(new Bead(x,y,rot));
    }

    //create a bead with number n
    public void addBead(int x, int y, double rot, int n) {
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
        return beads.get(Math.abs(n)-1);
    }

    public int numBeads() {
        return beads.size();
    }
}
