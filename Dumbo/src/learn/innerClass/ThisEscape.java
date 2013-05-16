package learn.innerClass;

import java.util.Date;

public class ThisEscape {
	private final int num;

	public ThisEscape(EventSource source) {
		num = 42;
		EventListener eventListener = new EventListener(){
			@Override
			public void onEvent(Event e){
				doSomething(e);
			}
		};
		System.out.println(eventListener.getClass());
		source.registerEventListener(
				eventListener);
	}

	protected void doSomething(Event e) {
		if(num != 42){
			System.out.println("Race condition detected at:"+new Date()+"]");
		}
	}
}
