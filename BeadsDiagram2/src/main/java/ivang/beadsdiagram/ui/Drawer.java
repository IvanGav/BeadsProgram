package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/*
    Create MyFrame and limit frame rate using Timer.
 */
public class Drawer implements ActionListener {

    public Window frame;
    public Timer tim;

    public Drawer(JManager dm) {
        frame = new Window(dm);
        tim = new Timer(1000/60,this);
    }

    //start to draw a frame
    public void start() {
        tim.start();
    }

    //close a frame
    public void close() {
        stop();
        frame.dispose();
    }

    public void stop() {
        tim.stop();
    }

    public void actionPerformed(ActionEvent e) {
        frame.repaint();
    }
}
