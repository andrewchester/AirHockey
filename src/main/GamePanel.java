package main;

import java.awt.Color;
import java.awt.Font;

//Image Source(background): https://www.codeproject.com/KB/mobile/432054/bariers.jpg

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	
	private ArrayList<MenuButton> menu_buttons;
	
	Thread collisionThread;
	private boolean running = false;

	GamePanel(final AirHockey game, int w, int h){
		super();
		
		this.game = game;
		
		menu_buttons = new ArrayList<MenuButton>();
		//menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 100, 100, 50, "Start", new Color(239, 69, 69)));
		//menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 200, 100, 50, "Scores", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 225, 100, 50, "Easy", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 325, 100, 50, "Medium", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 425, 100, 50, "Hard", new Color(239, 69, 69)));
		
		puck = new Puck(game, (game.getWidth() / 2) - 16, (game.getHeight() / 2) - 28);
		
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
		}
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(game.getGameState() == 3) {						//Playing the game
			g.drawImage(background_resized, 0, 0, null);
			
			g.setColor(Color.RED);
			
			//Drawing boundaries for the player, just for testing will be removed later 
			g.drawLine(0, (game.getHeight() / 2) - 14, game.getWidth(), (game.getHeight() / 2) - 18); //??????? Middle line
			g.drawLine(15, 0, 15, game.getHeight()); //Left
			g.drawLine(game.getWidth() - 20, 0, game.getWidth() - 20, game.getHeight()); //Right
			g.drawLine(0, game.getHeight() - 44, game.getWidth(), game.getHeight() - 44); //Bottom
			g.drawLine(0,  15, game.getWidth(), 15); //Top
			g.drawRect((game.getWidth()-200)/2, game.getHeight()-64, 200, 20); //bottom goal
			g.drawRect((game.getWidth()-200)/2, 15, 200, 20); //top goal
			
			game.getPlayer().render(g);
			puck.render(g);
			
			g.setColor(Color.RED);
			g.drawLine(game.getPlayer().getX(), game.getPlayer().getY(), (int)puck.getX(), (int)puck.getY());
			
		}else if(game.getGameState() == 0) {                //Main menu 
			g.setFont(new Font("Calibri", Font.BOLD, 50));
			int stringWidth = g.getFontMetrics().stringWidth("Air Hockey");
			g.drawString("Air Hockey", (game.getWidth()/2)-(stringWidth/2), 70);
			
			for(MenuButton b : menu_buttons)
				b.render(g, g2);
			
		}else if(game.getGameState() == 1) {                //Difficulty Menu
			
			
			
		}else if(game.getGameState() == 2) {                //Game over/Score Menu
			
			
			
		}else if(game.getGameState() == 4) {                //Pause menu
			/*
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .005f));
			g2.fillRect(0, 0, game.getWidth(), game.getHeight());
			*/
			g.setColor(new Color(96, 98, 102, 1));
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(155, 155, 200, 200);
			
			g.setColor(Color.WHITE);
			g.fillRect(150, 150, 200, 200);
			
			g.setColor(Color.BLACK);
			g.drawString("Press ESC to continue playing", 165, 180);
		}
	}
	public void clicked(MenuButton b) {
		if(b.getMessage() == "Easy") {
			game.setGameState(3);
			game.setShowingCursor(false);
		}else if(b.getMessage() == "Medium") {
			game.setGameState(3);
			game.setShowingCursor(false);
		}else if(b.getMessage() == "Hard") {
			game.setGameState(3);
			game.setShowingCursor(false);
		}
	}
	
	//pause menu
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) {
			if(game.getGameState() == 3) {
				game.setGameState(4); 
				game.setShowingCursor(true);
			}else if(game.getGameState() == 4) {
				game.setGameState(3);
				game.setShowingCursor(false);
			}
		}
	}
	
	public ArrayList<MenuButton> getButtons(){
		return menu_buttons;
	}
	public Puck getPuck() {
		return puck;
	}
}
