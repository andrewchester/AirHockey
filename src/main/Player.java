package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class Player {
	private int x, y, radius;
	private int velx, vely;
	private AirHockey game;
	
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
		else if(mx + radius >= game.getWidth() - 29)
			this.x = (game.getWidth() - 20) - radius * 2;
		else
			this.x = mx - 27; 
		
		if(my < (game.getHeight() / 2) + 25) 
			this.y = (game.getHeight() / 2) - 18; 
		else if(my >= game.getHeight() - 44)
			this.y = (game.getHeight() - 44) - radius * 2;
		else
			this.y = my - 45; 
		
		velx = x - startx;
		vely = y - starty;
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
		
		g.drawRect(x, y, radius * 2, radius * 2);
	}
	
	public int getRadius() {
		return radius;
	}
	public int getX() {
		return x + (radius / 2);
	}
	public int getY() {
		return y + (radius / 2);
	}
	public int getVelX() {
		return Math.abs(velx);
	}
	public int getVelY() {
		return Math.abs(vely);
	}
}
