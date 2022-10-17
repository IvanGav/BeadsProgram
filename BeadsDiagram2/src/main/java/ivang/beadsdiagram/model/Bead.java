package ivang.beadsdiagram.model;

/*
    Stores position, rotation and bezier curve points for strings coming from either end
 */
public class Bead {
    public int x,y; //position
    public double rot; //rotation
    public int upS,downS; //strength of string/line/bezier curve forward/backward
    //downS should be negative of magnitude of its strength

    public Bead(int x, int y) {
        this.x = x;
        this.y = y;
        rot = 0;
        upS = 50;
        downS = -50;
    }

    public Bead(int x, int y, double rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
        upS = 50;
        downS = -50;
    }

    public Bead(int x, int y, double rot, int upS, int downS) {
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.upS = upS;
        this.downS = downS;
    }
}

