package main;

import java.awt.Point;

public class Physics {
	
	public static final double FRICTION = 0.01;
	
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
	public static void collidesWall(Puck p) { //For walls
		if((int)p.getX()-p.getRadius() < 15 || (int)p.getX()+p.getRadius() > 486 - 20) {
			p.setVelX(getVelX(p.getMass(), p.getVelX()));
		}
		if((int)p.getY()-p.getRadius() < 15 || (int)p.getY()+p.getRadius() > 750 - 44) {
			p.setVelY(getVelY(p.getMass(), p.getVelY()));
		}

	}
	/*
	 * public static boolean collides(Ai a, Puck p){
	 * 		Method for when we implement ai
	 * 
	 * }
	 */
}
