//author: Kaimin Liu


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
	
	private int[][] gameBoard;
	private Random randomizer = new Random();
	private boolean ifMove = false;		//the variable to check if the array really moved
	private int ValidMoveCounter = 0;	//the counter to count the valid movement
	
	//constructor
	public Game() {
		gameBoard = new int[4][4];
	}
	
	int[][] getgameboard() {
		return this.gameBoard;	//the getting function
	}
	
	public void printArray() {		//to print out the array
		for(int i = 0; i < gameBoard.length; i ++) {
			for(int j = 0; j < gameBoard[i].length; j ++) {
				System.out.format("%6d", gameBoard[i][j]);
			}
			System.out.println();
		}
	}
	
	public void initializeNumber() {	//to initialized 2 random places in the array to be either 2(80% chance) or 4(%20 chance)
		
		int x1 = (int)(0+Math.random()*100);
		int y1 = (int)(0+Math.random()*100);
		if(x1<=79) {
			x1=2;
		}
		else {
			x1 = 4;
		}
		if(y1<=79) {
			y1=2;
		}
		else {
			y1 = 4;
		}
		int a1 = (int)(0+Math.random()*4);	//Separate the row and column for more random choice
		int b1 = (int)(0+Math.random()*4);
		int c1 = (int)(0+Math.random()*4);
		int d1 = (int)(0+Math.random()*4);
		while(c1==a1&&b1==d1) {
			c1 = (int)(0+Math.random()*4);
		} 
		gameBoard[a1][b1]= x1;
		gameBoard[c1][d1]= y1;
	}
	
	public void generator() { 		//to generator a random number (2 or 4 with certain probabilities)
		ArrayList<Integer[]> empty = new ArrayList<Integer[]>();
		if(ifBoardIsFull()) {		//if the board is full, do not generate
			return;
		}
		for(int i = 0; i < gameBoard.length; i ++ ) {
			for(int j = 0; j < gameBoard[i].length; j ++) {
				if(gameBoard[i][j] == 0) {
					Integer[] coord = {i, j};
					empty.add(coord);
				}
			}
		}
		int placeChoice = randomizer.nextInt(empty.size());
		int probability = randomizer.nextInt(9) ; //the prob will be 0-8
		int generatedNum = 0;
		if(probability <= 1) {
			generatedNum = 4; //if the prob is 0 or 1 out of (0-8), the new num is 4
		}					// in this way,we can make sure that the probability of 4 is 2/1-
		else {
			generatedNum = 2; //otherwise, generate number 2 (the probability of 2 is 8/10)
		}
			Integer[] newcoord = empty.get(placeChoice);
			gameBoard[newcoord[0]][newcoord[1]] = generatedNum;
	}
	
	//to add up and move the whole array up
	public void pushUp(){
		boolean[] isCombined= {false, false, false, false};	//if two elements are already combined in one movement
															//we don't combine it with other elements in the same one movement
		ifMove = false;					//check if the array is really changed					
		for(int col = 0; col < gameBoard[0].length; col ++) {
			for(int row = 1; row < gameBoard.length; row ++) {		//iterate through the array 
				if(gameBoard[row][col] != 0) {					//if the entry is not zero then check the element in next row
					int currVal = gameBoard[row][col];		//we record the value of this element
					int nextRow = row - 1;
					while(nextRow >= 0 && gameBoard[nextRow][col] == 0) {
						nextRow --;
					}
					if(nextRow == -1) {
						gameBoard[row][col] = 0;
						gameBoard[0][col] = currVal;
						ifMove = true;
					}
					else if(gameBoard[nextRow][col] == currVal) { //if they have the same value
						if(isCombined[nextRow]) {		//if they have been combined 
							gameBoard[row][col] = 0;		//assign it to zero to indicate it has been move
							gameBoard[nextRow + 1][col] = currVal;		//then we do not add then up, we place value of the current element to its next
						}
						else {
							gameBoard[row][col] = 0;
							gameBoard[nextRow][col] *= 2;		//if they have the same 
							isCombined[nextRow] = true;
						}
						for(boolean ele : isCombined) {
							if(ele == true) {
								ifMove = true;		//if any of the entries in the array is combined, then it means that the array is changed
							}
						}
					}
					else {
						gameBoard[row][col] = 0;
						gameBoard[nextRow + 1][col] = currVal;
						if(nextRow + 1 == row) {
							ifMove = false;			//in this case, the array is not really changed
						}
						else {
							ifMove = true;
						}
					}
				}
			}
		}
		if(ifMove == true) {
			ValidMoveCounter++;	//if there is change in this movement, we increase the counter by one
		}
	}
	//to add up and move the whole array down
	public void pushDown() {
		boolean[] isCombined= {false, false, false, false};
		ifMove = false;
		for(int col = 0; col < gameBoard[0].length; col ++) {
			for(int row = 2; row >= 0; row --) {
				if(gameBoard[row][col] != 0) {
					int currVal = gameBoard[row][col];
					int nextRow = row + 1;
					while(nextRow <= 3 && gameBoard[nextRow][col] == 0) {
						nextRow++;
					}
					if(nextRow == 4) {
						gameBoard[row][col] = 0;
						gameBoard[3][col] = currVal;
						ifMove = true;
					}
					else if(gameBoard[nextRow][col] != currVal) {
					    gameBoard[row][col] = 0;
						gameBoard[nextRow - 1][col] = currVal;
						if(nextRow - 1 == row) {
							ifMove = false;
						}
						else {
							ifMove = true;
						}
					}
					else {
						if(isCombined[nextRow]){
						    gameBoard[row][col] = 0;
							gameBoard[nextRow - 1][col] = currVal;
						}
						else {
							gameBoard[row][col] = 0;
							gameBoard[nextRow][col] *= 2;
							isCombined[nextRow] = true;
						}
						for(boolean ele : isCombined) {
							if(ele == true) {
								ifMove = true;
							}
						}
					}
				}
			}
		}	  
		if(ifMove == true) {
			ValidMoveCounter++;
		}
	}
	//to add up and move the whole array right
	public void pushRight() {
		boolean[] isCombined= {false, false, false, false};
		ifMove = false;
		for(int row = 0; row < gameBoard.length; row++) {
			for(int col = 2; col > -1; col--) {
				if(gameBoard[row][col]!=0) {
					int currVal = gameBoard[row][col];
					int nextCol = col + 1;
					while(nextCol <= 3 && gameBoard[row][nextCol]==0) {
						nextCol++;
					}
					if(nextCol == 4) {
					    gameBoard[row][col] = 0;
						gameBoard[row][3] = currVal;
						ifMove = true;
					}
					else if(gameBoard[row][nextCol] == currVal) {
						if(isCombined[nextCol]) {
							gameBoard[row][col] = 0;
							gameBoard[row][nextCol - 1] = currVal;
						}
						else {
							gameBoard[row][col] = 0;
							gameBoard[row][nextCol] *= 2;
							isCombined[nextCol] = true;
						}
						for(boolean ele : isCombined) {
							if(ele == true) {
								ifMove = true;
							}
						}
					}
					else{
						gameBoard[row][col] = 0;
						gameBoard[row][nextCol - 1] = currVal;
						if(nextCol - 1 == col) {
							ifMove = false;
						}
						else {
							ifMove = true;
						}
					}
				}
			}
		}
		if(ifMove == true) {
			ValidMoveCounter++;
		}
	}	

	//to add up and move the whole array left
	public void pushLeft() {
		boolean[] isCombined= {false, false, false, false};
		ifMove = false;
		for(int row = 0; row < gameBoard.length; row ++) {
			for(int col = 1; col < gameBoard[row].length; col ++) {
				if(gameBoard[row][col] != 0) {
					int currVal = gameBoard[row][col];
					int nextCol = col - 1;
					while(nextCol >= 0 && gameBoard[row][nextCol] == 0) {
						nextCol --;
					}
					if(nextCol == -1) {
						gameBoard[row][col] = 0;
						gameBoard[row][0] = currVal;
						ifMove = true;
					}
					else if(gameBoard[row][nextCol] == currVal) {
						if(isCombined[nextCol]) {
							gameBoard[row][col] = 0;
							gameBoard[row][nextCol + 1] = currVal;
						}
						else {
							gameBoard[row][col] = 0;
							gameBoard[row][nextCol] *= 2;
							isCombined[nextCol] = true;
						}
						for(boolean ele : isCombined) {
							if(ele == true) {
								ifMove = true;
							}
						}
					}
					else {
						gameBoard[row][col] = 0;
						gameBoard[row][nextCol + 1] = currVal;
						if(nextCol + 1 == col) {
							ifMove = false;
						}
						else {
							ifMove = true;
						}
					}
				}
			}
		}
		if(ifMove == true) {
			ValidMoveCounter++;
		}
	}
	
	public boolean checkif2048() {		//check if any entry reaches 2048
		for(int row = 0; row < gameBoard.length; row ++) {
			for(int col = 0; col < gameBoard[row].length; col ++) {
				if(gameBoard[row][col] == 2048) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean ifBoardIsFull() {  //to check if the board has no place for new numbers
		for(int row = 0; row < gameBoard.length; row ++) {
			for(int col = 0; col < gameBoard[row].length; col ++) {
				if(gameBoard[row][col] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	//check if there can be available moves when the board is full
	public boolean checkifCanMove() {
		for(int row = 0; row < gameBoard.length; row ++) {
			for(int col = 0; col < gameBoard[row].length; col ++) {
				if(row == 0) {
					if(row == 0 && col != 0 && gameBoard[row][col] == gameBoard[row][col - 1])
						return true;
				}
				else {
					if(col != 0 && gameBoard[row][col] == gameBoard[row][col - 1]) {
						return true;
					}
					if(gameBoard[row][col] == gameBoard[row - 1][col]) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int checkIfGameOver() { //denote WIN as 0; CONTINUE as 1; LOSE as 2
		int gamestate = -1; //default it as -1
		if(checkif2048()) {
			gamestate = 0;
		}
		else if(ifBoardIsFull()) {
			if(checkifCanMove()) {
				gamestate = 1;
			}
			else {
				gamestate = 2;
			}
		}
		else {
			gamestate = 1; // if there is no 2048 on the gameBoard and it is not full, then continue
		}
		return gamestate;
	}
	
	//need to clear the screen if user wants to restart
	public void clearTheBoard() {
		for(int[] row : gameBoard) {
			Arrays.fill(row, 0);  //reassign all the entries to 0
		}
	}
	
	public boolean getValidMove() {
		return ifMove;
	}
	
	public boolean ifValidMove() {
		if(getValidMove()) {
			return true;
		}
		else
			return false;
	}
	
	public int findMax() {		//iterate the array to find the max number
		int max = gameBoard[0][0];
		for(int row = 0; row < gameBoard.length; row ++) {
			for(int col = 0; col < gameBoard[row].length; col ++) {
				if(max < gameBoard[row][col]) {
					max = gameBoard[row][col];
				}
			}
		}
		return max;
	}
	
	public int getMoveNumber() {		//to get the private variable from other class
		return ValidMoveCounter;
	}
	
	public void setCounter(int val) {		//for us to reset the counter
		ValidMoveCounter = val;
	}
}
