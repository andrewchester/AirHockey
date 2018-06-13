package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
/*
 * Made our own MenuButton class so the button was a rect with a drop shadow 
 * 
 */
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
		
		this.bounds = new Rectangle(x, y + 10, w, h);//Bounds for calculating if the mouse pressed it
	}
	
	public void render(Graphics g, Graphics2D g2, int fontSize) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//Antialiasing
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 5, y + 5, w, h);
		//Hovered variable which is set if the mx and my is on the button, makes the color a little lighter
		if(hovered) 
			g.setColor(c.brighter());
		else 
			g.setColor(c);
		
		g.fillRect(x, y, w, h);
		g.setColor(Color.GRAY);
		g.drawRect(x, y, w, h);
		
		//Calculates the messages pixel width in order to center it in the button
		int messageWidth = g.getFontMetrics().stringWidth(message);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.PLAIN, fontSize));
		if(message == "Regular") //Doesn't work with the first menu button for some reason, so we had to do this awkward work around
			g.drawString(message, x + 20, y + (h / 2) + 6);
		else
			g.drawString(message, x + ((w / 2) - (messageWidth/2)), y + (h / 2) + 6);
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
