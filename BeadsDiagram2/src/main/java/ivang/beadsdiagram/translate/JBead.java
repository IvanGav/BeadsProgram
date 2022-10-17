package ivang.beadsdiagram.translate;

import ivang.beadsdiagram.Util;
import ivang.beadsdiagram.model.Bead;

/*
    Instead of giving access to upS and downS (the distance from the bead to the point on bezier curve in its direction)
    provides upP and downP JPoints. They are updated automatically when moving/rotating bead and should be moved with
    moveUp and moveDown functions.
 */
public class JBead extends Bead {
    public JPoint upP, downP;

    public JBead(int x, int y) {
        super(x,y);
        upP = new JPoint(x,y);
        downP = new JPoint(x,y);
        updateEnds();
    }

    public JBead(int x, int y, double rot) {
        super(x,y,rot);
        upP = new JPoint(x,y);
        downP = new JPoint(x,y);
        updateEnds();
    }

    public JBead(int x, int y, double rot, int upS, int downS) {
        super(x,y,rot,upS,downS);
        upP = new JPoint(x,y);
        downP = new JPoint(x,y);
        updateEnds();
    }

    public void updateEnds() {
        upP.moveTo(Util.rotX(upS,rot) + x, Util.rotY(upS,rot) + y);
        downP.moveTo(Util.rotX(downS,rot) + x, Util.rotY(downS,rot) + y);
    }

    //pos getter/setter
    public JPoint getPoint() {
        return new JPoint(x,y);
    }

    public void moveTo(int x, int y) {
        translate(x - this.x,y - this.y);
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
        updateEnds();
    }

    //rot getter/setter
    public double getRot() {
        return rot;
    }

    public void rotateTo(double r) {
        rotate(r - rot);
    }

    public void rotate(double r) {
        rot += r;
        updateEnds();
    }

    //move upP to a certain (x,y); it will automatically adjust to the axis of direction of this bead
    public void moveUp(int x, int y) {
        double theta = Util.angleBetween(this.x, this.y, x, y) - this.rot;
        double dist = Util.dist(this.x, this.y, x, y);
        setUpStrength(Util.rotX((int)dist, theta));
    }

    //move downP to a certain (x,y); it will automatically adjust to the axis of direction of this bead
    public void moveDown(int x, int y) {
        double theta = Util.angleBetween(this.x, this.y, x, y) - this.rot;
        double dist = Util.dist(this.x, this.y, x, y);
        setDownStrength(Util.rotX((int) dist, theta));
    }

    //set upS to a certain number
    public void setUpStrength(int s) {
        this.upS = s;
        updateEnds();
    }

    //set downS to a certain number
    public void setDownStrength(int s) {
        this.downS = s;
        updateEnds();
    }
}
