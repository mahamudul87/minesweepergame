/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineSweeper;

/**
 * @author ianw The refactored representation of a tile in the minefield. Note
 * that we use the unlisted no argument constructor, since we initialise all
 * fields st declaration.
 */
public class MineTile {

    private boolean marked = false;
    private int minedNeighbours = 0;
    private boolean mined = false;
    private boolean revealed = false;

    /**
     *
     * @return Whether the contents of the tile are visible or not
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Switch the current visibility state of the tile
     */
    public void toggleRevealed() {
        this.revealed = !this.revealed;
    }

    /**
     *
     * @return Whether the tile is flagged or not
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * Toggle the state of the flag
     */
    public void toggleMarked() {
        this.marked = !this.marked;
    }

    /**
     *
     * @return How many neighbour tiles are mined
     */
    public int getMinedNeighbours() {
        return minedNeighbours;
    }

    /**
     * Increment the number of mined neighbours
     */
    public void incMinedNeighbours() {
        this.minedNeighbours++;
    }

    /**
     *
     * @return true for a mine
     */
    public boolean isMined() {
        return mined;
    }

    /**
     * Toggle the presence of a mine
     */
    public void toggleMined() {
        this.mined = !this.mined;
    }

    /**
     * 
     * @return Standard representation, hiding unrevealed tiles
     */
    public String toString() {
        // As per specification, show the current state of the tile
        if (revealed) {
            if (mined) {
                return "*";
            } else {
                return "" + minedNeighbours;
            }
        } else {
            if (marked) {
                return "@";
            } else {
                return ".";
            }
        }
    }

    /**
     * 
     * @return Showing state assuming all tiles revealed
     */
    public String toStringRevealed() {
        if (mined) {
            return "*";
        } else {
            return "" + minedNeighbours;
        }
    }

    /**
     * Change the visibility of the tile
     * @param revealed
     */
    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

}
