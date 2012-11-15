import java.util.Scanner;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String board[][] = new String[6][7];
		board = InitializeBoard(board);
		while(board[0][0]!="Q")
			board = TryMove(board);
	}
	
	//get user input and return the changed(or unchanged) board
	public static String[][] TryMove(String[][] board){
		Scanner in = new Scanner(System.in); //Get user input
		String piece = "";
		String direction = "";
		PrintBoard(board);
		System.out.println("Piece to move: (A B C D E F G H I QUIT or CLEAR)");//To attempt a move, type something like \"A Down\" (a, b, c, d, e, f, g, h, i, j)(up down left right), QUIT to quit or CLEAR to clear the board");
		//Read and Format both inputs, check first for clear or quit
		piece = in.next();
		piece = piece.toUpperCase();
		if(piece.equals("CLEAR")){
			board = InitializeBoard(board);
			//in.close(); //had troubles reopening it on the next attempt...
			return board;
		}
		else if(piece.equals("QUIT")){
			in.close();
			board[0][0]="Q";
			return board;
		}
		System.out.println("Direction: (UP DOWN LEFT or RIGHT)");
		direction = in.next();
		direction = direction.toUpperCase();
		
		//check for invalid piece
		if(!(piece.equals("A") || piece.equals("B") || piece.equals("C") || piece.equals("D") || piece.equals("E") || piece.equals("F") || piece.equals("G") || piece.equals("H") || piece.equals("I") || piece.equals("J"))){
			System.out.println("invalid piece");
			//in.close(); //troubles reopening
			return board;//return unchanged board
		}
		Integer[][] emptyLoc = FindEmpty(board);//get the locations of the empty pieces
		
		//Check the opposite direction of the empty pieces for the location of the piece the
		//user has claimed to move. If it is not the same piece, it is an invalid move, return
		//the original board. Otherwise, break out of the switch and execute the move.
		switch (direction){
		case "UP":
			if(board[emptyLoc[0][0]+1][emptyLoc[0][1]].charAt(0)!=piece.charAt(0)
			&& board[emptyLoc[1][0]+1][emptyLoc[1][1]].charAt(0)!=piece.charAt(0)){//check below
			
				System.out.println("Invalid Move");
				//in.close(); //troubles reopening 
				return board;
			}
			break;//if it is the piece, break to continue
		case "DOWN":
			if(board[emptyLoc[0][0]-1][emptyLoc[0][1]].charAt(0)!=piece.charAt(0)
			&& board[emptyLoc[1][0]-1][emptyLoc[1][1]].charAt(0)!=piece.charAt(0)){//check above
				System.out.println("Invalid Move");
				//in.close(); //troubles reopening
				return board;
			}
			break;//if it is the piece, break to continue
		case "LEFT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]+1].charAt(0)!=piece.charAt(0)
			&& board[emptyLoc[1][0]][emptyLoc[1][1]+1].charAt(0)!=piece.charAt(0)){//check right
				System.out.println("Invalid Move");
				//in.close(); //troubles reopening
				return board;
			}
			break;//if it is the piece, break to continue
		case "RIGHT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]-1].charAt(0)!=piece.charAt(0)
			&& board[emptyLoc[1][0]][emptyLoc[1][1]-1].charAt(0)!=piece.charAt(0)){//check left
				System.out.println("Invalid Move");
				//in.close(); //troubles reopening
				return board;
			}
			break;//if it is the piece, break to continue
		}
		
		//We now know we have a (almost)valid move and a valid piece
		if(piece.equals("A")||piece.equals("B")||piece.equals("C")||piece.equals("D"))
			board = ExecuteTwobyOne(direction, piece, board, emptyLoc);
		else if(piece.equals("E"))
			board = ExecuteOnebyTwo(direction, piece, board, emptyLoc);
		else if(piece.equals("F")||piece.equals("G")||piece.equals("H")||piece.equals("I"))
			board = ExecuteOnebyOne(direction, piece, board, emptyLoc);
		else if(piece.equals("J"))
			board = ExecuteTwobyTwo(direction, piece, board, emptyLoc);
		
		//in.close(); //troubles reopening
		return board;
	}
	
	//find the coordinates of the empty spaces on the board and store them in a two-dimensional
	//array of Integers. Stores coordinates as emptyLoc[x][y] (emptyLoc[0] is the first empty spot
	//and emptyLoc[1] is the second empty spot), which is inverted from how arrays work,
	//so the moves based on the empty coordinates can look confusing and inverted...
	public static Integer[][] FindEmpty(String[][] board){
		Integer[][] emptyLoc = {{-1, -1},{-1, -1}};
		for(int i=0; i<=5; i++){
			for(int j=0; j<=6; j++){
				if(board[i][j].charAt(0)=='e')//empty found
					if(emptyLoc[0][0]==-1){//first empty
						emptyLoc[0][0]=i;
						emptyLoc[0][1]=j;
					}
					else{//second empty
						emptyLoc[1][0]=i;
						emptyLoc[1][1]=j;
					}
			}
		}
		return emptyLoc;
	}

	//create the initial board layout
	public static String[][] InitializeBoard(String[][] board){
		for(int i=0;i<=6;i++)//Horizontal Border
			board[0][i] = board[5][i] = "X";
		for(int i=0;i<=5;i++)//Vertical Border
			board[i][0] = board[i][6] = "X";
		
		board[1][1] = "A0";//First row
		board[1][2] = "A1";
		board[1][3] = "B0";
		board[1][4] = "B1";
		board[1][5] = "F0";
		board[2][1] = "J0";//Second row
		board[2][2] = "J1";
		board[2][3] = "E0";
		board[2][4] = "G0";
		board[2][5] = "empty";
		board[3][1] = "J2";//Third row
		board[3][2] = "J3";
		board[3][3] = "E2";
		board[3][4] = "H0";
		board[3][5] = "empty";
		board[4][1] = "C0";//Fourth row
		board[4][2] = "C1";
		board[4][3] = "D0";
		board[4][4] = "D1";
		board[4][5] = "I0";
		
		return board;
	}
	
	//print the current board
	public static void PrintBoard(String[][] board){
		
		for(int i=0; i<=5; i++){
			for(int j=0; j<=6; j++){
				if(board[i][j].charAt(0)=='e')//empty prints
					System.out.print('%');
				else if(board[i][j].charAt(0)=='X')//border prints
					System.out.print('#');
				else
				System.out.print(board[i][j].charAt(0));//print the first character
			}
		System.out.println();
		}
	}
	
	//move A B C D
	public static String[][] ExecuteTwobyOne(String direction, String piece, String[][] board, Integer[][] emptyLoc){
		switch(direction){
		case "UP":
			if(board[emptyLoc[0][0]+1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]+1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]+1][emptyLoc[0][1]]="empty";
				board[emptyLoc[1][0]+1][emptyLoc[1][1]]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"2");
			}
			break;
		case "DOWN":
			if(board[emptyLoc[0][0]-1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]-1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]-1][emptyLoc[0][1]]="empty";
				board[emptyLoc[1][0]-1][emptyLoc[1][1]]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"2");
			}
			break;
		case "LEFT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]+1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]+1]=(piece+"2");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[0][0]][emptyLoc[0][1]+2]="empty";
			}
			else if(board[emptyLoc[1][0]][emptyLoc[1][1]+1].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]][emptyLoc[1][1]+1]=(piece+"2");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]+2]="empty";
			}
			break;
		case "RIGHT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]-1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]-1]=(piece+"2");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[0][0]][emptyLoc[0][1]-2]="empty";
			}
			else if(board[emptyLoc[1][0]][emptyLoc[1][1]-1].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]][emptyLoc[1][1]-1]=(piece+"2");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]-2]="empty";
			}
			break;
		}
		return board;
	}
	
	//move E
	public static String[][] ExecuteOnebyTwo(String direction, String piece, String[][] board, Integer[][] emptyLoc){
		switch(direction){
		case "UP":
			if(board[emptyLoc[0][0]+1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]+1][emptyLoc[0][1]]=(piece+"2");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[0][0]+2][emptyLoc[0][1]]="empty";
			}
			else if(board[emptyLoc[1][0]+1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]+1][emptyLoc[1][1]]=(piece+"2");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
				board[emptyLoc[1][0]+2][emptyLoc[1][1]]="empty";
			}
			break;
		case "DOWN":
			if(board[emptyLoc[0][0]-1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]-1][emptyLoc[0][1]]=(piece+"2");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[0][0]-2][emptyLoc[0][1]]="empty";
			}
			else if(board[emptyLoc[1][0]-1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]-1][emptyLoc[1][1]]=(piece+"2");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
				board[emptyLoc[1][0]-2][emptyLoc[1][1]]="empty";
			}
			break;
		case "RIGHT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]-1].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]][emptyLoc[1][1]-1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]-1]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]-1]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"2");
			}
			break;
		case "LEFT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]+1].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]][emptyLoc[1][1]+1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]+1]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]+1]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"2");
			}
			break;
		}
		return board;
	}
	
	//move F G H I
	public static String[][] ExecuteOnebyOne(String direction, String piece, String[][] board, Integer[][] emptyLoc){
		switch(direction){
		case "UP":
			if(board[emptyLoc[0][0]+1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]+1][emptyLoc[0][1]]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
			}
			else if(board[emptyLoc[1][0]+1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]+1][emptyLoc[1][1]]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
			}
			break;
		case "DOWN":
			if(board[emptyLoc[0][0]-1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]-1][emptyLoc[0][1]]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
			}
			else if(board[emptyLoc[1][0]-1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]-1][emptyLoc[1][1]]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
			}
			break;
		case "RIGHT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]-1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]-1]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
			}
			else if(board[emptyLoc[1][0]][emptyLoc[1][1]-1].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]][emptyLoc[1][1]-1]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
			}
			break;
		case "LEFT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]+1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]+1]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
			}
			else if(board[emptyLoc[1][0]][emptyLoc[1][1]+1].charAt(0)==piece.charAt(0)){
				board[emptyLoc[1][0]][emptyLoc[1][1]+1]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"0");
			}
			break;
		}
		
		
		return board;
	}
	
	//move J
	public static String[][] ExecuteTwobyTwo(String direction, String piece, String[][] board, Integer[][] emptyLoc){
		switch(direction){
		case "UP":
			if(board[emptyLoc[0][0]+1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]+1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]+2][emptyLoc[0][1]]="empty";
				board[emptyLoc[1][0]+2][emptyLoc[1][1]]="empty";
				board[emptyLoc[0][0]+1][emptyLoc[0][1]]=(piece+"2");
				board[emptyLoc[0][0]+1][emptyLoc[0][1]]=(piece+"3");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"1");
			}
			break;
		case "DOWN":
			if(board[emptyLoc[0][0]-1][emptyLoc[0][1]].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]-1][emptyLoc[1][1]].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]-2][emptyLoc[0][1]]="empty";
				board[emptyLoc[1][0]-2][emptyLoc[1][1]]="empty";
				board[emptyLoc[0][0]-1][emptyLoc[0][1]]=(piece+"2");
				board[emptyLoc[0][0]-1][emptyLoc[0][1]]=(piece+"3");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"1");
			}
			break;
		case "RIGHT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]-1].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]][emptyLoc[1][1]-1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]-2]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]-2]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]-1]=(piece+"2");
				board[emptyLoc[0][0]][emptyLoc[0][1]-1]=(piece+"3");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"1");
			}
			break;
		case "LEFT":
			if(board[emptyLoc[0][0]][emptyLoc[0][1]+1].charAt(0)==piece.charAt(0)
			&& board[emptyLoc[1][0]][emptyLoc[1][1]+1].charAt(0)==piece.charAt(0)){//swap empty with piece
				board[emptyLoc[0][0]][emptyLoc[0][1]+2]="empty";
				board[emptyLoc[1][0]][emptyLoc[1][1]+2]="empty";
				board[emptyLoc[0][0]][emptyLoc[0][1]+1]=(piece+"2");
				board[emptyLoc[0][0]][emptyLoc[0][1]+1]=(piece+"3");
				board[emptyLoc[0][0]][emptyLoc[0][1]]=(piece+"0");
				board[emptyLoc[1][0]][emptyLoc[1][1]]=(piece+"1");
			}
			break;
		}
		return board;
	}

}
