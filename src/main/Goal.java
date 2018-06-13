package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
/*
 * Goal has two things, the bounds, which when rendered is for debugging and the method inGoal, for detecting if a point is in the goal
 * 
 */
public class Goal {
	
	private Rectangle bounds;
	
	Goal(int x, int y){
		this.bounds = new Rectangle(x, y, 100, 10);
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.draw(bounds);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	public boolean inGoal(Puck p) { //returns true/false depending if the point passed is inside the 
		return bounds.intersects(p.getBounds());
	}
}
