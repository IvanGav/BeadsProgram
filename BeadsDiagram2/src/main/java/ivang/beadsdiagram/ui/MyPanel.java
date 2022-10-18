package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.Util;
import ivang.beadsdiagram.translate.JBead;
import ivang.beadsdiagram.translate.JManager;
import ivang.beadsdiagram.translate.JPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
    MyPanel is what the diagram is going to be drawn on. Implements AWTEventListener
    and updates variables/calls functions when certain events happen.
    The idea is that I would extend this class (UI class) and write some specific behaviors there.
    This class would serve something of a middle point between AWT (Drawing) and UI (Interaction with user).
 */
public class MyPanel extends JPanel implements AWTEventListener {

    int dx = 8;
    int dy = 31;

    public final int MOUSE_LEFT = MouseEvent.BUTTON1;
    public final int MOUSE_RIGHT = MouseEvent.BUTTON3;

    public final int SLIDER_CAPTURE_RADIUS = 10;
    public final int BEAD_RADIUS = 15;

    public final int NO_ACTION = 0;
    public final int CREATE_BEAD = 1;
    public final int CHANGE_SLIDER = 2;
    public final int LINE_END_MOVE = 3;
    public final int MOVE_BEAD = 4;
    public final int ROTATE_BEAD = 5;

    public final int workspaceX;
    public final int workspaceY;

    public JManager dm;
    public Image image;

    //current mouse position
    public int mouseX;
    public int mouseY;

    //mouse down position
    public int mouseDownX;
    public int mouseDownY;

    //mouse up position
    public int mouseUpX;
    public int mouseUpY;

    public boolean isMouseDown;
    public boolean isMouseInWorkspace;

    public MyPanel(JManager dm) {
        image = new ImageIcon("placeholder.jpg").getImage();
        int imgW = image.getWidth(null);
        int imgH = image.getHeight(null);

        int screenSize = 750;
        workspaceX = screenSize;
        workspaceY = screenSize * imgH / imgW;

        this.dm = dm;

        isMouseDown = false;

        this.setPreferredSize(new Dimension(workspaceX,workspaceY));
        this.setVisible(true);
        //listener
        Toolkit.getDefaultToolkit().addAWTEventListener(this,
                AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
    }

    //___________________________Draw and listen to mouse/keyboard______________________________

    //listen to mouse/keyboard
    public void eventDispatched(AWTEvent event) {
        if(event.getID() == 503) { //mouse moved
            MouseEvent me = (MouseEvent) event;
            isMouseInWorkspace = !isMouseOutOfBounds(me);
            if(isMouseInWorkspace) {
                updateMousePos(me);
                mouseMoved();
            }
        }
        if(event.getID() == 506) { //mouse dragged (pressed moved)
            MouseEvent me = (MouseEvent) event;
            if(isMouseInWorkspace) {
                updateMousePos(me);
                mouseDragged(me.getButton());
            }
        }
        if(event.getID() == 501) { //mouse pressed
            MouseEvent me = (MouseEvent) event;
            if(isMouseInWorkspace) {
                updateMouseDown(me);
                mousePressed(me.getButton());
            }
        }
        if(event.getID() == 502) { //mouse released
            MouseEvent me = (MouseEvent) event;
            if(isMouseInWorkspace) {
                updateMouseUp(me);
                mouseReleased();
            }
        }
        if(event.getID() == 500) { //mouse clicked
            MouseEvent me = (MouseEvent) event;
            mouseClicked(me.getButton(), me.getClickCount());
        }
        if(event.getID() == 400) { //key typed
            KeyEvent ke = (KeyEvent) event;
            keyTyped(ke.getKeyChar());
        }
    }

    //draw
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        g2d.drawImage(image, 0, 0, workspaceX, workspaceY, null);
        g2d.setPaint(Color.BLACK);
        drawBeads(g2d);
        drawStrings(true, g2d);
    }

    //____________________________________________Events_____________________________________________

    private void updateMousePos(MouseEvent me) {
        JPoint mouseP = new JPoint(me.getPoint());
        mouseX = mouseP.x - dx;
        mouseY = mouseP.y - dy;
    }

    private void updateMouseDown(MouseEvent me) {
        JPoint mouseDownP = new JPoint(me.getPoint());
        mouseDownX = mouseDownP.x - dx;
        mouseDownY = mouseDownP.y - dy;
        isMouseDown = true;
    }

