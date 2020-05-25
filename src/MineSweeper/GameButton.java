/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineSweeper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author mahamudul.hasan
 */
public class GameButton extends JButton implements MouseListener {

    private boolean pressed;
    private boolean flagStatus = false;
    private int row;
    private int col;

    public GameButton(int r, int c) {
        addMouseListener(this);
        this.row = r;
        this.col = c;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.getModel().setArmed(false);
        this.getModel().setPressed(false);
        Command c;
        if (pressed) {
            if(Minesweeper.mineSweeper.isIsGameOVer()){
                JOptionPane.showMessageDialog(this, "Game Over! Please start new game.");
                return;
            }
            if (SwingUtilities.isRightMouseButton(e)) {

                if (flagStatus) {
                    this.setIcon(null);
                    flagStatus = false;
                    this.setEnabled(true);
                    c = new Command(CommandWord.UNMARK, this.row, this.col);
                    Minesweeper.mineSweeper.execute(c);
                    Minesweeper.mineSweeper.printPrompt(c.getMsg());
                    Minesweeper.mineSweeper.reDisplayBoard();
                    if (Minesweeper.mineSweeper.mines.areAllMinesFound()) {
                        System.out.print("Hurrah!! All the mines are found.");
                        JOptionPane.showMessageDialog(this, "Hurrah!! All the mines are found.");
                    }
                } else {
                    this.setIcon(GameIcon.getFlagIcon());
                    flagStatus = true;
                    c = new Command(CommandWord.MARK, this.row, this.col);
                    Minesweeper.mineSweeper.execute(c);
                    Minesweeper.mineSweeper.printPrompt(c.getMsg());
                    Minesweeper.mineSweeper.reDisplayBoard();
                    if (Minesweeper.mineSweeper.mines.areAllMinesFound()) {
                        System.out.print("Hurrah!! All the mines are found.");
                        JOptionPane.showMessageDialog(this, "Hurrah!! All the mines are found.");
                    }
                }

            } else if (this.isEnabled()) {//left mouse button
                c = new Command(CommandWord.STEP, this.row, this.col);
                int r = Minesweeper.mineSweeper.execute(c);
                Minesweeper.mineSweeper.printPrompt(c.getMsg());
                Minesweeper.mineSweeper.reDisplayBoard();
                if (r == -1) {
                    this.setIcon(GameIcon.getMineIcon());
                    JOptionPane.showMessageDialog(this, "Sorry!! BOOM!!.");
                    Minesweeper.mineSweeper.disableBoard();
                } else if (Minesweeper.mineSweeper.mines.areAllMinesFound()) {
                    System.out.print("Hurrah!! All the mines are found.");
                    JOptionPane.showMessageDialog(this, "Hurrah!! All the mines are found.");
                }
            }
        }
        pressed = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.getModel().setArmed(true);
        this.getModel().setPressed(true);
        pressed = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        pressed = false;
    }

}
