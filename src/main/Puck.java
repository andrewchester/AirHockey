package main;

import java.awt.Color;
import java.awt.Graphics;

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
		
		this.radius = 5;
		
		this.mass = 1;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval((int)x, (int)y, radius * 2, radius * 2);
	}
}
