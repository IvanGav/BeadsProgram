package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
    An actual frame. Contains Workspace which draws a diagram on itself.
    It's made this way so that the top bar with close, maximise, minimize does not count towards the drawing area.
 */
public class Window extends JFrame {
    public Window(JManager dm) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel); //put a panel to this frame, which will hold all other elements.

        UI ui = new UI(dm);
        Layout layout = new Layout(dm);

        panel.add(ui); //the main panel that you draw stuff on
        panel.add(layout); //bottom panel holds all other ui - buttons, dropdowns and stuff

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setTitle("Testing in process");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setFocusable(true);
    }
}
