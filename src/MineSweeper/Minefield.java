/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MineSweeper;

public class Minefield {
	private MineTile[][] minefield;

	private int rows, columns;
        
        /**
         * Create an empty minefield of the required size
         * @param rows
         * @param columns 
         */
	public Minefield(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		minefield = new MineTile[rows][columns];
		// We must fill out the array with objects this time, as opposed to arrays
		// of primitive values.
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				minefield[i][j] = new MineTile();
			}
		}
	}

        /**
         * 
         * @return how many rows
         */
	public int getRows() {
		return rows;
	}

        /**
         * 
         * @return how many columns
         */
	public int getColumns() {
		return columns;
	}

        /**
         * Mine a tile
         * @param row
         * @param column
         * @return false if tile already mined, true if successful
         */
	public boolean mineTile(int row, int column) {
            if(minefield[row][column].isMined()) {
                return false;
            }
		minefield[row][column].toggleMined();
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (i >= 0 && j >= 0 && i < minefield.length
						&& j < minefield[i].length) {
					minefield[i][j].incMinedNeighbours();
				}

			}
		}
                return false;
	}

        /**
         * 
         * @return the minefield with tiles hidden
         */    
	public String toString() {
		String s = "";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				s += minefield[i][j];

			}
			s += "\n";
		}

		return s;
	}
        
        /**
         * 
         * @return the minefield with everything on show
         */
        public String toStringRevealed() {
        String s = "";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				s += minefield[i][j].toStringRevealed();

			}
			s += "\n";
		}

		return s;
}
        /**
         * COnvenience method to print out to the console
         */
	public void printMinefield() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(minefield[i][j]);
			}
			System.out.println();
		}
	}

        /**
         * Populate the minefield randomly to num mines
         * @param num 
         */
	public void populate(int num) {
		int created = 0;
		while (created < num) {
			int row = (int) (Math.random() * this.rows);
			int col = (int) (Math.random() * this.columns);
			if (!minefield[row][col].isMined() && !(row == 0 && col == 0)) {
				mineTile(row, col);
				created++;
			}

		}
	}


        /**
         * Flag a given tile
         * @param row
         * @param column 
         */
	public void mark(int row, int column) {
		this.minefield[row][column].toggleMarked();

	}
        
        public void unmark(int row, int column) {
		this.minefield[row][column].toggleMarked();

	}

	private void checkReveal(int row, int column) {
		// walk round the outside of the current tile
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				// Now that you've seen one approach to creating bounds in v1,
				// here we roll the check on bounds inside the loop
				if (i >= 0 && j >= 0 && i < minefield.length
						&& j < minefield[i].length) {
					// Not the one just shown...
					if (!(i == row && j == column)) {
						// If its not already reveaed - recursion termination condition
						if (!minefield[i][j].isRevealed()) {
							// Note that this is the check on ending the recursion
							minefield[i][j].setRevealed(true);
							if (minefield[i][j].getMinedNeighbours() == 0) {
								// recurse around here to see what also needs to be revealed
								checkReveal(i, j);
							}
						}
					}
				}
			}
		}

	}

        /**
         * Step on a given tile
         * @param row
         * @param column
         * @return false if the world explodes
         */
	public boolean step(int row, int column) {
		if (row >= 0 && row < getRows() && column >= 0 && column < getColumns()) {
			if (minefield[row][column].isMined()) {
				// The BOOM return
				return false;
			} else {
				// Otherwise reveal...
				minefield[row][column].setRevealed(true);
				// ...and show any other tiles needed
				if (minefield[row][column].getMinedNeighbours() == 0) {
					checkReveal(row, column);
				}
			}

		}
		return true;

	}

        /**
         * 
         * @return Has everything been correctly flagged
         */
	public boolean areAllMinesFound() {
		// Check all tiles for mined tiles marked and only mined tiles marked
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				if ((minefield[i][j].isMined() && !minefield[i][j].isMarked())
						|| (!minefield[i][j].isMined() && minefield[i][j]
								.isMarked())) {
					return false;
				}
			}
		}
		return true;
	}

        /**
         * 
         * @param row
         * @param col
         * @return the desired tile
         */
	public MineTile getMineTile(int row, int col) {
		return minefield[row][col];
	}

}