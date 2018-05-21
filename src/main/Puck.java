package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Puck {
	
	private Physics p;
	
	private float x, y, velx, vely;
	private int radius;
	private float mass;
	
	Puck(Physics p, int x, int y){
		
		this.p = p;
		this.x = x;
		this.y = y;
		
		this.velx = 0;
		this.vely = 0;
		
		this.radius = 10;
		
		this.mass = 1;
	}
	
	public void update() {
		this.x += velx;
		this.y += vely;
	}
	
	public void render(Graphics g) {
		Graphics2D g2  = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillOval((int)x, (int)y, radius * 2, radius * 2);
	}
}
