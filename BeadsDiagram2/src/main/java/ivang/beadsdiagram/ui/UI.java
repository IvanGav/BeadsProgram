package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.Util;
import ivang.beadsdiagram.translate.JBead;
import ivang.beadsdiagram.translate.JManager;
import ivang.beadsdiagram.translate.JPoint;

import java.awt.*;

/*
    Extends UI and implements some of its functions. This class is defining what happens when
    user does something.
 */
public class UI extends Workspace {

    private int action = NO_ACTION;
    private int slider;
    private JPoint lineEnd;
    private int grabbedBead;
    private double tempAngle;
    private int numSelected = 1;

    private int snapX;
    private int snapY;

    public UI (JManager dm) {
        super(dm);
    }

    public void mousePressed(int button) {
        slider = strengthSlider();
        lineEnd = endGrabbed();
        grabbedBead = beadGrabbed();
        if(button == MOUSE_LEFT) LMB();
        else RMB();
    }

    //left mouse button pressed
    private void LMB() {
        if(slider != 0)
            action = CHANGE_SLIDER;
        else if(lineEnd != null)
            action = LINE_END_MOVE;
        else if(grabbedBead != 0)
            action = MOVE_BEAD;
        else
            action = CREATE_BEAD;
    }

    //right mouse button pressed
    private void RMB() {
        if(grabbedBead != 0) {
            action = ROTATE_BEAD;
            JBead b = dm.getBead(grabbedBead);
            tempAngle = Util.angleBetween(b.x, b.y, mouseX, mouseY) - b.getRot();
        }
    }

    public void mouseReleased() {
        if(action == CREATE_BEAD) {
            if(numSelected == 1)
                createBead();
            else
                createBeads();
        }
        action = NO_ACTION;
    }

    public void mouseDragged(int button) {
        if(action == CHANGE_SLIDER)
            moveSlider();
        else if (action == LINE_END_MOVE)
            moveEnd();
        else if (action == MOVE_BEAD)
            moveBead();
        else if (action == ROTATE_BEAD)
            rotBead();
    }

    public void mouseClicked(int button, int clickCount) {
        if(button == MOUSE_RIGHT && clickCount > 1)
            deleteBead(grabbedBead);
    }

    public void keyTyped(char key) {
        if(key > 48 && key < 58)
            numSelected = key - 48;
        if(key == 's') FileManager.saveFile("test", dm);
        if(key == 'n') FileManager.createFile("test");
        if(key == 'l') dm = FileManager.loadFile("test"); //no, wrong. dm is not going to be changed outside of ui class
    }

    public void draw(Graphics2D g2d) {
        if(action == CREATE_BEAD) {
            if(isShiftDown) {
                setSnapPos(mouseDownX,mouseDownY,mouseX,mouseY);
                drawLine(mouseDownX, mouseDownY, snapX, snapY, g2d);
            } else {
                drawLine(mouseDownX, mouseDownY, mouseX, mouseY, g2d);
            }
        }
    }

    //-----------------additional methods-------------------
    private void createBead() {
        double angle = Util.angleBetween(mouseDownX, mouseDownY, mouseUpX, mouseUpY);
        if(isShiftDown) angle = Util.snapAngle(angle);
        dm.addBead(mouseDownX, mouseDownY, angle);
    }

    private void createBeads() {
        if(isShiftDown) {
            //with snap
            double angle = Util.snapAngle(Util.angleBetween(mouseDownX, mouseDownY, mouseUpX, mouseUpY));
            setSnapPos(mouseDownX,mouseDownY,mouseUpX,mouseUpY);
            int dx = (snapX - mouseDownX) / (numSelected - 1);
            int dy = (snapY - mouseDownY) / (numSelected - 1);
            for (int i = 0; i < numSelected; i++) {
                dm.addBead(mouseDownX + dx*i, mouseDownY + dy*i, angle);
            }
        } else {
            //without snap
            double angle = Util.angleBetween(mouseDownX, mouseDownY, mouseUpX, mouseUpY);
            int dx = (mouseUpX - mouseDownX) / (numSelected - 1);
            int dy = (mouseUpY - mouseDownY) / (numSelected - 1);
            for (int i = 0; i < numSelected; i++) {
                dm.addBead(mouseDownX + dx*i, mouseDownY + dy*i, angle);
            }
        }
    }

    private void moveSlider() {
        JBead b = dm.getBead(slider);
        if(slider > 0)
            b.moveUp(mouseX, mouseY);
        else
            b.moveDown(mouseX, mouseY);
    }

    private void moveEnd() {
        lineEnd.moveTo(mouseX, mouseY);
    }

    private void moveBead() {
        dm.getBead(grabbedBead).moveTo(mouseX, mouseY);
    }

    private void rotBead() {
        JBead b = dm.getBead(grabbedBead);
        b.rotateTo(Util.angleBetween(b.x, b.y, mouseX, mouseY) - tempAngle);
    }

    private void deleteBead(int bead) {
        if(bead > 0)
            dm.deleteBead(grabbedBead);
    }

    private void setSnapPos(int originX, int originY, int x, int y) {
        double angle = Util.angleBetween(originX, originY, x, y);
        double snapAngle = Util.snapAngle(angle);
        int dist = Util.rotX((int)Util.dist(originX, originY, x, y), angle-snapAngle);
        snapX = originX + Util.rotX(dist,snapAngle);
        snapY = originY + Util.rotY(dist,snapAngle);
    }
}
