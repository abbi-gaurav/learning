package coursera.alg1.lect4.eventSimulation;

import java.awt.Color;

import coursera.alg1.lect4.BHBasedMinPrioritQ;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

public class CollidingSystem {
	private Particle[] particles;
	private double t = 0.0;
	private final BHBasedMinPrioritQ<Event> eventQueue;
	
	public CollidingSystem(Particle[] particles) {
		this.particles = particles;
		this.eventQueue = new BHBasedMinPrioritQ<>(particles.length);
	}

	public static void main(String[] args) {
		new CollidingSystem(getParticles(args[0])).simulate();
	}

	private static Particle[] getParticles(String fileName) {
		In in = new In(fileName);
		Particle[] ps = new Particle[in.readInt()];
		for(int i=0;i<ps.length;i++){
			ps[i] = new Particle(in.readDouble(), in.readDouble(),
					in.readDouble(), in.readDouble(), in.readDouble(),
					in.readDouble(), new Color(in.readInt(), in.readInt(),
							in.readInt()));
		}
		return ps;
	}

	private void simulate() {
		for(int i=0;i<particles.length;i++){
			predict(particles[i]);
		}
		eventQueue.insert(new Event(0, null, null));
		
		while(!eventQueue.isEmpty()){
			Event event = eventQueue.delMin();
			
			if(!event.isValid()){
				continue;
			}
			
			Particle a = event.getP1();
			Particle b = event.getP2();
			
			for(int i=0;i<particles.length;i++){
				particles[i].move(event.getTime()-t);
			}
			
			t = event.getTime();
			
			if		(a != null && b != null)	a.bounceOff(b);
			else if	(a == null && b != null)	b.bounceOffHorizontalWall();
			else if	(a != null && b == null)	a.bounceOffVerticalWall();
			else if	(a == null && b == null)	redraw();
			
			predict(a);
			predict(b);
		}
	}
	
	private void redraw() {
		StdDraw.clear();
		for(int i=0;i<particles.length;i++){
			particles[i].draw();
		}
		StdDraw.show(50);
		eventQueue.insert(new Event(t+2, null, null));
	}

	private void predict(Particle a){
		if(a == null) return;
		for(int i = 0; i<particles.length;i++){
			double dt = a.timeToHit(particles[i]);
			if(dt == Double.POSITIVE_INFINITY){
				continue;
			}
			eventQueue.insert(new Event(t+dt, a, particles[i]));
		}
		eventQueue.insert(new Event(t+a.timeToHitVerticalWall(), a, null));
		eventQueue.insert(new Event(t+a.timeToHitHorizontalWall(), null, a));
	}
}
