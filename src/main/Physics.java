package main;

/*
 * Physics class for calculating Physics/Collisions
 */

public class Physics {
	
	public static final double FRICTION = 0.005; //The puck's friction
	public static final double LOSS = .75; //I honestly forget what this is
	public static final int SPEED_LIMIT = 10; //Speed limit for the puck
	
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
	//Calculates the velocity and angle from a given velx/vely
	public static float[] calcVel(float velx, float vely) {
		float[] values = new float[2];
		
		float vel = 0, dir = 0;
		
		vel = (float) Math.sqrt((velx * velx) + (vely * vely)); //Vel using pythagorean theorem 
		
		//Using trig to find the angle of the triangle to the nearest x-axis, and adding/subtracting it from 270/90 depending on the initial x/y velocity
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
	//Adjusting the velocity towards zero based on friction
	public static float adjust(float vel) { 
		float new_vel = Math.abs(vel);
		new_vel -= FRICTION;
		
		if(vel < 0)
			return new_vel * -1;
		else
			return new_vel;
	}
	//The collides method for the player and the puck
	public static void collides(Player p, Puck puck) { //Uses the distance formula
		
		float x_dif = p.getX() - puck.getX();
	    float y_dif = p.getY() - puck.getY();

	    float total_radius = p.getRadius() + puck.getRadius();
	    float sqrRadius = total_radius * total_radius;

	    float distSqr = (x_dif * x_dif) + (y_dif * y_dif);

	    if (distSqr <= sqrRadius)
	    {   
	        //
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
	        //float new_velX = (float) (((p.getMass()*p.getVelX()) + (puck.getMass()*puck.getVelX()) - p.getMass())/puck.getMass());
	        //float new_velY = (float) (((p.getMass()*p.getVelY()) + (puck.getMass()*puck.getVelY()) - p.getMass())/puck.getMass());
	       
	        //puck.setVelX(new_velX);
	        //puck.setVelY(new_velY);
	        if(Math.abs(new_vel) > SPEED_LIMIT)
	        	new_vel = new_vel / (new_vel / SPEED_LIMIT);
	        
	        puck.setVel(new_vel);
	    }
	}
	public static void collides(AI a, Puck puck) { //Uses the distance formula
		
		float x_dif = a.getX() - puck.getX();
	    float y_dif = a.getY() - puck.getY();

	    float total_radius = a.getRadius() + puck.getRadius();
	    float sqrRadius = total_radius * total_radius;

	    float distSqr = (x_dif * x_dif) + (y_dif * y_dif);

	    if (distSqr <= sqrRadius)
	    {   
	        //Moves the puck outside the player to prevent overlap on collisions
	        if(puck.getY() < a.getY() && puck.getX() > (a.getX() - a.getRadius()) && puck.getX() < (a.getX() + a.getRadius())) { //Top
	        	if(a.getVelY() < 0)
		        	puck.setY(a.getY() - (a.getRadius() + puck.getRadius() - a.getVelY() + 1));
		        else
			        puck.setY(a.getY() - (a.getRadius() + puck.getRadius() + 1));
	        }else if(puck.getY() > a.getY() && puck.getX() > (a.getX() - a.getRadius()) && puck.getX() < (a.getX() + a.getRadius())) { //Bottom
	        	if(a.getVelY() > 0)
		        	puck.setY(a.getY() + (a.getRadius() + puck.getRadius() + a.getVelY() + 1));
		        else
			        puck.setY(a.getY() + (a.getRadius() + puck.getRadius() + 1));
	        	
	        }else if(puck.getX() < a.getX() && puck.getY() > (a.getY() - a.getRadius()) && puck.getY() < (a.getY() + a.getRadius())) { //Left
	        	if(a.getVelX() < 0)
	        		puck.setX(a.getX() - (a.getRadius() + puck.getRadius() - a.getVelX() + 1));
	        	else
	        		puck.setX(a.getX() - (a.getRadius() + puck.getRadius() + 1));
	        }else if(puck.getX() > a.getX() && puck.getY() > (a.getY() - a.getRadius()) && puck.getY() < (a.getY() + a.getRadius())) { //Right
	        	if(a.getVelX() > 0)
	        		puck.setX(a.getX() + (a.getRadius() + puck.getRadius() + a.getVelX() + 1));
	        	else
	        		puck.setX(a.getX() + (a.getRadius() + puck.getRadius() + 1));
	        }
	        
	        puck.setDir((float) reflectAngle(puck.getDir()));
	        float new_vel = (float) calcVel(a.getVelX() + puck.getVelX(), a.getVelY() + puck.getVelY())[0];
	        
	        //Adjusts the new velocity based on the speed limit
	        if(Math.abs(new_vel) > SPEED_LIMIT)
	        	new_vel = new_vel / (new_vel / SPEED_LIMIT);
	        	
	        puck.setVel((float) new_vel);
	    }
	}
	//If the puck collides with the wall
	public static void collidesWall(Puck p) { //For walls
		/*
		if((int)p.getX()-p.getRadius() < 15 || (int)p.getX()+p.getRadius() > 486 - 20) { //Left and right walls
			p.setVelX(p.getVelX() * -1);
		}
		if((int)p.getY()-p.getRadius() < 20 || (int)p.getY()+p.getRadius() > 759 - 50) { //Top and botttom walls
			p.setVelY(p.getVelY() * -1);
		}
		*/
		
		float angle = p.getDir();
		float new_angle = p.getDir();
		
		if((int)p.getX()-p.getRadius() < 15 || (int)p.getX()+p.getRadius() > 486 - 20) { //Left and right walls
			if(angle > 0 && angle < 90) { //Hitting right wall going up
				p.setX(466 - p.getRadius());
				new_angle = 270 + (90 - angle);
			}else if(angle > 90 && angle < 180) { //Hitting right wall going down
				p.setX(466 - p.getRadius());
				new_angle = 270 - (angle - 90);
			}else if (angle == 90) {
				new_angle += 180;
			}
			
			if(angle > 270 && angle < 360) { //Hitting left wall going up
				p.setX(15 + p.getRadius());
				new_angle = 90 - (angle - 270);
			}else if(angle > 180 && angle < 270) { //Hitting left wall going down
				p.setX(15 + p.getRadius());
				new_angle = 90 + (270 - angle);
			}else if(angle == 270) {
				new_angle -= 180;
			}
		}
		if((int)p.getY()-p.getRadius() < 20 || (int)p.getY()+p.getRadius() > 759 - 50) { //Top and botttom walls
			if(angle > 0 && angle < 90) { //Hitting top wall going right
				p.setY(20 + p.getRadius());
				new_angle = 90 + (90 - angle);
			}else if(angle > 270 && angle < 360) { //Hitting top wall going left
				p.setY(20 + p.getRadius());
				new_angle = 270 - (angle - 270);
			}else if (angle == 0) {
				new_angle += 180;
			}
			
			if(angle > 90 && angle < 180) { //Hitting bottom wall going right
				p.setY((759 - 65) - p.getRadius());
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
	//Reflects an angle(may or may not be copied and pasted from the wall collisions)
	public static float reflectAngle(float angle) {
		float newA = 0;
		if(angle > 0 && angle < 90) { 
			newA = 90 + (90 - angle);
		}else if(angle > 270 && angle < 360) { 
			newA = 270 - (angle - 270);
		}else if (angle == 0) {
			newA = 180;
		}
		
		if(angle > 90 && angle < 180) { 
			newA = 90 - (angle - 90);
		}else if(angle > 180 && angle < 270) {
			newA = 270 + (270 - angle);
		}else if (angle == 180) {
			newA = 180;
		}
		return newA;
	}
}
