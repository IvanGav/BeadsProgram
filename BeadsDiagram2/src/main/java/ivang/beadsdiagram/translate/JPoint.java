package ivang.beadsdiagram.translate;

import ivang.beadsdiagram.model.VPoint;

import java.awt.Point;

/*
    Pretty much same as Point, but provides 3 additional functions and constructor with awt point.
 */
public class JPoint extends VPoint {
    public JPoint(int x, int y) {
        super(x,y);
    }
    public JPoint(Point p) {
        super(p.x,p.y);
    }
    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public JPoint copy() {
        return new JPoint(x,y);
    }
}