
public class Tuple<I,J> {
	private final I i;
	private final J j;
	
	public Tuple(I i, J j) {
		this.i = i;
		this.j = j;
	}
	public I getI(){
		return this.i;
	}
	public J getJ(){
		return this.j;
	}
}
