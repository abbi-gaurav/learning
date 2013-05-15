package coursera.alg1.lect4.eventSimulation;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class Particle {
	private double rx,  ry;
	private double vx,  vy;
	private final double radius;
	private final double mass;
	private int count;
	private final Color color;
	public Particle(double x, double y, double vx, double vy, double radius, double mass, Color color) {
		this.rx = x;
		this.ry = y;
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
		this.mass = mass;
		this.color = color;
	}
	
	public double timeToHit(Particle that) {
		
		if(this == that){ 
			return Double.POSITIVE_INFINITY;
		}
		
		double dvx = that.vx-this.vx;
		double dvy = that.vy-this.vy;
		
		double drx = that.rx-this.rx;
		double dry = that.ry - this.ry;
		
		double dvdr = (dvx * drx) + (dvy * dry);
		
		if (dvdr >= 0){
			return Double.POSITIVE_INFINITY;
		}
		
		double dvdv = (dvx*dvx) + (dvy*dvy);
		double drdr = (drx*drx) + (dry*dry);
		double sigma = that.radius+this.radius;
		double d = (dvdr*dvdr) - (dvdv)*(drdr-(sigma*sigma));
		
		if(d < 0){
			return Double.POSITIVE_INFINITY;
		}
		
		return -((dvdr+Math.sqrt(d))/dvdv);
	}
	
	public double timeToHitHorizontalWall() { 
		if(vy == 0){
			return Double.POSITIVE_INFINITY;
		}
		
		return vy > 0 ? (1-radius-ry)/vy : (radius-ry)/vy;
	}
	
	public double timeToHitVerticalWall() { 
		if(vx == 0){
			return Double.POSITIVE_INFINITY;
		}
		
		return vx > 0 ? (1-radius-rx)/vx : (radius-rx)/vx;
	}
	
	public void bounceOffVerticalWall(){
		this.vx = -vx;
		this.count++;
	}
	
	public void bounceOffHorizontalWall(){
		this.vy = -vy;
		this.count++;
	}
	
	public void bounceOff(Particle that){
		double dvx = that.vx-this.vx;
		double dvy = that.vy-this.vy;
		
		double drx = that.rx-this.rx;
		double dry = that.ry - this.ry;
		
		double dvdr = (dvx * drx) + (dvy * dry);
		double sigma = that.radius+this.radius;
		
		double impulse = (2 * this.mass* that.mass * (dvdr))/(sigma * (this.mass+that.mass));
		double jx = (impulse*drx)/sigma;
		double jy = (impulse*dry)/sigma;
		
		this.vx = this.vx+(jx/this.mass);
		that.vx = that.vx-(jx/that.mass);
		this.vy = this.vy+(jy/this.mass);
		that.vy = that.vy-(jy/that.mass);
		
		this.count++;
		that.count++;
	}
	
	public int getCollisionCount(){
		return count;
	}
	
	public void move(double dt){
//		if((rx+vx*dt < radius) || rx+vx*dt > 1.0-radius){
//			vx = -vx;
//		}
//		if((ry+vy*dt < radius) || ry+vy*dt > 1.0-radius){
//			vy = -vy;
//		}
		
		rx = rx+vx*dt;
		ry = ry+vy*dt;
	}
	
	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, radius);
	}
}
