package test.simple.webservice.samples;

public class SampleService {
	public void sayHi(){
		System.out.println("SImple hello from my webService");
	}
	
	public void sayHiIn(String input){
		System.out.println(input);
	}
	
	public String sayHiInOut(String input){
		System.out.println(input);
		return "StringResponse";
	}
	
	public float sayHiInOutadd(int x, float f){
		return x+f;
	}
}
