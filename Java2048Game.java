//author: Kaimin Liu

import java.util.Scanner;

public class Java2048Game {
	
	private static int direction = 0;

	public static void main(String[] args) {
		Scanner keyboardInput = new Scanner(System.in);
		System.out.println("Do you want to start the game? Press y to start, q to quit");
		String startInput = keyboardInput.nextLine();     //take user input
		while(!startInput.contentEquals("y") && !startInput.contentEquals("q")){
			startInput = keyboardInput.next("Invaild input! Please input again!");
		}
		if(startInput.equals("q")) {      //ask user to confirm
			System.out.println("Are you sure that you want to QUIT? Press y to confirm, n to change your mind");
			startInput = keyboardInput.nextLine();
			if(startInput.equals("y")) {
				return;   //quit the program
			}
		}
		
		Game game = new Game();
		game.initializeNumber();  //initialize gameboard
		game.printArray();		//printing out the board
		System.out.println("Press w,s,a,d to move up down left and right");  	//movement instructions
		startTheGame(game);		//start the game
		
	}

	public static void startTheGame(Game game) {
		Scanner keyboardInput = new Scanner(System.in);
		String startInput = null;		
		String directionInput = keyboardInput.next(); //take user's direction
		
		keyboardInput.nextLine();
		
		if(directionInput.equals("q")) {		//if user input q, we double check with user
				System.out.println("Are you sure that you want to QUIT? Press y to confirm, n to change your mind");
				startInput = keyboardInput.nextLine();
				if(startInput.equals("y")) {
					System.exit(0);		//exit the program if quit
				}
		}
		if(directionInput.equals("r")) {	//if user input r, we double check with user
			System.out.println("Are you sure that you want to RESTART? Press y to confirm, n to continue");
			String newInput = keyboardInput.nextLine();
			if(newInput.equals("y")) {
				System.out.println("restarting...");
				game.clearTheBoard();	//clear the board
				game.initializeNumber();	//initialize number again
				refreshScreen();	
				game.setCounter(0);		//reset the counter to 0
				game.printArray();
				System.out.println("the Maximum number is " + game.findMax());
				System.out.println("the number of valid move is " + game.getMoveNumber());
				
				startTheGame(game);		//restart the game
			}
		}
		while(!checkVaildInputDirc(directionInput) && !directionInput.equals("q") && !directionInput.equals("r")) {
																	//if user's input movement is not w,s,a,d, q, r
			System.out.println("Invaild input! Please input again!"); 
			directionInput = keyboardInput.next();					//ask user to input again until we get the valid input
		}
		set(directionInput); 				//set the direction
		upDateBoard(game, getDirection());	//update the game board by moving the entries to the corresponding direction
		if(game.ifValidMove()) {
			game.generator();		//only if the move is valid can we generate a random number
		}
		refreshScreen();
		game.printArray();
		System.out.println("the Maximum number is " + game.findMax());
		System.out.println("the number of valid move is " + game.getMoveNumber());
		while(!directionInput.equals("q") && game.checkIfGameOver() == 1 || game.checkIfGameOver() == 2 || game.checkIfGameOver() == 0) {
			
			
			if(game.checkIfGameOver() == 1) {     //when a game can be continued
				startTheGame(game);
			}
			
			else if(game.checkIfGameOver() == 2) {    //if lose
				System.out.println("Sorry you lose. Do you want to play again?");  //the case of quiting
				System.out.println("Press 'r' to restart and 'q' to quit");
				startInput = keyboardInput.nextLine();
				if(startInput.equals("q")) {
					System.out.println("Are you sure that you want to QUIT? Press y to confirm, n to change your mind");
					String newInput = keyboardInput.nextLine();
					if(newInput.equals("y")) {
						System.exit(0);
					}
				}
				if(startInput.equals("r")) {		//the case of restarting
					System.out.println("Are you sure that you want to RESTART? Press y to confirm, n to continue");
					String newInput = keyboardInput.next();
					keyboardInput.nextLine();
					if(newInput.equals("y")) {
						System.out.println("restarting...");
						game.clearTheBoard();
						game.initializeNumber();
						refreshScreen();
						game.setCounter(0);
						game.printArray();
						System.out.println("the Maximum number is " + game.findMax());
						System.out.println("the number of valid move is " + game.getMoveNumber());
						startTheGame(game);
					}
				}
				while(!startInput.contentEquals("y") && !startInput.contentEquals("q") && !startInput.contentEquals("n")){
					startInput = keyboardInput.next("Invaild input! Please input again!");		//the case of other inputs
				}
				
			}
			
			else {         //if win
				System.out.println("Congras! You Win! Do you want to continue?");
				System.out.println("Press y to continue, q to quit");
				startInput = keyboardInput.nextLine();
				while(!startInput.contentEquals("y") && !startInput.contentEquals("q")){
					startInput = keyboardInput.next("Invaild input! Please input again!");
				}
				if(startInput.contentEquals("y")) {
					startTheGame(game);
				}
				else {
					if(startInput.equals("q")) {
						System.out.println("Are you sure that you want to QUIT? Press y to confirm, n to change your mind");
						String newInput = keyboardInput.nextLine();
						if(newInput.equals("y")) {
							System.exit(0);
						}
					}
					if(startInput.equals("r")) {
						System.out.println("Are you sure that you want to RESTART? Press y to confirm, n to continue");
						String newInput = keyboardInput.next();
						keyboardInput.nextLine();
						if(newInput.equals("y")) {
							System.out.println("restarting...");
							game.clearTheBoard();
							game.initializeNumber();
							refreshScreen();
							game.printArray();
							game.printArray();
							System.out.println("the Maximum number is " + game.findMax());
							System.out.println("the number of valid move is " + game.getMoveNumber());
							startTheGame(game);
						}
					}
				}
			}
		}

	}
	
	//to set the direction
	public static void set(String input) {
		if(input.equals("a")){ //if left pressed
			setDirection(1);
		}
		if(input.equals("d")){//if right pressed
			setDirection(2);
		}
		if(input.equals("w")){//if up pressed
			setDirection(3);
		}
		if(input.equals("s")){//if down pressed
			setDirection(4);
		}
	}
	
	//check if a input is "w","s", "a","d"
	public static boolean checkVaildInputDirc(String direction) {
		if(direction.equals("w") || direction.equals("s") || direction.equals("a") || direction.equals("d")) {
			return true;
		}
		return false;
	}

	//get the private variable direction 
	public static int getDirection() {
		return Java2048Game.direction;
	}
	
	//denote the direction as some integers
	public static void setDirection(int direction) {
		Java2048Game.direction = direction;
	}
	
	//update the board with instructed movements
	public static void upDateBoard(Game game, int direction) {
		if(direction == 3) {
			game.pushUp();
		}
		else if(direction == 4) {
			game.pushDown();
		}
		else if(direction == 1) {
			game.pushLeft();
		}
		else {
			game.pushRight();
		}
	}
	
	//printing out 9 empty lines to refresh the screen
	public static void refreshScreen() { 
		for(int i = 0; i < 9; i ++) {
			System.out.println();
		}
	}
	
	
	
}
