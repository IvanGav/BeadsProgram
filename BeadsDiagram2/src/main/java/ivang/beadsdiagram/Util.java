package ivang.beadsdiagram;

public class Util {
    //get magnitude of x component of rotation by an angle
    public static int rotX(int dist, double angle) {
        return (int)(dist*Math.cos(angle));
    }

    //get magnitude of y component of rotation by an angle
    public static int rotY(int dist, double angle) {
        return (int)(dist*Math.sin(angle));
    }

    //distance between 2 points
    public static double dist(int x1, int y1, int x2, int y2) {
        if(x1 == x2 && y1 == y2) return 0;
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }

    //angle between 2 points
    public static double angleBetween(int x1, int y1, int x2, int y2) {
        if(x1 == x2 && y1 == y2) return 0;
        int dx = x2 - x1;
        int dy = y2 - y1;
        double angle;
        angle = Math.asin(dy / Math.sqrt(dx * dx + dy * dy));
        if(dx < 0) angle = Math.PI - angle;
        return angle;
    }

    //find the angle that is multiple of 45 degrees closest to the given angle
    public static double snapAngle(double angle) {
        while(angle < 0) {
            angle += Math.PI*2;
        }
        double closeAngle = 0;
        while(Math.abs(angle - closeAngle - Math.PI/4) < Math.abs(angle - closeAngle)) {
            closeAngle += Math.PI/4;
        }
        return closeAngle;
    }
}
