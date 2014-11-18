package game;

import java.util.ArrayList;
import java.util.List;

public class Cell {

	private List<Integer> adjacentCellsAddress = new ArrayList<Integer>();
	private List<Cell> adjacentCells = new ArrayList<Cell>();
	private List<Cell> board;//contains the 'pointer' to the games board
	private int boardSize;
	private int boardLength;	
	private int address;
	private boolean state;//state of the cell, alive or dead.
	private boolean globeBoard;
	
	public Cell(){}
	
	public Cell(int boardSize, int address,boolean state,List<Cell> board)
	{		
		this.board = board;//assigning the memory address of the Game objects board to the cell
		this.boardSize = boardSize;
		this.address = address;
		this.state = state;
		boardLength = (int) Math.sqrt(boardSize);// calculate board size by length squared
		determineAdjacentCells();
	}
	
	public Cell(int boardSize, int address,boolean state,List<Cell> board, boolean globeBoard)
	{		
		this.board = board;//assigning the memory address of the Game objects board to the cell
		this.boardSize = boardSize;
		this.address = address;
		this.state = state;
		boardLength = (int) Math.sqrt(boardSize);// calculate board size by length squared
		this.globeBoard = globeBoard;
		determineAdjacentCells();
	}
	
	/*Used for checking that adjacent cells are correct by visualising*/
	public void printOutCellAndAdjacentCells()
	{
		System.out.println("");
		System.out.println("");
		System.out.println("Cell: " + address + " Adjacent cells: ");
		
		for(Integer i : adjacentCellsAddress)
		{
			System.out.print(i + " ");
		}
	}
	
	/*
	Creates a list of adjacent cells for each cell and determines 
	if a cell is at the edge of the 'board' by using the reminder function
	as all cell that are divisible by the length of the board will be in 
	the right most column and all cells who's address -1 is divisible by 
	length of board will be in left most column.
	if the address + boardLength-1 is greater than the size of the board then the cells 
	adjacent cell will be off the board and the cell will not get an adjacent cell.
	If the address - boardLength is less than zero then the adjacent cell is off the board too. 
	*/
	private void determineAdjacentCells()
	{		
		if(globeBoard == false) //flat board
		{		
			//set adjacent cells address
			//set cell above
			if(address - boardLength > 0)
				adjacentCellsAddress.add(address - boardLength );		
			
			//set top right
			if(address - boardLength + 1 > 1 && address % boardLength != 0)
				adjacentCellsAddress.add(address - boardLength +1);
			
			//set top left
			if(address - boardLength -1 > 0 && ((address -1) % boardLength != 0))
				adjacentCellsAddress.add(address - boardLength -1);
			
					
			//set bottom
			if(address + boardLength <= boardSize)
				adjacentCellsAddress.add(address + boardLength);
			
			//set bottom right
			if(address + boardLength+1 <= boardSize && (address % boardLength !=0))
				adjacentCellsAddress.add(address + boardLength+1);

			//set bottom left
			if((address + boardLength-1 <= boardSize)  && ((address -1) % boardLength != 0)  )
				adjacentCellsAddress.add(address + boardLength-1);
			
			//set right
			if(address + 1 <= boardSize && (address % boardLength ) != 0 )
				adjacentCellsAddress.add(address+1);
			
			//set left
			if(address - 1 > 0 && ((address -1) % boardLength ) != 0)
				adjacentCellsAddress.add(address-1);
			
			
		}
		else//globe board
		{
			//set adjacent cells address for a global board configuration						
			
			//set border top right
			if(address == boardLength)
			{
				adjacentCellsAddress.add(boardSize +1 - boardLength );
			}
			//set border top left
			if(address == 1)
			{
				adjacentCellsAddress.add(boardSize);
			}
			//set border bottom left
			if(address == (boardSize +1 - boardLength) )
			{
				adjacentCellsAddress.add(boardLength);
			}
			//set border bottom right
			if(address == boardSize)
			{
				adjacentCellsAddress.add(1);
			}
			
			//set border top cells
			if(address <= boardLength)
			{
				adjacentCellsAddress.add(boardSize - boardLength + address);
				//set border top cells top left
				if(address != 1)
				{					
					adjacentCellsAddress.add(boardSize - boardLength +address  -1);
				}
				//set border top cells top right
				if(address != boardLength)
				{
					adjacentCellsAddress.add(boardSize - boardLength + address +1);						
				}			
			
			}
			
			
			//set border bottom cells			
			if(address > (boardSize - boardLength))
			{
				adjacentCellsAddress.add( boardLength - (boardSize - address) );
				
				//bottom right cells except for the last cell
				if(address != boardSize)
				{
					adjacentCellsAddress.add( boardLength - (boardSize - address) +1 );
				}
				
				//bottom left cells except for the first bottom cell
				if(address + boardLength - 1 != boardSize)
				{
					adjacentCellsAddress.add( boardLength - (boardSize - address) -1 );
				}
				
				
				
			}
			
			//set right border cells
			if(address % boardLength == 0)
			{
				//set right border cell
				adjacentCellsAddress.add(address + 1 - boardLength);
				
				//set right border perpendicular cells
			
					//top right border except for top most right cell
					if(address != boardLength)
					{
					adjacentCellsAddress.add(address -(2 * boardLength) + 1);
					}
					//bottom right border except for bottom most right cell
					if(address != boardSize)
					{
					adjacentCellsAddress.add(address + 1);					
					}
			}
			
			//left border cells
			if(address == 1 || (address -1) % boardLength == 0)
			{
				adjacentCellsAddress.add(address-1 + boardLength);
				
				if(address != 1)
				{
					adjacentCellsAddress.add(address-1);
				}
				
				if( (address+boardLength-1) != boardSize )
				{
					
					adjacentCellsAddress.add(2*boardLength + address - 1);
				}
				
				
			}
			
			
			
			//set adjacent cells address
			//set cell above
			if(address - boardLength > 0)
				adjacentCellsAddress.add(address - boardLength );		
			
			//set top right
			if(address - boardLength + 1 > 1 && address % boardLength != 0)
				adjacentCellsAddress.add(address - boardLength +1);
			
			//set top left
			if(address - boardLength -1 > 0 && ((address -1) % boardLength != 0))
				adjacentCellsAddress.add(address - boardLength -1);
			
					
			//set bottom
			if(address + boardLength <= boardSize)
				adjacentCellsAddress.add(address + boardLength);
			
			//set bottom right
			if(address + boardLength+1 <= boardSize && (address % boardLength !=0))
				adjacentCellsAddress.add(address + boardLength+1);

			//set bottom left
			if((address + boardLength-1 <= boardSize)  && ((address -1) % boardLength != 0)  )
				adjacentCellsAddress.add(address + boardLength-1);
			
			//set right
			if(address + 1 <= boardSize && (address % boardLength ) != 0 )
				adjacentCellsAddress.add(address+1);
			
			//set left
			if(address - 1 > 0 && ((address -1) % boardLength ) != 0)
				adjacentCellsAddress.add(address-1);
			
			
		}
			
	}
	
