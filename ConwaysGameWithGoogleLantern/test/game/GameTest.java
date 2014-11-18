package game;

import static org.junit.Assert.*;
import game.Game;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

public class GameTest {

	private Game game = null;
	

	@Test
	public void displaySizeBoardTest()
	{
		game = new Game();		
		game.setBoardLength(5);
		game.initialise();
		game.displaySizeBoard();
	}
	
	
	@Test
	public void displayBoardTest()
	{
		game = new Game();		
		game.setBoardLength(20);
		game.initialise();		
		game.displayBoard();
		
	}
	
	
	/*
	 * Tests displaying random life
	 * */
	@Test
	public void displayBoardWithRandmomLife()
	{
		game = new Game();		
		game.setBoardLength(5);
		game.initialise();	
		game.createRandomLifeOnBoard(3);
		game.displayBoard();
		
	}
	
	
	/*
	 * Used to test if cells adjacent cells are correctly calculated
	 * */
	@Test
	public void printCellsAndAdjacentCells()
	{
		game = new Game();		
		game.setGlobeBoard(true);
		game.setBoardLength(5);
		game.initialise();	
		game.displaySizeBoard();		
		game.printOutCellAndAdjacentCells();
		
		
	}
	
	
	/*
	 * Creates a board with a beehive configuration to test rules
	 * */
	@Test
	public void BeehiveStillLifeTest()
	{
		List<Integer> aliveCells = new ArrayList<Integer>();
		aliveCells.add(9);
		aliveCells.add(10);
		aliveCells.add(14);
		aliveCells.add(17);
		aliveCells.add(21);
		aliveCells.add(22);		
		
		game = new Game();
		//game = Game.getInstance();
		game.setBoardLength(6);
		game.initialise();	
		game.createSpecificLife(aliveCells);
		game.displayBoard();
		System.out.println("");System.out.println("");
		game.cycleGenerationsShowInCmd(10);
		
	}
	
	
	/*
	 * Creates a board with a becon configuration to test rules
	 * */
	@Test
	public void BeaconOscillatoreTest()
	{
		List<Integer> aliveCells = new ArrayList<Integer>();
		aliveCells.add(8);
		aliveCells.add(9);
		aliveCells.add(14);		
		aliveCells.add(23);
		aliveCells.add(28);
		aliveCells.add(29);		
		
		game = new Game();
		
		game.setBoardLength(6);
		game.initialise();	
		game.createSpecificLife(aliveCells);		
		game.cycleGenerationsShowInTerminal(50,10);
		
	}
	
	@Test
	public void startGameTest()
	{
		game = new Game();		
		game.initialise();
		game.startGame(1000, 5, 8);
		
	}
	
	
	
	
}




