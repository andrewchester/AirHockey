package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Puck {
	private float x, y, velx, vely;
	private int radius;
	private float vel, dir;
	private double mass;
	
	private AirHockey game;
	
	Puck(AirHockey game, int x, int y){
		this.x = x;
		this.y = y;
		
		this.vel = 5;
		this.dir = 10;
		
		this.velx = Physics.calcVels(vel, dir)[0];
		this.vely = Physics.calcVels(vel, dir)[1];
		
		this.radius = 10;
		this.mass = 1.25;
		
		this.game = game;
	}
	
	public void update() {
		Physics.collides(game.getPlayer(), this);
		Physics.collidesWall(this);
		
		this.velx = Physics.calcVels(vel, dir)[0];
		this.vely = Physics.calcVels(vel, dir)[1];
		
		vel = Physics.adjust(vel);
		
		this.x += velx;
		this.y += vely;
	}
	
	public void render(Graphics g) {
		Graphics2D g2  = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillOval((int)x, (int)y, radius * 2, radius * 2);
	}
	
	public int getRadius() {
		return (int)radius;
	}
	public float getX() {
		return x + radius;
	}
	public float getY() {
		return y + radius;
	}
	public float getVelX() {
		return velx;
	}
	public float getVelY() {
		return vely;
	}
	public void setVelX(float velX) {
		this.velx = velX;
	}
	public void setVelY(float velY) {
		this.vely = velY;
	}
	public double getMass() {
		return mass;
	}
	public void setX(float x) {
		this.x = x - radius;
	}
	public void setY(float y) {
		this.y = y - radius;
	}
	public float getVel() {
		return vel;
	}
	public float getDir() {
		return dir;
	}
	public void setVel(float vel) {
		this.vel = vel;
	}
	public void setDir(float dir) {
		this.dir = dir;
	}
}