    private void updateMouseUp(MouseEvent me) {
        JPoint mouseUpP = new JPoint(me.getPoint());
        mouseUpX = mouseUpP.x - dx;
        mouseUpY = mouseUpP.y - dy;
        isMouseDown = false;
    }

    //____________________________To be implemented in ivang.beadsdiagram.ui.UI if needed__________________________

    public void mousePressed(int button) { }

    public void mouseReleased() { }

    public void mouseDragged(int button) { }

    public void mouseMoved() { }

    public void mouseClicked(int button, int clickCount) { }

    public void keyTyped(char key) { }

    //__________________________________Calculating mouse things functions________________________________________

    //return the +bead number in array if mouse is close to its upS slider, -beadN if close to downS
    //  and 0 if mouse is not near any slider. Prefers lower bead numbers if some overlap.
    public int strengthSlider() {
        for (int i = 1; i <= dm.numBeads(); i++) {
            JBead b = dm.getBead(i);
            if(Util.dist(b.upP.x, b.upP.y, mouseX, mouseY) < SLIDER_CAPTURE_RADIUS) {
                return i;
            }
            if(Util.dist(b.downP.x, b.downP.y, mouseX, mouseY) < SLIDER_CAPTURE_RADIUS) {
                return -i;
            }
        }
        return 0;
    }

    //return the point at the end of the line (string) if it's close to a mouse
    public JPoint endGrabbed() {
        for (int i = 0; i < dm.numLines(); i++) {
            if (Util.dist(dm.getLine(i).start.x, dm.getLine(i).start.y, mouseX, mouseY) < SLIDER_CAPTURE_RADIUS)
                return dm.getLine(i).getStart();
            if (Util.dist(dm.getLine(i).end.x, dm.getLine(i).end.y, mouseX, mouseY) < SLIDER_CAPTURE_RADIUS)
                return dm.getLine(i).getEnd();
        }
        return null;
    }

    //return an index of a bead that is touching the mouse (0 if none)
    public int beadGrabbed() {
        for (int i = 1; i <= dm.numBeads(); i++) {
            if (Util.dist(dm.getBead(i).x, dm.getBead(i).y, mouseX, mouseY) < BEAD_RADIUS)
                return i;
        }
        return 0;
    }

    //return true if mouse is out of workspace boundaries (or over something but MyPanel)
    public boolean isMouseOutOfBounds(MouseEvent me) {
        JPoint mousePos = new JPoint(me.getPoint());
        return mousePos.x < dx || mousePos.y < dy || mousePos.x > dx + workspaceX || mousePos.y > dy + workspaceY
                || me.getSource().getClass() != MyFrame.class;
    }

    //____________________________________Drawing methods_________________________________________________

    //draw beads
    public void drawBeads(Graphics2D g2d) {
        for (int i = 1; i <= dm.numBeads(); i++) {
            bead(g2d, i);
            JBead b = dm.getBead(i);
            g2d.drawString(i+"+", b.upP.x, b.upP.y);
            drawCircle(b.upP.x,b.upP.y,SLIDER_CAPTURE_RADIUS,g2d);
            g2d.drawString(i+"-", b.downP.x, b.downP.y);
            drawCircle(b.downP.x,b.downP.y,SLIDER_CAPTURE_RADIUS,g2d);
        }
    }

    //draw strings (lines)
    public void drawStrings(boolean drawEnds, Graphics2D g2d) {
        for (int i = 0; i < dm.numLines(); i++) {
            if(dm.getLine(i).beads.size() > 0) {
                //shortcut assignments
                ArrayList<Integer> points = dm.getLine(i).beads;
                ArrayList<Integer> offsets = dm.getLine(i).offsets;
                JPoint start = dm.getLine(i).getStart();
                JPoint end = dm.getLine(i).getEnd();
                //start to first bead
                if (drawEnds && start != null && dm.numBeads() >= Math.abs(points.get(0))) {
                    drawStringBetween(start, -points.get(0), offsets.get(0), g2d);
                    drawCircle(start.x, start.y, SLIDER_CAPTURE_RADIUS, g2d);
                }
                //between beads
                for (int j = 0; j < points.size() - 1; j++) {
                    if (dm.numBeads() >= Math.abs(points.get(j)) && dm.numBeads() >= Math.abs(points.get(j + 1))) {
                        drawStringBetween(points.get(j), points.get(j + 1), offsets.get(j), offsets.get(j + 1), g2d);
                    }
                }
                //last bead to end
                if (drawEnds && end != null && dm.numBeads() >= Math.abs(points.get(points.size() - 1))) {
                    drawStringBetween(end, points.get(points.size() - 1), offsets.get(offsets.size() - 1), g2d);
                    drawCircle(end.x, end.y, SLIDER_CAPTURE_RADIUS, g2d);
                }
            }
        }
    }