	/*
	 * Called by the Game object to determine if cells live or die by calling the cells getState method.
	 * The cell will determin if it should live or die by counting live adjacent cells.  
	 * */
	public boolean liveOrDie()
	{	
		int adjacentCellCount = 0;		
		for(Cell c : adjacentCells)
		{
			if(c.getState() == true)
			{
				adjacentCellCount++;	
			}
		}
		
		
		//Any live cell with fewer than two live neighbours dies, as if caused by under-population.
		if(adjacentCellCount < 2)
		{
			state = false;
		}
		else//Any live cell with two or three live neighbours lives on to the next generation.
		if(this.state == true && (adjacentCellCount ==2 || adjacentCellCount == 3))
		{
			state = true;	
		}
		else//Any live cell with more than three live neighbours dies, as if by overcrowding.
		if(this.state == true && (adjacentCellCount > 3))
		{
			state = false;
		}
		else
		//regenrate if 3 neigbours
		if(this.state == false && adjacentCellCount == 3)
		{
			state = true;
		}
		
		return state;
		
		
	}
	
	public void setAdjacentCells()
	{
		
		adjacentCells.clear();
		for(Integer a : adjacentCellsAddress)
		{
			try {
				adjacentCells.add(new Cell(board.get(a-1).boardSize,board.get(a-1).address,board.get(a-1).getState(),board.get(a-1).board));
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}			
		}
	}
	
	
	public boolean getState()
	{
		return this.state;
	}
	
	public void setState(boolean state)
	{
		this.state = state;
	}
	
	
	public int getBoardSize() {
		return boardSize;
	}


	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public List<Cell> getAdjacentCells() {
		return adjacentCells;
	}

	public void setAdjacentCells(List<Cell> adjacentCells) {
		this.adjacentCells = adjacentCells;
	}

	public List<Integer> getAdjacentCellsAddress() {
		return adjacentCellsAddress;
	}

	
	

}
