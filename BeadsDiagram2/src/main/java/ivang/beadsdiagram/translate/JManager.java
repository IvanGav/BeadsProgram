package ivang.beadsdiagram.translate;

import ivang.beadsdiagram.model.DiagramManager;
import ivang.beadsdiagram.model.Line;

/*
    Very similar to DiagramManager, but stores JBead and JLine in DiagramManager's arrays
    (and when getting them casts back to JBead and JLine).
 */
public class JManager extends DiagramManager {
    //create a bead at the end
    public void createBead(int x, int y, double rot) {
        beads.add(new JBead(x,y,rot));
    }

    //create a bead with number n
    public void createBead(int x, int y, double rot, int n) {
        beads.add(n-1, new JBead(x,y,rot));
        for(Line l : lines)
            l.shift(n, 1);
    }

    //get a bead from a list
    public JBead getBead(int n) {
        return (JBead) super.getBead(n);
    }

    public void addLine() {
        lines.add(new JLine());
        lines.get(lines.size()-1).addEnds();
    }

    //create a JLine
    public void addLine(int[] b) {
        lines.add(new JLine(b));
        lines.get(lines.size()-1).addEnds();
    }

    //create a JLine with offsets
    public void addLine(int[] b, int[] o) {
        lines.add(new JLine(b,o));
        lines.get(lines.size()-1).addEnds();
    }

    //get a JLine at a specific index
    public JLine getLine(int i) {
        return (JLine) super.getLine(i);
    }
}
