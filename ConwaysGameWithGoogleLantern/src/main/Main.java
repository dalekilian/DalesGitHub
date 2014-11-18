package main;

import presentation.GameWindow;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Theme;

import game.Game;


/*
 * Used for launching the game window
 * */
public class Main {
	GUIScreen gui;
	
	public Main() {
		
	}

	public static void main(String[] args) {
		
		//Launches terminal screen
		GUIScreen gui;	    
		gui = TerminalFacade.createGUIScreen();
		
		if(gui == null) {
	        System.err.println("Couldn't allocate a terminal!");
	        return;
	    }
		gui.getScreen().startScreen();
		gui.setTheme(Theme.getDefaultTheme());
		
	    //Attaches game window to terminal screen
		GameWindow window = new GameWindow();
	    gui.showWindow(window, GUIScreen.Position.CENTER);

	}

}