    //_____________________________________Other functions related to drawing_________________________________________

    //draw a bead number n in the array of beads
    private void bead(Graphics2D g, int n) { //n is still the number of bead - index in ArrayList+1
        JBead b = dm.getBead(n);
        drawCircle(b.x,b.y,BEAD_RADIUS,g); // -d because START OF A CIRCLE IS UP LEFT. Actual coordinates are b.x, b.y
        g.drawString(""+n,b.x+20,b.y);
        int dx = Util.rotX(BEAD_RADIUS,b.rot);
        int dy = Util.rotY(BEAD_RADIUS,b.rot);
        g.drawLine(b.x,b.y,b.x+dx,b.y+dy);
    }

    //calculate bezier curve value for 4 points at specific t (0 <= t <= 1)
    private int calcBezier(int p1, int p2, int p3, int p4, double t) {
        double v1 = Math.pow(1-t, 3) * p1;
        double v2 = 3 * Math.pow(1-t, 2) * t * p2;
        double v3 = 3 * (1 - t) * Math.pow(t, 2) * p3;
        double v4 = Math.pow(t, 3) * p4;
        return (int) (v1 + v2 + v3 + v4);
    }

    //draw a string between 2 beads in the array of beads
    //if first or second is negative, it means that the string is going through them from back to front
    public void drawStringBetween(int first, int second, int offFirst,int offSecond, Graphics2D g2d) {
        //setup
        JBead b1 = dm.getBead(Math.abs(first));
        JBead b2 = dm.getBead(Math.abs(second));
        //p1 and p4 = beads: first and second
        JPoint p1 = b1.getPoint();
        p1.translate(Util.rotY(offFirst,b1.rot),Util.rotX(offFirst,b1.rot)); //x goes to y and wise versa to rotate it 90 degrees
        JPoint p4 = b2.getPoint();
        p4.translate(Util.rotY(offSecond,b2.rot),Util.rotX(offSecond,b2.rot)); //same here
        JPoint p2 = p1.copy();
        if(first >= 0)
            p2.translate(Util.rotX(b1.upS,b1.rot),Util.rotY(b1.upS,b1.rot));
        else
            p2.translate(Util.rotX(b1.downS,b1.rot),Util.rotY(b1.downS,b1.rot));
        JPoint p3 = p4.copy();
        if(second >= 0)
            p3.translate(Util.rotX(b2.downS,b2.rot),Util.rotY(b2.downS,b2.rot));
        else
            p3.translate(Util.rotX(b2.upS,b2.rot),Util.rotY(b2.upS,b2.rot));
        //actual drawing
        drawBezier(p1,p2,p3,p4,g2d);
    }

    //draw a string between a bead in the array of beads and a point (quadratic bezier)
    public void drawStringBetween(JPoint p, int beadN, int offset, Graphics2D g2d) {
        //setup
        JBead b = dm.getBead(Math.abs(beadN));
        JPoint p1 = b.getPoint();
        p1.translate(Util.rotY(offset,b.rot),Util.rotX(offset,b.rot));
        JPoint p3 = p;
        JPoint p2 = p1.copy();
        if(beadN >= 0)
            p2.translate(Util.rotX(b.upS,b.rot),Util.rotY(b.upS,b.rot));
        else
            p2.translate(Util.rotX(b.downS,b.rot),Util.rotY(b.downS,b.rot));
        //actual drawing
        drawBezier(p1,p2,p2,p3,g2d);
    }

    //draw a bezier curve using 4 points (cubic)
    public void drawBezier(JPoint p1, JPoint p2, JPoint p3, JPoint p4, Graphics2D g2d) {
        double detail = 20;
        for (int t = 0; t < detail; t++) {
            g2d.drawLine(calcBezier(p1.x,p2.x,p3.x,p4.x,t / detail),calcBezier(p1.y,p2.y,p3.y,p4.y,t / detail),
                    calcBezier(p1.x,p2.x,p3.x,p4.x,(t+1) / detail),calcBezier(p1.y,p2.y,p3.y,p4.y,(t+1) / detail));
        }
    }

    //draw a circle with center at (x,y) and radius 'r'
    private void drawCircle(int x, int y, int r, Graphics2D g2d) {
        g2d.drawOval(x-r,y-r,r*2,r*2);
    }
}
