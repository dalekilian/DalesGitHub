package game;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.common.base.Strings;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.terminal.Terminal;



/*
 * Singlton class of game
 * */
public class Game {
	
	//private static Game instance = null;
	private  List<Cell> board = new ArrayList<Cell>();	
	private  int boardSize = 64;
	private  int boardLength = 8;
	private  String inputString = "";
	private  int generations = 0;
	private  int generationCount = 0;	
	private  int amoundOfRandomLife = 0;
	private String boardAsString = "";
	private boolean globeBoard = false;
	
	


	private Terminal terminal;
	
	
	public Game() {}
	
	
	/*
	 * Not used in final version but 
	 * used in testing
	 * */
	public void initialise()
	{
		generateBoard();
		terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
	}
	
	
	
	/*creates life on board by looping through the board and assigning life to random cells*/
	public void createRandomLifeOnBoard(int amountOfRandomLife)
	{
	board.clear();
		generateBoard();
		
		Random randomGenerator = new Random();
		 for(int i = 1; i <= amountOfRandomLife; i++)
		 {
			 board.get(randomGenerator.nextInt(boardSize)).setState(true);
			 
		 }
	}
		
	
	/*
	 * Used for creating specific paterns to test rules
	 * */
	public void createSpecificLife(List<Integer> livingCells)
	{
		for(Integer l : livingCells)
		{
			 board.get(l-1).setState(true);
		}
	}

	/*
	 * Sets the board length and size
	 * */
	public void setBoardLength(int boardLength)
	{
		this.boardLength = boardLength;
		this.boardSize = (int) Math.pow(boardLength, 2);		
	}
		
	
	
	/*
	 * Generates a board with cell to the size 
	 * of spcified in boardSize
	 * */
	public void generateBoard()
	{	//initialise board, i is the postion in the board
		board.clear();
		for(int i = 1;i<= boardSize;i++)
		{
			board.add(new Cell(boardSize,i,false,board,globeBoard));		
		}
	}
	
	/*For testing
	 * Displays the board on the cmd but with the address of each cell
	 * */
	public void displaySizeBoard()
	{
		int counter = 0;
		for(int v=1; v<=boardLength; v++ )
		{			
			for(int h=1;h<=boardLength;h++ )
			{
				System.out.print(Strings.padEnd(Integer.toString(board.get(counter).getAddress()),String.valueOf(boardSize).length()+1, ' '));
				counter++;
			}
			System.out.print("\n");
		}		
	}
		
	/*
	 * displays the running game on the cmd
	 * */
	public void displayBoard()
	{
		int counter = 0;
	
		for(Cell c : board)
		{
			if(counter == boardLength)
			{
				System.out.print("\n");
			
				counter = 0;
			}
			System.out.print(c.getState()?"[&]":"[ ]");
		
			counter++;
			
		}
	}
	
	/*
	 * Returns the board as a string
	 * */
	public String getBoardDisplayAsString()
	{			
		int counter = 0;
		StringBuilder sb = new StringBuilder();
		for(Cell c : board)
		{
			if(counter == boardLength)
			{				
				sb.append("\n");
				counter = 0;
			}
			
			sb.append(c.getState()?"[&]":"[ ]");
			counter++;
			
		}
		//set the board as string variable 
		boardAsString = sb.toString();		
		
		return boardAsString;		
	}
	
	/*used for generating the game in the cmd prompt*/
	public void startGame(int generations,int boardLength,int amountOfRandomLife)
	{
		setBoardLength(boardLength);
		initialise();
		setAmoundOfRandomLife(amountOfRandomLife);
		
		createRandomLifeOnBoard(amountOfRandomLife);	
	
		displayBoard();System.out.println();System.out.println();

		
		for(int g=0;g<=generations; g++ )
		{
			setAdjacentCells();
			liveOrDie();
			displayBoard();
			System.out.println();System.out.println();
			if(isThereLife() == false)
				break;
		}	
		
	}
	
	/*displays generations of the board in the cmd prompt*/
	public void cycleGenerationsShowInCmd(int generations)
	{		
		for(int g=0;g<=generations; g++ )
		{
			setAdjacentCells();
			liveOrDie();
			displayBoard();
			System.out.println();System.out.println();
			if(isThereLife() == false)
				break;
		}	
	}
	
	
	
	/*
	 * runs game and displays via a console. 
	 * Not used in final version but can be used for testing
	 * */
	public void cycleGenerationsShowInTerminal(int generations, int startRow)
	{		
		
		int colNum = 0;		
		int lineNum = startRow;
		
		terminal.enterPrivateMode();
		
		terminal.setCursorVisible(false);
		terminal.moveCursor(0,lineNum);	
		
		for(int g=0;g<=generations; g++ )
		{
			setAdjacentCells();
			liveOrDie();			
			
			for(Cell c : board)
			{
				if(colNum == boardLength)
				{
					colNum = 0;
					lineNum++;
					terminal.moveCursor(0,lineNum);				
				}
				
				if(c.getState() == true)
				{
					terminal.putCharacter('[');	
					terminal.putCharacter('&');
					terminal.putCharacter(']');		
				}
				else
				{
					terminal.putCharacter('[');
					terminal.putCharacter(' ');
					terminal.putCharacter(']');				
				}		
				
				colNum++;	
				if(((lineNum+1 -startRow)== boardLength) && (colNum == boardLength) )
				{
					lineNum = startRow;
					colNum = 0;
					terminal.moveCursor(0,lineNum);						
				}				
			}		
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
			if(isThereLife() == false)
				break;
		}
		
		terminal.exitPrivateMode();
		
		
	}
		
	/*
	 * Used to determine if there is any life and if not stop the game
	 * Not used in final version
	 * */	
	public boolean isThereLife()
	{		
		boolean hasLife = false;
		for(Cell c : board)
		{
			if(c.getState() == true)
			{
				hasLife = true;
				break;
			}
		}
		
		return hasLife;		
	}
	
	
	/*Counts and returns the amount of life*/
	public int lifeCounter()
	{		
		int lifeCounter = 0;
		for(Cell c : board)
		{
			if(c.getState() == true)
			{
				lifeCounter++;
			}
		}		
		return lifeCounter;	
	}
	
	/*
	 * Sets each cells ajacent cells on the board
	 * */
	public void setAdjacentCells()
	{
		for(int b = 0;b<=board.size()-1;b++)			
		{				
			try {
				((Cell)board.get(b)).setAdjacentCells();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}		
	}
		
	
	/*
	 * Call each cells live or dive method to 
	 * determine the state of live for the next round
	 * */
	public void liveOrDie()
	{		
		for(int b = 0;b<=board.size()-1;b++)			
		{				
			try {
				((Cell)board.get(b)).liveOrDie();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}		
		}
	}
	
	public  int getGenerations() {
		return generations;
	}

	public  void setGenerations(int generations) {
		this.generations = generations;
	}

	public  int getAmountOfRandomLife() {
		return amoundOfRandomLife;
	}

	public  void setAmoundOfRandomLife(int amoundOfRandomLife) {
		this.amoundOfRandomLife = amoundOfRandomLife;
	}
	
	
	/**
	 * Used for testing
	 * Used to see a cells address and adjacent cells
	 * */
	public void printOutCellAndAdjacentCells()
	{
		for(Cell c : board)
		{
			c.printOutCellAndAdjacentCells();
		}
	}
	
	
	public boolean isGlobeBoard() {
		return globeBoard;
	}


	public void setGlobeBoard(boolean globeBoard) {
		this.globeBoard = globeBoard;
	}
	

}
