package ivang.beadsdiagram.translate;

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
}
