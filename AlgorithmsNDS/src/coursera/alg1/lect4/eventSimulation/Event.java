package coursera.alg1.lect4.eventSimulation;

public class Event implements Comparable<Event>{
	private double time;
	private final Particle p1;
	private final Particle p2;
	private final int cp1;
	private final int cp2;
	
	public Event(double time, Particle p1, Particle p2) {
		this.time = time;
		this.p1 = p1;
		this.p2 = p2;
		this.cp1 = p1 == null? -1: p1.getCollisionCount();
		this.cp2 = p2==null? -1: p2.getCollisionCount();
	}

	@Override
	public int compareTo(Event that) {
		return Double.compare(time, that.time);
	}

	public Particle getP1() {
		return p1;
	}

	public Particle getP2() {
		return p2;
	}
	
	public boolean isValid(){
		if(p1 != null && p1.getCollisionCount() != cp1){
			return false;
		}
		if(p2 != null && p2.getCollisionCount() != cp2){
			return false;
		}
		
		return true;
	}

	public double getTime() {
		return time;
	}
}
