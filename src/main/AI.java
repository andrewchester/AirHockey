package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javafx.scene.shape.Line;

public class AI {

	private float x, y, velx, vely, radius;
	private int difficulty;
	private AirHockey game;
	private final float SPEED_LIMIT = 4;
	private float midline;
	
	AI(float x, float y, int difficulty, AirHockey game){
		this.x = x;
		this.y = y;
		this.radius = 15;
		this.velx = 0;
		this.vely = 0;
		this.difficulty = difficulty;
		this.game = game;
		this.midline = (game.getHeight() / 2) - 30;
	}
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Ellipse2D s = new Ellipse2D.Float();
		g.setColor(Color.RED);
	    s.setFrame(x, y, radius * 2, radius * 2);
	    g2.fill(s);
	}
	public void update() {
		Physics.collides(this, game.getPanel().getPuck());
		
		float velx_dif = Math.abs(velx);
		float vely_dif = Math.abs(vely);
		
		if(velx_dif > SPEED_LIMIT) {
			if(velx > 0)
				velx -= (velx - SPEED_LIMIT);
			else if(velx < 0)
				velx += (Math.abs(SPEED_LIMIT + velx));
		}else if(vely_dif > SPEED_LIMIT) {
			if(vely > 0)
				vely -= (vely - SPEED_LIMIT);
			else if(vely < 0)
				vely += (Math.abs(SPEED_LIMIT + vely));
		}
		
		if(this.y > midline) {
			setY(midline - (radius * 2));
		}else if(this.x - radius <  15) {
			setX(15 + (radius * 2));
		}else if(this.x + radius > game.getWidth() - 30) {
			setX((game.getWidth() - 30) - (radius * 2));
		}else if(this.y < 15) {
			setY(15);
		}
		
		if(difficulty == 0) {
			if(game.getPanel().getPuck().getY() < midline)
				moveTo(game.getPanel().getPuck().getX(), game.getPanel().getPuck().getY());
			else
				moveTo(game.getWidth() / 2, 50);
		}else if(difficulty == 1) {
			float middle = (game.getWidth() / 2) - 16;
			if(game.getPanel().getPuck().getX() > (middle - 100) && game.getPanel().getPuck().getX() < (middle + 100)) {
				
				moveTo(middle, 50);
				
				if(game.getPanel().getPuck().getX() > this.x) {
					velx = 3;
				}else if(game.getPanel().getPuck().getX() < this.x){
					velx = -3;
				}else if(game.getPanel().getPuck().getX() == this.getX()) {
					velx = 0;
				}
			}else if(game.getPanel().getPuck().getVel() < .5 && game.getPanel().getPuck().getY() < midline) {
				moveTo(game.getPanel().getPuck().getX(), game.getPanel().getPuck().getY());
			}else {
				moveTo(game.getWidth() / 2, 50);
			}
		}
		
		x += velx;
		y += vely;
	}
	
	public void moveTo(float x, float y) {
		Line l = new Line(this.x, this.y, x, y);
		
		velx = (int)(l.getEndX() - l.getStartX()) / 20;
		vely = (int)(l.getEndY() - l.getStartY()) / 20;
	}
	
	public float getX() {
		return x + radius;
	}
	public float getY() {
		return y + radius;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getVelX() {
		return velx;
	}
	public float getVelY() {
		return vely;
	}
	public float getRadius() {
		return radius;
	}
}
