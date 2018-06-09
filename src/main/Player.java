package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class Player {
	private int x, y, radius;
	private float velx, vely;
	private AirHockey game;
	
	private double mass = 1.5;

	private final float SL = 5;
	
	Player(AirHockey game, int x, int y, int radius){
		this.radius = radius / 2;
		
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public void update(int mx, int my) {
		
		int startx = x, starty = y;
		
		if((mx - radius) - 5 <= 15)
			this.x = 15;
		else if(mx + radius >= game.getWidth() - 30)
			this.x = (game.getWidth() - 30) - radius * 2;
		else
			this.x = mx - 27; 
		
		if(my < (game.getHeight() / 2) + 30) 
			this.y = (game.getHeight() / 2) - 14; 
		else if(my >= game.getHeight() - 54)
			this.y = (game.getHeight() - 54) - radius * 2;
		else
			this.y = my - 45; 
		
		//made it a float for the calculation, may round out 0.75 if it's an int
		velx = (float) ((x - startx) * 0.75);
		vely = (float) ((y - starty) * 0.75);
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Ellipse2D s = new Ellipse2D.Float();
		g.setColor(new Color(0, 153, 255));
	    s.setFrame(x, y, radius * 2, radius * 2);
	    g2.fill (s);
	    
	    Ellipse2D s2 = new Ellipse2D.Float();
		g.setColor(new Color(0, 51, 255));
		s2.setFrame(x + (radius / 2), y + (radius / 2), radius, radius);
		g2.fill(s2);
		
		//g.drawRect(x, y, radius * 2, radius * 2);
	}
	
	public int getRadius() {
		return radius;
	}
	public int getX() {
		return x + radius;
	}
	public int getY() {
		return y + radius;
	}
	//issa float
	public float getVelX() {
		return Math.abs(velx);
	}
	public float getVelY() {
		return Math.abs(vely);
	}
	public double getMass() {
		return mass;
	}
}
