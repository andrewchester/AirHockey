package main;

import java.awt.Point;

public class Physics {
	
	public static final double FRICTION = 0.1;
	
	////////// SAMPLE METHODS, these need to be updated to use mass /////////////
	public static float getVelX(float mass, float current_vel) {
		float new_vel = current_vel * -1;
		return new_vel;
	}
	public static float getVelY(float mass, float current_vel) { 
		float new_vel = current_vel * -1;
		return new_vel;
	}
	//////////////////////////
	
	public static float adjust(float vel) { //Adjusting the velocity towards zero based on friction
		float new_vel = Math.abs(vel);
		new_vel -= FRICTION;
		
		if(vel < 0)
			return new_vel * -1;
		else
			return new_vel;
	}
	
	public static boolean collides(Player p, Puck puck) { //Uses the distance formula
		Point start = new Point(p.getX(), p.getY());
		Point end = new Point((int)puck.getX(), (int)puck.getY());
		
		double d = Math.hypot(end.getX() - start.getX(), end.getY() - start.getY());
		
		if(d <= ((p.getRadius() + puck.getRadius()))) 
			return true;
		
		return false;
	}
	public static boolean collides(Puck p) { //For walls
		
		//Need stuff for walls here
		
		return false;
	}
	/*
	 * public static boolean collides(Ai a, Puck p){
	 * 		Method for when we implement ai
	 * 
	 * }
	 */
}
