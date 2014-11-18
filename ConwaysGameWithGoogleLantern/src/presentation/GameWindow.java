package presentation;


import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.Game;

import com.googlecode.lanterna.gui.*;
import com.googlecode.lanterna.gui.component.*;
import com.googlecode.lanterna.gui.dialog.MessageBox;

public class GameWindow extends Window {
	
	private Button btnStop = new Button("Stop");
	private Label lblLifeCounter = new Label("Amount of life: ");
	private Label lblGenerationCounter = new Label("Generations: 0");
	private Label lblBoard = new Label("");
	private TextBox tbBoardSize = new TextBox("10");
	private TextBox tbLife = new TextBox("25");
	private TextBox tbGeneration = new TextBox("500");
		
	private Panel rightPanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);;
	private Panel leftPanel = new Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
	private Panel horizontalPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);	        
	private Panel monitorPanel = new  Panel(new Border.Bevel(true), Panel.Orientation.VERTICAL);
	
	private Game game;
	
	private boolean stopGame =true;
	private boolean globeBoard = false;
	
	 ExecutorService threadPool = Executors.newFixedThreadPool(1);
	 final CompletionService<String> taskCompletionService = new ExecutorCompletionService<String>(threadPool);	
	
	public GameWindow() {
		super("Conways game of life by Dale Kilian");
			game = new Game();	
			game.setBoardLength(10);
			game.setGlobeBoard(globeBoard);
			
		 	
	
	        leftPanel.setTitle("Inputs");
	        leftPanel.addComponent(new Label("Board length"));
	        leftPanel.addComponent(tbBoardSize);
	        leftPanel.addComponent(new Label("Generation limit"));
	        leftPanel.addComponent(tbGeneration);
	        leftPanel.addComponent(new Label("Amount of initial life"));
	        leftPanel.addComponent(tbLife);	  
	        
	        rightPanel.addComponent(lblBoard);	        
	        game.generateBoard();
	        lblBoard.setText(game.getBoardDisplayAsString());
	        
	        leftPanel.addComponent(new Label(" "));
	        leftPanel.addComponent(new Button("1. Flat board set", new Action() {
                @Override
                public void doAction() {
                	
                	//deactivate button while running
                	if(stopGame == false)
                		return;
                	
                	if(globeBoard == false)
                	{
                		globeBoard = true;
                		//change the text of the calling button
                		((Button)leftPanel.getComponentAt(7)).setText("1.Globe board set");
                		game.setGlobeBoard(globeBoard);                		
                		
                	}
                	else
                	{
                		globeBoard = false;
                		//change the text of the calling button
                		((Button)leftPanel.getComponentAt(7)).setText("1.Flat board set");
                		game.setGlobeBoard(globeBoard);
                	}
                
                }
            }));
	         
	        leftPanel.addComponent(new Button("2. Display board", new Action() {
                @Override
                public void doAction() {             	

                	//deactivate button while running
                	if(stopGame == false)
                		return;
                  
                	//checks if all inputs are numeric and displays msg if not
                	if(isNumeric(tbBoardSize.getText()) &&  isNumeric(tbLife.getText()) &&  isNumeric(tbGeneration.getText()))
                	{
                		game.setBoardLength(Integer.parseInt(tbBoardSize.getText()));
                		game.generateBoard();                		
                	
                		lblBoard.setText(" ");                		
                		lblBoard.setText(game.getBoardDisplayAsString());                		
                		
                	}
                	else
                	{
						MessageBox.showMessageBox(getOwner(), "Incorrect inputs", "Please only enter numeric characters.");
					}
                	
                }
            }));

	        leftPanel.addComponent(new Button("3. Generate life", new Action(){
	        	public void doAction() {
	        		if(stopGame == false)
                		return;
	        	
	        		//checks if all inputs are numeric and displays msg if not
                	if(isNumeric(tbBoardSize.getText()) &&  isNumeric(tbLife.getText()) &&  isNumeric(tbGeneration.getText()))
                	{
                		game.createRandomLifeOnBoard(Integer.parseInt(((TextBox)tbLife).getText()));
    	        		lblBoard.setText(game.getBoardDisplayAsString());            		
                		lblBoard.setVisible(true);            		
                		lblLifeCounter.setText("Amount of life: " + game.lifeCounter());               		
                		
                	}
                	else
                	{
						MessageBox.showMessageBox(getOwner(), "Incorrect inputs", "Please only enter numeric characters.");
					}
	        		
	        	};
	        }));
	        	        
	        leftPanel.addComponent(new Button("4. Start game", new Action(){
	        	public void doAction() {
	        		
	        		//deactivate button while running
                	if(stopGame == false)
                		return;
	        		
	        		//checks if all inputs are numeric and displays msg if not
                	if(isNumeric(tbBoardSize.getText()) &&  isNumeric(tbLife.getText()) &&  isNumeric(tbGeneration.getText()))
                	{
                		game.setGenerations(Integer.parseInt(tbGeneration.getText())); 
                	
    	        		new Thread(new Runnable() {
    					    public void run() {
    					    	stopGame = false;
    					    	displayGenerations();
    					    	}
    					    }).start();	          		
                		
                	}
                	else
                	{
						MessageBox.showMessageBox(getOwner(), "Incorrect inputs", "Please only enter numeric characters.");
					} 
	        	};
	        }));
	        
	        leftPanel.addComponent(new Button("5. Stop game", new Action(){
	        	public void doAction() {	        			        		
	        		stopGame = true;
	        		
	        	};
	        }));
	        
	        leftPanel.addComponent(new Button("6. Exit", new Action(){
	        	public void doAction() {	        			        		
	        		System.gc();
	        		System.exit(0);
	        		
	        	};
	        }));
	        
	        monitorPanel.setTitle("Monitor");
	        monitorPanel.addComponent(lblGenerationCounter);
	        monitorPanel.addComponent(lblLifeCounter);
	        	        
	        leftPanel.addComponent(monitorPanel);
	        horizontalPanel.addComponent(leftPanel);
	        horizontalPanel.addComponent(rightPanel);
	    
	        addComponent(horizontalPanel);

	}
	
	public void displayGenerations()
	{
  		int lifeCounter = 0;
  		int generations = game.getGenerations();
		
		for(int g=0;g<=generations; g++ )
		{
			game.setAdjacentCells();
			game.liveOrDie();
			
			String board = game.getBoardDisplayAsString();
    		lblBoard.setText(" ");    	
    		lblBoard.setText(board);   		
    		lblGenerationCounter.setText("Generations: "+ g);
    		lifeCounter = game.lifeCounter();
    		lblLifeCounter.setText("Amount of life: " + lifeCounter);
    		
			if(lifeCounter == 0)
				break;
			if(stopGame == true)
				break;
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				
			}
		}
		
	}
	
	public boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	

}
