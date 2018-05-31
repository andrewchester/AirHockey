package main;

//import java.awt.Point;

public class Physics {
	
	public static final double FRICTION = 0.0075;
	public static final double LOSS = .75;
	public static final int SPEED_LIMIT = 5;
	
	////// Method for calculating velx and vely from given velocity and direction ////////
	public static float[] calcVels(float vel, float dir) {
		float[] vels = new float[2]; // Array for velx and vely values
		
		//Make sure vel and direction will always be positive
		vel = Math.abs(vel);	
		dir = Math.abs(dir);
		
		//Make sure the direction is within 0 - 360
		while(dir > 360)
			dir -= 360;
		
		//The hypotenuse of the triangle is the vel, and we use the angle to calculate the sides, which are velx and vely 
		//angle is the reference angle of the triangle created by the hypotenuse and the x axis as the adjacent side and with the opposite side being the vely
		float angle = dir;
		
		float velx = 0, vely = 0;
		
		//If it's going straight down one of the axis, set the values manually because we know what they'll be, we can't use trig because they're straight lines
		if(angle == 0 || angle == 90 || angle == 180 || angle == 270) {
			if(angle == 0) {
				velx = 0;
				vely = -1 * vel;
			}else if(angle == 90) {
				velx = vel;
				vely = 0;
			}else if(angle == 180) {
				velx = 0;
				vely = vel;
			}else if(angle == 270) {
				velx = -1 * vel;
				vely = 0;
			}
			
			vels[0] = velx;
			vels[1] = vely;
			
			return vels;
		}
		
		//Make sure the angle is less then 90
		if(dir > 0 && dir < 90) {
			angle = 90 - dir;
		}else if(dir > 90 && dir < 180) {
			angle -= 90;
		}else if(dir > 180 && dir < 270) {
			angle = 270 - dir;
		}else if(dir > 270 && dir < 360) {
			angle -= 270;
		}
		
		//Using trig to calculate the side lengths of the triangle formed by the direction and vel
		velx = (float) (Math.cos(angle) * vel);
		vely = (float) (Math.sin(angle) * vel);
		
		//Depending on which quadrant the direction is in, make the velx or vely positive or negative 
		if(dir > 0 && dir < 90) { //Top Right: Positive x, Negative y
			velx = Math.abs(velx);
			if(vely > 0)
				vely *= -1;
		}else if(dir > 90 && dir < 180) { //Bottom Right: Positive x, Positive y
			velx = Math.abs(velx);
			vely = Math.abs(vely);
		}else if(dir > 180 && dir < 270) { //Bottom Left: Negative x, Positive y
			if(velx > 0)
				velx *= -1;
			vely = Math.abs(vely);
		}else if(dir > 270 && dir < 360) { //Top Left: Negative x, Negative y
			if(velx > 0)
				velx *= -1;
			if(vely > 0)
				vely *= -1;
		}
		
		vels[0] = velx;
		vels[1] = vely;
		
		return vels;
	}
	
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
	
	public static float totalVel(float velx, float vely) {
		float velocity = (float) Math.sqrt((velx * velx) + (vely * vely));
		return velocity;
	}
	
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
	        
	        new_velx = (float) ((p.getVelX() * (p.getMass() - puck.getMass()) + 2 * (puck.getMass() * puck.getVelX())) / (p.getMass() + puck.getMass()));
	        new_vely = (float) ((p.getVelY() * (p.getMass() - puck.getMass()) + 2 * (puck.getMass() * puck.getVelY())) / (p.getMass() + puck.getMass()));
	        
	        /*
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
	        */
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
