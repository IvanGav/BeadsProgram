package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.Util;
import ivang.beadsdiagram.translate.JBead;
import ivang.beadsdiagram.translate.JManager;
import ivang.beadsdiagram.translate.JPoint;

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

    public UI (JManager dm) {
        super(dm);
    }

    public void mousePressed(int button) {
//        if(!isMouseInWorkspace) {
//            action = NO_ACTION;
//            slider = 0;
//            lineEnd = null;
//            grabbedBead = 0;
//            return;
//        } else {
        slider = strengthSlider();
        lineEnd = endGrabbed();
        grabbedBead = beadGrabbed();
        if(slider != 0)
            action = CHANGE_SLIDER;
        else if(lineEnd != null)
            action = LINE_END_MOVE;
        else if(grabbedBead != 0) {
            if(button == MOUSE_LEFT)
                action = MOVE_BEAD;
            else {
                action = ROTATE_BEAD;
                JBead b = dm.getBead(grabbedBead);
                tempAngle = Util.angleBetween(b.x, b.y, mouseX, mouseY) - b.getRot();
            }
        } else
            action = CREATE_BEAD;
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
        if(button == MOUSE_RIGHT && clickCount == 2) {
            deleteBead(grabbedBead);
        }
    }

    public void keyTyped(char key) {
        if(key > 48 && key < 58)
            numSelected = key - 48;
        if(key == 's') FileManager.saveFile("test", dm);
        if(key == 'n') FileManager.createFile("test");
        if(key == 'l') dm = FileManager.loadFile("test"); //no, wrong. dm is not going to be changed outside of ui class
    }

    //-----------------additional methods-------------------
    private void createBead() {
        double angle = Util.angleBetween(mouseDownX, mouseDownY, mouseUpX, mouseUpY);
        dm.createBead(mouseDownX, mouseDownY, angle);
    }

    private void createBeads() {
        double angle = Util.angleBetween(mouseDownX, mouseDownY, mouseUpX, mouseUpY);
        int dx = (mouseUpX - mouseDownX) / (numSelected - 1);
        int dy = (mouseUpY - mouseDownY) / (numSelected - 1);
        for (int i = 0; i < numSelected; i++) {
            dm.createBead(mouseDownX + dx*i, mouseDownY + dy*i, angle);
        }
    }

    private void moveSlider() {
        JBead b = dm.getBead(Math.abs(slider));
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
}
