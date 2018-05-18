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
	
	private ArrayList<MenuButton> buttons;

	GamePanel(AirHockey game, int w, int h){
		super();
		
		this.game = game;
		
		buttons = new ArrayList<MenuButton>();
		buttons.add(new MenuButton((game.getWidth() / 2) - 50, 100, 100, 50, "Start", new Color(239, 69, 69)));
		
		try {
			background = ImageIO.read(new File("cheating.jpeg"));
			background_resized = background.getScaledInstance(480, 720, Image.SCALE_DEFAULT);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(game.getGameState() == 3) {
			game.getPlayer().update(game.getMX(), game.getMY());
		}
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(game.getGameState() == 3) {						//Playing the game
			g.drawImage(background_resized, 0, 0, null);
			
			g.setColor(Color.RED);
			g.drawLine(0, (game.getHeight() / 2) - 18, game.getWidth(), (game.getHeight() / 2) - 18); //???????
			
			game.getPlayer().render(g);
		}else if(game.getGameState() == 0) {                //Main menu 
			
			for(MenuButton b : buttons)
				b.render(g, g2);
			
		}else if(game.getGameState() == 1) {                //Difficulty Menu
			
		}else if(game.getGameState() == 2) {                //Game over/Score Menu
			
		}
	}
	
	public ArrayList<MenuButton> getButtons(){
		return buttons;
	}
}
