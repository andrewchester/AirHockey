package main;

import java.awt.Point;

public class Physics {
	
	public static final double FRICTION = 0.01;
	public static final double LOSS = .75;
	
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
	
	public static void collides(Player p, Puck puck) { //Uses the distance formula
		Point start = new Point(p.getX(), p.getY());
		Point end = new Point((int)puck.getX(), (int)puck.getY());
		
		double d = Math.hypot(end.getX() - start.getX(), end.getY() - start.getY());
		
		if(d <= ((p.getRadius() + puck.getRadius()))) {
			int x_dif = (int) (p.getX() - puck.getY());
			int y_dif = (int) (p.getY() - puck.getY());
			
			int total_radius = p.getRadius() + puck.getRadius();
			
			if(x_dif > 0 && y_dif > 0) {
				puck.setX((float)p.getX() - total_radius);
				puck.setY((float)p.getY() - total_radius);
				puck.setVelX(-1 * (puck.getVelX() + (int)(p.getVelX() * LOSS)));
				puck.setVelY(-1 * (puck.getVelY() + (int)(p.getVelY() * LOSS)));
			}else if(x_dif < 0 && y_dif > 0) {
				puck.setX((float)p.getX() + total_radius);
				puck.setY((float)p.getY() - total_radius);
				puck.setVelX(puck.getVelX() + (int)(p.getVelX() * LOSS));
				puck.setVelY(puck.getVelY() + (int)(p.getVelY() * LOSS));
			}else if(x_dif > 0 && y_dif < 0) {
				puck.setX((float)p.getX() - total_radius);
				puck.setY((float)p.getY() + total_radius);
				puck.setVelX(puck.getVelX() + (int)(p.getVelX() * LOSS));
				puck.setVelY(puck.getVelY() + (int)(p.getVelY() * LOSS));
			}else if(x_dif < 0 && y_dif < 0){
				puck.setX((float)p.getX() + total_radius);
				puck.setY((float)p.getY() + total_radius);
				puck.setVelX(puck.getVelX() + (int)(p.getVelX() * LOSS));
				puck.setVelY(puck.getVelY() + (int)(p.getVelY() * LOSS));
			}
		}
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
