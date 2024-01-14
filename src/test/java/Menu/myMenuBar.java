package Menu;


import javax.swing.*;

public class myMenuBar extends JMenuBar {
    protected JPanel basePanel;
    FileMenu fileMenu;
    //EditMenu editMenu = new EditMenu("Edit");
    ViewMenu viewMenu;

    public myMenuBar(JPanel main) {
        this.basePanel = main;

        fileMenu  = new FileMenu("File",basePanel);
        //editMenu = new EditMenu("Edit");
        viewMenu = new ViewMenu("View");
    }
}

