package Menu;


import frame.Main;

import javax.swing.*;

public class myMenuBar extends JMenuBar {
    protected JPanel basePanel;
    FileMenu fileMenu;
    //EditMenu editMenu = new EditMenu("Edit");
    ViewMenu viewMenu;

    public myMenuBar(JPanel main) {
        this.basePanel = main;

        fileMenu  = new FileMenu("File", (Main) main);
        //editMenu = new EditMenu("Edit");
        viewMenu = new ViewMenu("View");
    }
}

