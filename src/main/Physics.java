package main;

import java.awt.Point;

public class Physics {
	
	public static final double FRICTION = 0.0075;
	public static final double LOSS = .75;
	public static final int SPEED_LIMIT = 10;
	
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
		
		float x_dif = p.getX() - puck.getX();
	    float y_dif = p.getY() - puck.getY();

	    float total_radius = p.getRadius() + puck.getRadius();
	    float sqrRadius = total_radius * total_radius;

	    float distSqr = (x_dif * x_dif) + (y_dif * y_dif);

	    if (distSqr <= sqrRadius)
	    {
	        System.out.println("Collided");
	        
	        float new_velx = 0, new_vely = 0;
	     
	        //Really awful if statements don't look pls
	        if(puck.getY() < p.getY() && puck.getX() > (p.getX() - p.getRadius()) && puck.getX() < (p.getX() + p.getRadius())) { //Top
	        	if(p.getVelY() < 0)
		        	puck.setY(p.getY() - (p.getRadius() + puck.getRadius() - p.getVelY() + 1));
		        else
			        puck.setY(p.getY() - (p.getRadius() + puck.getRadius() + 1));
		        
	        	
	        	new_vely = -1 * (Math.abs(puck.getVelY()) + Math.abs(p.getVelY()));
	        	new_velx = p.getVelX();
	        }else if(puck.getY() > p.getY() && puck.getX() > (p.getX() - p.getRadius()) && puck.getX() < (p.getX() + p.getRadius())) { //Bottom
	        	if(p.getVelY() > 0)
		        	puck.setY(p.getY() + (p.getRadius() + puck.getRadius() + p.getVelY() + 1));
		        else
			        puck.setY(p.getY() + (p.getRadius() + puck.getRadius() + 1));
	        	
	        	new_vely = -1 * (Math.abs(puck.getVelY()) + Math.abs(p.getVelY()));
	        	new_velx = p.getVelX();
	        }else if(puck.getX() < p.getX() && puck.getY() > (p.getY() - p.getRadius()) && puck.getY() < (p.getY() + p.getRadius())) { //Left
	        	if(p.getVelX() < 0)
	        		puck.setX(p.getX() - (p.getRadius() + puck.getRadius() - p.getVelX() + 1));
	        	else
	        		puck.setX(p.getX() - (p.getRadius() + puck.getRadius() + 1));
	        	
	        	new_velx = -1 * (Math.abs(puck.getVelX()) + Math.abs(p.getVelX()));
	        	new_vely = p.getVelY();
	        }else if(puck.getX() > p.getX() && puck.getY() > (p.getY() - p.getRadius()) && puck.getY() < (p.getY() + p.getRadius())) { //Right
	        	if(p.getVelX() > 0)
	        		puck.setX(p.getX() + (p.getRadius() + puck.getRadius() + p.getVelX() + 1));
	        	else
	        		puck.setX(p.getX() + (p.getRadius() + puck.getRadius() + 1));
	        	
	        	new_velx = -1 * (Math.abs(puck.getVelX()) + Math.abs(p.getVelX()));
	        	new_vely = p.getVelY();
	        }
	        
	        if(Math.abs(new_velx) > SPEED_LIMIT)
	        	new_velx = new_velx / (new_velx / SPEED_LIMIT);
	        if(Math.abs(new_vely) > 10)
	        	new_vely = new_vely / (new_vely / SPEED_LIMIT);
	        	
	        puck.setVelX(new_velx);
	        puck.setVelY(new_vely);
	    }
	}
	public static void collidesWall(Puck p) { //For walls
		if((int)p.getX()-p.getRadius() < 15 || (int)p.getX()+p.getRadius() > 486 - 20) {
			p.setVelX(getVelX((float)p.getMass(), p.getVelX()));
		}
		if((int)p.getY()-p.getRadius() < 15 || (int)p.getY()+p.getRadius() > 750 - 44) {
			p.setVelY(getVelY((float)p.getMass(), p.getVelY()));
		}

	}
	/*
	 * public static boolean collides(Ai a, Puck p){
	 * 		Method for when we implement ai
	 * 
	 * }
	 */
}
