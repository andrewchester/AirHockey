package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

	private int gameState, w, h;
	private int x, y, radius;
	private BufferedImage background;
	private Image background_resized;

	GameBoard(int gameState, int w, int h){
		super();
		
		this.gameState = gameState;
		this.w = w;
		this.h = h;
		
		this.radius = 100;
		this.x = (w / 2) - (radius / 2);
		this.y = (h / 2) - (radius / 2);
		
		try {
			background = ImageIO.read(new File("cheating.jpeg"));
			background_resized = background.getScaledInstance(480, 720, Image.SCALE_DEFAULT);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void paintComponent(Graphics g) {
		g.drawImage(background_resized, 0, 0, null);
	}
}
