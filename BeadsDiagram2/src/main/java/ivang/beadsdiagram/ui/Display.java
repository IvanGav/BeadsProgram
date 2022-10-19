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
//            dm.addLine(new int[]{1, 3, 4}, new int[]{5, 5, 5});
//            dm.addLine(new int[]{2, 3, 5}, new int[]{-5, -5, -5});
        } else {
            dm = FileManager.loadFile("test");
        }

        Drawer dr = new Drawer(dm);
        dr.start();
    }
}
