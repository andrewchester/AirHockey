package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JPanel;

public class GameBoard extends JPanel{

	private int gameState, w, h;
	
	GameBoard(int gameState, int w, int h){
		super();
		
		this.gameState = gameState;
		this.w = w;
		this.h = h;
	}
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.drawOval((w / 2) - 25, (h / 2) - 25, 50, 50);
		
	}
}
