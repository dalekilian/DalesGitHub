package PresentationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import presentation.GameWindow;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Theme;

public class GameWindowTest {

	
	/*
	 * Tests the game by launching the game window.
	 * */
	@Test
	public void GameWindowTest() {	
		
		GUIScreen gui = TerminalFacade.createGUIScreen();
		
		if(gui == null) {
	        System.err.println("Couldn't allocate a terminal!");
	        return;
	    }
		gui.getScreen().startScreen();
		gui.setTheme(Theme.getDefaultTheme());
		
	    GameWindow window = new GameWindow();
	    gui.showWindow(window, GUIScreen.Position.CENTER);
	
	}

}
