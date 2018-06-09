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

public class GamePanel extends JPanel {

	private AirHockey game;
	private BufferedImage background;
	private Image background_resized;
	
	private Puck puck;
	private Goal topGoal, bottomGoal;
	
	private byte playerScore = 0, enemyScore = 0;
	
	private ArrayList<MenuButton> menu_buttons;
	private ArrayList<MenuButton> pause_buttons;
	
	Thread collisionThread;
	private boolean running = false;

	GamePanel(final AirHockey game, int w, int h){
		super();
		
		this.game = game;
		
		menu_buttons = new ArrayList<MenuButton>();
		//menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 100, 100, 50, "Start", new Color(239, 69, 69)));
		//menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 200, 100, 50, "Scores", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 125, 100, 50, "Easy", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 200, 100, 50, "Medium", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 275, 100, 50, "Hard", new Color(239, 69, 69)));
		
		pause_buttons = new ArrayList<MenuButton>();
		pause_buttons.add(new MenuButton((game.getWidth() / 2) - 45, 210, 100, 35, "Main Menu", new Color(239, 69, 69)));
		pause_buttons.add(new MenuButton((game.getWidth() / 2) - 45, 260, 100, 35, "Restart", new Color(239, 69, 69)));
		
		puck = new Puck(game, (game.getWidth() / 2) - 16, (game.getHeight() / 2) - 28);
		topGoal = new Goal((game.getWidth() / 2) - 50, 15);
		bottomGoal = new Goal((game.getWidth() / 2) - 50, game.getHeight() - 65);
		
		try {
			background = ImageIO.read(new File("cheating.jpeg"));
			background_resized = background.getScaledInstance(480, 720, Image.SCALE_DEFAULT);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(game.getGameState() == 3) {
			puck.update();
			game.getPlayer().update(game.getMX(), game.getMY());
			if(topGoal.inGoal(puck)) {
				playerScore++;
				goalScored(true);
			}else if(bottomGoal.inGoal(puck)) {
				enemyScore++;
				goalScored(false);
			}
			if(playerScore >= 7) {
				
				reset();
			}else if(enemyScore >= 7) {
				
				reset();
			}
		}
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(game.getGameState() == 3) {						//Playing the game
			g.drawImage(background_resized, 0, 0, null);
			
			g.setColor(Color.RED);
			
			//Drawing boundaries for the player, just for testing will be removed later 
			/*
			g.drawLine(0, (game.getHeight() / 2) - 14, game.getWidth(), (game.getHeight() / 2) - 18); //??????? Middle line
			g.drawLine(15, 0, 15, game.getHeight()); //Left
			g.drawLine(game.getWidth() - 20, 0, game.getWidth() - 20, game.getHeight()); //Right
			g.drawLine(0, game.getHeight() - 44, game.getWidth(), game.getHeight() - 44); //Bottom
			g.drawLine(0,  15, game.getWidth(), 15); //Top
			*/
			
			game.getPlayer().render(g);
			puck.render(g);
			
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
			
		}else if(game.getGameState() == 1) {                //Difficulty Menu
			
			
			
		}else if(game.getGameState() == 2) {                //Game over/Score Menu
			
			
			
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
	public void goalScored(boolean playerScored) {
		if(playerScored) {
			puck.setX(game.getWidth() / 2);
			puck.setY((game.getHeight() / 2) - 100);
		}else {
			puck.setX(game.getWidth() / 2);
			puck.setY((game.getHeight() / 2) + 100);
		}
		
		puck.setVel(0);
		
		try {
			Robot r = new Robot();
			int wx = (int)game.getFrame().getLocation().getX(), wy = (int)game.getFrame().getLocation().getY();
			r.mouseMove(wx + game.getWidth() / 2, wy + (game.getHeight() / 2) + 200  );
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void reset() {
		playerScore = 0;
		enemyScore = 0;
		game.setGameState(3);
		game.showCursor();
	}
	public void clicked(MenuButton b) {
		if(b.getMessage() == "Easy") {
			game.setDifficulty(0);
			//ai
			game.setGameState(3);
			game.hideCursor();
		}else if(b.getMessage() == "Medium") {
			game.setDifficulty(1);
			//ai
			game.setGameState(3);
			game.hideCursor();
		}else if(b.getMessage() == "Hard") {
			game.setDifficulty(2);
			//ai
			game.setGameState(3);
			game.hideCursor();
		}
		
		if(game.getGameState() == 4) {
			if(b.getMessage() == "Main Menu") {
				game.showCursor();
				game.setGameState(0);
				reset();
			}else if(b.getMessage() == "Restart") {
				game.hideCursor();
				reset();
			}
		}
	}
	
	//pause menu
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
