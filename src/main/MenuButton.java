package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class MenuButton {

	private String message;
	private int x,y, w, h;
	private Color c;
	private Rectangle bounds;
	private boolean hovered = false;
	
	MenuButton(int x, int y, int w, int h, String message, Color c){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.message = message;
		this.c = c;
		
		this.bounds = new Rectangle(x, y, w, h);
	}
	
	public void render(Graphics g, Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 5, y + 5, w, h);
		if(hovered) 
			g.setColor(c.brighter());
		else 
			g.setColor(c);
		
		g.fillRect(x, y, w, h);
		g.setColor(Color.GRAY);
		g.drawRect(x, y, w, h);
		int messageWidth = g.getFontMetrics().stringWidth(message);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString(message, x + ((w - messageWidth) / 2), y + (h / 2) + 4);
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	public String getMessage() {
		return message;
	}
}
