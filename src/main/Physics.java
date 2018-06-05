package main;

//import java.awt.Point;

public class Physics {
	
	public static final double FRICTION = 0.005;
	public static final double LOSS = .75;
	public static final int SPEED_LIMIT = 10;
	
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
		velx = (float) (Math.cos(Math.toRadians(angle)) * vel);
		vely = (float) (Math.sin(Math.toRadians(angle)) * vel);
		
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
	public static float[] calcVel(float velx, float vely) {
		float[] values = new float[2];
		
		float vel = 0, dir = 0;
		
		vel = (float) Math.sqrt((velx * velx) + (vely * vely));
		
		if(velx < 0 && vely < 0) {
			dir = (float) (270 + Math.atan((Math.abs(vely) / Math.abs(velx))));
		}else if(velx > 0 && vely < 0) {
			dir = (float) (90 - Math.atan((Math.abs(vely) / Math.abs(velx))));
		}else if(velx < 0 && vely > 0) {
			dir = (float) (270 - Math.atan((Math.abs(vely) / Math.abs(velx))));
		}else if(velx > 0 && vely > 0) {
			dir = (float) (90 + Math.atan((Math.abs(vely) / Math.abs(velx))));
		}
		
		values[0] = vel;
		values[1] = dir;
		
		return values;
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
	        float new_velx = 0, new_vely = 0;
	        
	        new_velx = (float) ((p.getVelX() * (p.getMass() - puck.getMass()) + 2 * (puck.getMass() * puck.getVelX())) / (p.getMass() + puck.getMass()));
	        new_vely = (float) ((p.getVelY() * (p.getMass() - puck.getMass()) + 2 * (puck.getMass() * puck.getVelY())) / (p.getMass() + puck.getMass()));
	        
	        
	        //Really awful if statements don't look pls
	        if(puck.getY() < p.getY() && puck.getX() > (p.getX() - p.getRadius()) && puck.getX() < (p.getX() + p.getRadius())) { //Top
	        	if(p.getVelY() < 0)
		        	puck.setY(p.getY() - (p.getRadius() + puck.getRadius() - p.getVelY() + 1));
		        else
			        puck.setY(p.getY() - (p.getRadius() + puck.getRadius() + 1));
	        }else if(puck.getY() > p.getY() && puck.getX() > (p.getX() - p.getRadius()) && puck.getX() < (p.getX() + p.getRadius())) { //Bottom
	        	if(p.getVelY() > 0)
		        	puck.setY(p.getY() + (p.getRadius() + puck.getRadius() + p.getVelY() + 1));
		        else
			        puck.setY(p.getY() + (p.getRadius() + puck.getRadius() + 1));
	        	
	        }else if(puck.getX() < p.getX() && puck.getY() > (p.getY() - p.getRadius()) && puck.getY() < (p.getY() + p.getRadius())) { //Left
	        	if(p.getVelX() < 0)
	        		puck.setX(p.getX() - (p.getRadius() + puck.getRadius() - p.getVelX() + 1));
	        	else
	        		puck.setX(p.getX() - (p.getRadius() + puck.getRadius() + 1));
	        }else if(puck.getX() > p.getX() && puck.getY() > (p.getY() - p.getRadius()) && puck.getY() < (p.getY() + p.getRadius())) { //Right
	        	if(p.getVelX() > 0)
	        		puck.setX(p.getX() + (p.getRadius() + puck.getRadius() + p.getVelX() + 1));
	        	else
	        		puck.setX(p.getX() + (p.getRadius() + puck.getRadius() + 1));
	        }
	        
	        puck.setDir((float) reflectAngle(puck.getDir()));
	        float new_vel = (float) calcVel(p.getVelX() + puck.getVelX(), p.getVelY() + puck.getVelY())[0];
	        
	        if(Math.abs(new_vel) > SPEED_LIMIT)
	        	new_vel = new_vel / (new_vel / SPEED_LIMIT);
	        	
	        puck.setVel((float) new_vel);
	    }
	}
	public static void collidesWall(Puck p) { //For walls
		
		float angle = p.getDir();
		float new_angle = p.getDir();
		
		if((int)p.getX()-p.getRadius() < 15 || (int)p.getX()+p.getRadius() > 486 - 20) { //Left and right walls
			if(angle > 0 && angle < 90) { //Hitting right wall going up
				new_angle = 270 + (90 - angle);
			}else if(angle > 90 && angle < 180) { //Hitting right wall going down
				new_angle = 270 - (angle - 90);
			}else if (angle == 90) {
				new_angle += 180;
			}
			
			if(angle > 270 && angle < 360) { //Hitting left wall going up
				new_angle = 90 - (angle - 270);
			}else if(angle > 180 && angle < 270) { //Hitting left wall going down
				new_angle = 90 + (270 - angle);
			}else if(angle == 270) {
				new_angle -= 180;
			}
		}
		if((int)p.getY()-p.getRadius() < 15 || (int)p.getY()+p.getRadius() > 750 - 44) { //Top and botttom walls
			if(angle > 0 && angle < 90) { //Hitting top wall going right
				new_angle = 90 + (90 - angle);
			}else if(angle > 270 && angle < 360) { //Hitting top wall going left
				new_angle = 270 - (angle - 270);
			}else if (angle == 0) {
				new_angle += 180;
			}
			
			if(angle > 90 && angle < 180) { //Hitting bottom wall going right
				new_angle = 90 - (angle - 90);
			}else if(angle > 180 && angle < 270) { //Hitting bottom wall going left
				new_angle = 270 + (270 - angle);
			}else if (angle == 180) {
				new_angle -= 180;
			}
		}
		if(new_angle != angle)
			p.setDir(new_angle);
	}
	public static float[] calcAngle(Player p, Puck puck) {
		System.out.println(p.getVelX() + ", " + p.getVelY());
		System.out.println(puck.getVelX() + ", " + puck.getVelY());
		
		float new_velx = p.getVelX() + puck.getVelX();
		float new_vely = p.getVelY() + puck.getVelY();
		
		return Physics.calcVel(new_velx, new_vely);
	}
	public static float reflectAngle(float angle) {
		float newA = 0;
		if(angle > 0 && angle < 90) { //Hitting top wall going right
			newA = 90 + (90 - angle);
		}else if(angle > 270 && angle < 360) { //Hitting top wall going left
			newA = 270 - (angle - 270);
		}else if (angle == 0) {
			newA = 180;
		}
		
		if(angle > 90 && angle < 180) { //Hitting bottom wall going right
			newA = 90 - (angle - 90);
		}else if(angle > 180 && angle < 270) { //Hitting bottom wall going left
			newA = 270 + (270 - angle);
		}else if (angle == 180) {
			newA = 180;
		}
		return newA;
	}
	/*
	 * public static boolean collides(Ai a, Puck p){
	 * 		Method for when we implement ai
	 * 
	 * }
	 */
}
