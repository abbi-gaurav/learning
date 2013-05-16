
public interface RandomizedQueue<Item> extends Iterable<Item>{

	public abstract boolean isEmpty();

	public abstract int size();

	public abstract void enqueue(Item item);

	public abstract Item dequeue();

	public abstract Item sample();

}