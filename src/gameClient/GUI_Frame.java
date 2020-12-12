package gameClient;

import api.*;

import javax.swing.*;
import java.util.Random;

public class GUI_Frame extends JFrame {



    public GUI_Frame(Arena ar){
        super();
        GUI gui=new GUI();
        this.add(gui);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        gui.update(ar);

    }


}
