package ivang.beadsdiagram.translate;

import ivang.beadsdiagram.Util;
import ivang.beadsdiagram.model.Line;

/*
    Pretty much same as Line, but uses JPoints instead of VPoints.
    For endpoints, stores JPoints on initialization and when getting, casts to JPoints.
 */
public class JLine extends Line {
    public JLine() { }
    public JLine(int[] b) {
        super(b);
    }
    public JLine(int[] b, int[] o) {
        super(b,o);
    }
    public void addEnds() {
        start = new JPoint(100,100);
        end = new JPoint(100,100);
    }
    public JPoint getStart() {
        return (JPoint) start;
    }
    public JPoint getEnd() {
        return (JPoint) end;
    }

    //set all beads to 'b' with offsets 'o' (assuming same size arrays) and move endpoints to Beads
    public void set(int[] b, int[] o, JBead first, JBead last) {
        int setLen = 75;
        boolean changeFirst = beads.size() == 0 || beads.get(0) != b[0];
        boolean changeLast = beads.size() == 0 || beads.get(beads.size()-1) != b[b.length-1];
        deleteAll();
        add(b,o);
        //if going from - to + in the first bead
        if(changeFirst)
            if(b[0] > 0) {
                start = new JPoint(Util.rotX(-setLen,first.rot) + first.x, Util.rotY(-setLen,first.rot) + first.y);
            } else {
                start = new JPoint(Util.rotX(setLen,first.rot) + first.x, Util.rotY(setLen,first.rot) + first.y);
            }
        //if going from - to + in the last bead
        if(changeLast)
            if(b[b.length-1] > 0) {
                end = new JPoint(Util.rotX(setLen,last.rot) + last.x, Util.rotY(setLen,last.rot) + last.y);
            } else {
                end = new JPoint(Util.rotX(-setLen,last.rot) + last.x, Util.rotY(-setLen,last.rot) + last.y);
            }
    }
    //set all beads to 'b' and move endpoints to Beads
    public void set(int[] b, JBead first, JBead last) {
        int setLen = 75;
        boolean changeFirst = beads.size() == 0 || beads.get(0) != b[0];
        boolean changeLast = beads.size() == 0 || beads.get(beads.size()-1) != b[b.length-1];
        deleteAll();
        add(b);
        //if going from - to + in the first bead
        if(changeFirst)
            if(b[0] > 0) {
                start = new JPoint(Util.rotX(-setLen,first.rot) + first.x, Util.rotY(-setLen,first.rot) + first.y);
            } else {
                start = new JPoint(Util.rotX(setLen,first.rot) + first.x, Util.rotY(setLen,first.rot) + first.y);
            }
        //if going from - to + in the last bead
        if(changeLast)
            if(b[b.length-1] > 0) {
                end = new JPoint(Util.rotX(setLen,last.rot) + last.x, Util.rotY(setLen,last.rot) + last.y);
            } else {
                end = new JPoint(Util.rotX(-setLen,last.rot) + last.x, Util.rotY(-setLen,last.rot) + last.y);
            }
    }
}
