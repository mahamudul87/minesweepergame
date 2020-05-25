/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineSweeper;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * The simple text version of the minesweeper game, using the parse and commands
 *
 * @author ianw
 */
public class Minesweeper {

    public static final int DEFAULTROWS = 5;
    public static final int DEFAULTCOLS = 5;
    public static final int DEFAULTMINES = 5;
    public static Minefield mines;
    private Parser parser;
    private static GameButton[][] buttons;
    public static Minesweeper mineSweeper;
    private static JFrame window;
    private boolean isGameOVer=false;

    public boolean isIsGameOVer() {
        return isGameOVer;
    }
    /**
     * Create the desired size of game
     *
     * @param numMines
     * @param rows
     * @param columns
     */
    public Minesweeper(int numMines, int rows, int columns) {
        this.mines = new Minefield(rows, columns);
        this.mines.populate(numMines);
        this.prepareGUI(rows, columns);
        mineSweeper = this;
    }

    public void reDisplayBoard() {
        for (int r = 0; r < mines.getRows(); r++) {
            for (int c = 0; c < mines.getColumns(); c++) {
                MineTile mineTile = mines.getMineTile(r, c);
                if (mineTile.isRevealed()) {
                    if (mineTile.toStringRevealed().equals("0")) {
                        buttons[r][c].setText("");
                    } else {
                        buttons[r][c].setText(mineTile.toStringRevealed());
                    }
                    buttons[r][c].setEnabled(false);
                } else if (mineTile.isMarked()) {
                    buttons[r][c].setText("");
                    buttons[r][c].setIcon(GameIcon.getFlagIcon());
                    buttons[r][c].setEnabled(false);
                }
            }
        }
    }
    public void disableBoard(){
     for (int r = 0; r < mines.getRows(); r++) {
            for (int c = 0; c < mines.getColumns(); c++) {
              buttons[r][c].setEnabled(false);
            }   
     }
     this.isGameOVer=true;
    }
    public void prepareGUI(int rows, int columns) {
       window = new JFrame("Minesweeper");
        window.setLayout(new GridLayout(rows, columns));
        buttons = new GameButton[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                buttons[r][c] = new GameButton(r, c);
                window.add(buttons[r][c]);
            }
        }
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exitProgram = new JMenuItem("Exit");
        fileMenu.add(newGame);
        fileMenu.add(exitProgram);
        menubar.add(fileMenu);
        window.setJMenuBar(menubar);
        window.setSize(400, 400);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String noOfRows = JOptionPane.showInputDialog("Enter Number of Rows: ");
                String noOfCol = JOptionPane.showInputDialog("Enter Number of Columns: ");
                String noOfMine = JOptionPane.showInputDialog("Enter Number of Mines: ");
                try {
                    int r = Integer.parseInt(noOfRows);
                    int c = Integer.parseInt(noOfCol);
                    int m = Integer.parseInt(noOfMine);
                    mineSweeper.mines = new Minefield(r, c);
                    mineSweeper.mines.populate(m);
                    window.dispose();
                    mineSweeper.isGameOVer=false;
                    mineSweeper.prepareGUI(r, c);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Data Entered!");
                }
            }
        });
        exitProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }

    /**
     * Create and populate a minefield with default sizes
     */
    public Minesweeper() {
        this(DEFAULTMINES, DEFAULTROWS, DEFAULTCOLS);
    }

    /**
     * Start a game on the console
     *
     * @param args
     */
    public static void main(String[] args) {
        Minesweeper game = new Minesweeper();
        game.commandLine();

    }

    public void printPrompt(String msg) {
        System.out.print(this.mines);
        System.out.println(msg);
        System.out.print("> ");
    }

    private boolean legalBounds(Command c) {
        return c.getRow() >= 0 && c.getRow() < mines.getRows() && c.getColumn() >= 0
                && c.getColumn() < mines.getColumns();
    }

    public int execute(Command c) {
        // As in the example code from the lecture, we use the enum CommandWord to 
        // decide on action
        switch (c.getCommand()) {
            case MARK: {
                // We have incorporated a legality check here, guaranteeing we always use
                // legal values.  
                if (legalBounds(c)) {
                    mines.mark(c.getRow(), c.getColumn());
                } else {
                    System.out.println("Row " + c.getRow() + " must be 0 or more and less than "
                            + mines.getRows() + " and columns " + c.getColumn()
                            + " must be 0 or more and less than " + mines.getColumns());
                }
                break;
            }
            case UNMARK: {
                // We have incorporated a legality check here, guaranteeing we always use
                // legal values.  
                if (legalBounds(c)) {
                    mines.unmark(c.getRow(), c.getColumn());
                } else {
                    System.out.println("Row " + c.getRow() + " must be 0 or more and less than "
                            + mines.getRows() + " and columns " + c.getColumn()
                            + " must be 0 or more and less than " + mines.getColumns());
                }
                break;
            }
            case STEP: {
                if (legalBounds(c)) {
                    if (!mines.step(c.getRow(), c.getColumn())) {
                        System.out.println("BOOM!!!");
                        return -1;
                    }
                } else {
                    // Duplicate from the line above - perhaps this should be factored out
                    System.out.println("Row " + c.getRow() + " must be 0 or more and less than "
                            + mines.getRows() + " and columns " + c.getColumn()
                            + " must be 0 or more and less than " + mines.getColumns());

                }
                break;
            }
        }
        return 1;
    }

    private void commandLine() {
        printPrompt("New game");
        /*
        this.parser = new Parser();
        Command c = parser.getCommand();
        while (c.getCommand() != CommandWord.QUIT) {
            System.out.println(c);
            execute(c);
            if (mines.areAllMinesFound()) {
                System.out.print("Hurrah!! All the mines are found.");
                System.exit(0);
            }
            printPrompt(c.getMsg());
            c = parser.getCommand();
        }
         */
    }

}
