package ivang.beadsdiagram.ui;

import ivang.beadsdiagram.translate.JManager;

/*
    Create/load a diagram and call to Drawer.
 */
public class Display  {
    public static void main(String[] args) {
        JManager dm;
        if(true) {
            dm = new JManager();
        } else {
            dm = FileManager.loadFile("test");
        }

        Drawer dr = new Drawer(dm);
        dr.start();
    }
}
