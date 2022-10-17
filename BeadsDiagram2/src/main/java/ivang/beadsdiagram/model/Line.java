package ivang.beadsdiagram.model;

import java.util.ArrayList;

/*
    Stores beads that this string is going through by their numbers (which are kind of arbitrary, so not very good),
    the offsets when going through the bead (move string a bit up or down inside the bead) and end points.
    Provides a few functions to manipulate the string, which are not essential.
    Primary function is to store information about the string.
 */
public class Line {
    public ArrayList<Integer> beads = new ArrayList<>(); //bead numbers start at 1
    public ArrayList<Integer> offsets = new ArrayList<>();
    public VPoint start;
    public VPoint end;

    public Line() { }
    public Line(int[] b) {
        add(b);
    }
    public Line(int[] b, int[] o) {
        add(b,o);
    }

    //add all beads in array 'b'
    public void add(int[] b) {
        for (int i = 0; i < b.length; i++) {
            add(b[i]);
        }
    }
    //add all beads in array 'b' with offsets from 'o'. assuming 'b' and 'o' of same size
    public void add(int[] b, int[] o) {
        for (int i = 0; i < b.length; i++) {
            add(b[i], o[i]);
        }
    }

    //add a bead to the line (with no offsets)
    public void add(int b) {
        beads.add(b);
        offsets.add(0);
    }
    //add a bead to the line with offset
    public void add(int b, int o) {
        beads.add(b);
        offsets.add(o);
    }

    public void addEnds() {
        start = new VPoint(100,100);
        end = new VPoint(100,100);
    }

    //delete bead with a number 'n' from the list
    public void delete(int n) {
        for (int i = 0; i < beads.size(); i++) {
            if (beads.get(i) == Math.abs(n)) {
                beads.remove(i);
                offsets.remove(i);

            }
        }
        shift(n,-1); //move all bead numbers greater than 'n' 1 closer to 0
    }

    //Change values in beads list by 'value' if they are bigger than a 'limit'
    public void shift(int limit, int value) {
        for (int i = 0; i < beads.size(); i++) {
            int current = beads.get(i);
            if(current >= limit) beads.set(i, current+value);
            if(current <= -limit) beads.set(i, current-value);
        }
    }
}