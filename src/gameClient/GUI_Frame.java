package gameClient;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * This Class represents the frame on which the game operates.
 */
public class GUI_Frame extends JFrame {


    /**
     * Create our game based on the arena.
     * @param ar
     */
    public GUI_Frame(Arena ar){
        super();
        GUI gui=new GUI();
        this.add(gui);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1400,800);
        //this.setLayout(new BorderLayout());
        //pack();
        gui.update(ar);


    }


}
