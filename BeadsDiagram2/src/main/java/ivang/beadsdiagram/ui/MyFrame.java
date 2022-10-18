package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    An actual frame. Contains MyPanel which draws a diagram on itself.
    It's made this way so that the top bar with close, maximise, minimize does not count towards the drawing area.
 */
public class MyFrame extends JFrame {
    public MyFrame(JManager dm) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel); //put a panel to this frame, which will hold all other elements.

        JPanel ui = new UI(dm);
        panel.add(ui); //the main panel that you draw stuff on

        JPanel bottomPanel = new JPanel();
        panel.add(bottomPanel); //bottom panel holds all other ui - buttons, dropdowns and stuff

        JButton b = new JButton("Create");
        b.setPreferredSize(new Dimension(100,50));
        b.setBorderPainted(true);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.addActionListener(e -> dm.createBead(200,200,0));
        bottomPanel.add(b);

        JButton b2 = new JButton("-");
        b2.addActionListener(e -> {
            if(dm.numBeads() > 0)
                dm.deleteBead(1);
        });
        bottomPanel.add(b2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setTitle("Testing in process");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
