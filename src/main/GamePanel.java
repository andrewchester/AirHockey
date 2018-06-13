package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;

//Image Source(background): https://www.codeproject.com/KB/mobile/432054/bariers.jpg

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/*
 * 
 * The GamePanel class extends JPanel and handles drawing objects, updating them, resetting the board, and win/lose conditions.
 * 
 */
public class GamePanel extends JPanel {

	private AirHockey game;
	private BufferedImage background;
	private Image background_resized;
	
	private Puck puck;
	private AI a;
	private Goal topGoal, bottomGoal;
	
	private byte playerScore = 0, enemyScore = 0;
	
	//All buttons
	private ArrayList<MenuButton> menu_buttons;
	private ArrayList<MenuButton> pause_buttons;
	
	Thread collisionThread;
	private boolean running = false;

	GamePanel(final AirHockey game, int w, int h){
		super();
		
		this.game = game;
		
		//Create the main menu buttons
		menu_buttons = new ArrayList<MenuButton>();
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 125, 100, 50, "Regular", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 200, 100, 50, "Impossible", new Color(239, 69, 69)));
		
		//Create the pause menu buttons
		pause_buttons = new ArrayList<MenuButton>();
		pause_buttons.add(new MenuButton((game.getWidth() / 2) - 45, 210, 100, 35, "Main Menu", new Color(239, 69, 69)));
		pause_buttons.add(new MenuButton((game.getWidth() / 2) - 45, 260, 100, 35, "Restart", new Color(239, 69, 69)));
		
		//Initialize the puck(the player is in the game class)
		puck = new Puck(game, (game.getWidth() / 2) - 16, (game.getHeight() / 2) - 20);
		topGoal = new Goal((game.getWidth() / 2) - 50, 15);
		bottomGoal = new Goal((game.getWidth() / 2) - 50, game.getHeight() - 65);
		
		//Resize the background image to the window width height
		try {
			background = ImageIO.read(new File("cheating.jpeg"));
			background_resized = background.getScaledInstance(480, 720, Image.SCALE_DEFAULT);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//Update method, runs every 16 millis or so
	public void update() {
		if(game.getGameState() == 3) {
			puck.update();
			game.getPlayer().update(game.getMX(), game.getMY());
			a.update();
			
			//Increasing score
			if(topGoal.inGoal(puck)) {
				playerScore++;
				goalScored(true);
			}else if(bottomGoal.inGoal(puck)) {
				enemyScore++;
				goalScored(false);
			}
			//Win conditions
			if(playerScore >= 7) {
				goalScored(true);
				reset();
				game.setGameState(2);
			}else if(enemyScore >= 7) {
				reset();
				game.setGameState(2);
			}
		}
		
		repaint(); //Rendering objects to screen
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(game.getGameState() == 3) {						//Playing the game
			g.drawImage(background_resized, 0, 0, null);
			
			game.getPlayer().render(g);
			puck.render(g);
		
			a.render(g);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Calibri", Font.BOLD, 30));
			g.drawString("" + playerScore, game.getWidth() - 40, game.getHeight() - 45);
			g.drawString("" + enemyScore, game.getWidth() - 40, 25);
			
		}else if(game.getGameState() == 0) {                //Main menu 
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
			for(MenuButton b : menu_buttons)
				b.render(g, g2, 20);
			
			g.setFont(new Font("Calibri", Font.BOLD, 50));
			int stringWidth = g.getFontMetrics().stringWidth("Air Hockey");
			g.drawString("Air Hockey", (game.getWidth()/2)-(stringWidth/2), 70);
			
		}else if(game.getGameState() == 2) {                //Game over
			
			g.setColor(new Color(96, 98, 102, 1));
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(155, 155, 200, 200);
			
			g.setColor(Color.WHITE);
			g.fillRect(150, 150, 200, 200);
			
			g.setColor(Color.BLACK);
			
			if(playerScore == 7) {
				g.drawString("Player Won!" + playerScore + "-" + enemyScore, 165, 180);
			}else if(enemyScore == 7) {
				g.drawString("AI Won!" + enemyScore + "-" + playerScore, 165, 180);
			}
						
			for(MenuButton b : pause_buttons)
				b.render(g, g2, 15);
			
		}else if(game.getGameState() == 4) {                //Pause menu
			
			g.setColor(new Color(96, 98, 102, 1));
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(155, 155, 200, 200);
			
			g.setColor(Color.WHITE);
			g.fillRect(150, 150, 200, 200);
			
			g.setColor(Color.BLACK);
			g.drawString("Press ESC to continue playing", 165, 180);
						
			for(MenuButton b : pause_buttons)
				b.render(g, g2, 15);
		}
	}
	//Reset the board, putting the puck on one side or another depending on who scored
	public void goalScored(boolean playerScored) {
		if(playerScored) {
			puck.setX(game.getWidth() / 2);
			puck.setY((game.getHeight() / 2) - 200);
		}else {
			puck.setX(game.getWidth() / 2);
			puck.setY((game.getHeight() / 2) + 100);
		}
		
		puck.setVel(0);
		
		//Move the mouse to the player's side of the board
		try {
			Robot r = new Robot();
			int wx = (int)game.getFrame().getLocation().getX(), wy = (int)game.getFrame().getLocation().getY();
			r.mouseMove(wx + game.getWidth() / 2, wy + (game.getHeight() / 2) + 200  );
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.setX((game.getWidth() / 2) - 16);
		a.setY(50);
	}
	//Reset the board in playing game state
	public void reset() {
		playerScore = 0;
		enemyScore = 0;

		puck.setX((game.getWidth() / 2) + 16);
		puck.setY((game.getHeight() / 2));
		
		a.setX((game.getWidth() / 2) - 16);
		a.setY(50);
		
		game.setGameState(3);
		game.hideCursor();
	}
	//When a button is clicked, gets a button passed into it from the AirHockey class
	public void clicked(MenuButton b) {
		if(game.getGameState() == 0) {
			//Different difficulties
			if(b.getMessage() == "Regular") {
				game.setDifficulty(0);
				a = new AI((game.getWidth() / 2) - 16, 50, 0, game);
				game.setGameState(3);
				game.hideCursor();
			}else if(b.getMessage() == "Impossible") {
				game.setDifficulty(1);
				a = new AI((game.getWidth() / 2) - 16, 50, 1, game);
				game.setGameState(3);
				game.hideCursor();
			}
		}
		//Pause menu actions
		if(game.getGameState() == 4) {
			if(b.getMessage() == "Main Menu") {
				reset();
				game.setGameState(0);
				game.showCursor();
			}else if(b.getMessage() == "Restart") {
				game.hideCursor();
				reset();
			}
		}
	}
	
	//toggle the pause menu
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) {
			if(game.getGameState() == 3) {
				game.setGameState(4); 
				game.showCursor();
			}else if(game.getGameState() == 4) {
				game.setGameState(3);
				game.hideCursor();
			}
		}
	}
	//Combines the menu_buttons and pause_buttons arrays so that you can get every button in the game
	public ArrayList<MenuButton> getButtons(){
		ArrayList<MenuButton> temp = new ArrayList<MenuButton>();
		temp.addAll(menu_buttons);
		temp.addAll(pause_buttons);
		return temp;
	}
	public ArrayList<MenuButton> getMenuButtons(){
		return menu_buttons;
	}
	public ArrayList<MenuButton> getPauseButtons(){
		return pause_buttons;
	}
	public Puck getPuck() {
		return puck;
	}
}
