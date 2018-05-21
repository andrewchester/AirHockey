package main;

import java.awt.Color;

//Image Source(background): https://www.codeproject.com/KB/mobile/432054/bariers.jpg

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

	GamePanel(AirHockey game, int w, int h){
		super();
		
		this.game = game;
		
		menu_buttons = new ArrayList<MenuButton>();
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 100, 100, 50, "Start", new Color(239, 69, 69)));
		menu_buttons.add(new MenuButton((game.getWidth() / 2) - 50, 200, 100, 50, "Scores", new Color(239, 69, 69)));
		
		puck = new Puck((game.getWidth() / 2) - 16, (game.getHeight() / 2) - 28);
		
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
			g.drawLine(0, (game.getHeight() / 2) - 18, game.getWidth(), (game.getHeight() / 2) - 18); //??????? Middle line
			g.drawLine(15, 0, 15, game.getHeight()); //Left
			g.drawLine(game.getWidth() - 30, 0, game.getWidth() - 30, game.getHeight()); //Right
			g.drawLine(0, game.getHeight() - 52, game.getWidth(), game.getHeight() - 52); //Bottom
			
			puck.render(g);
			game.getPlayer().render(g);
		}else if(game.getGameState() == 0) {                //Main menu 
			
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
		}
	}
	public void clicked(MenuButton b) {
		if(b.getMessage() == "Start") {
			game.setGameState(3);
			game.setShowingCursor(false);
		}
	}
	
	public ArrayList<MenuButton> getButtons(){
		return menu_buttons;
	}
}
