package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JManager;

import javax.swing.*;

/*
    An actual frame. Contains MyPanel which draws a diagram on itself.
    It's made this way so that the top bar with close, maximise, minimize does not count towards the drawing area.
 */
public class MyFrame extends JFrame {
    public MyFrame(JManager dm) {
        JPanel panel = new UI(dm);
        //TextField tf = new TextField(10);
        //this.add(tf);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setTitle("Testing in process");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
