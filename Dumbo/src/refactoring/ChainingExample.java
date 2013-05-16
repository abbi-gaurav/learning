package refactoring;

public class ChainingExample {
	public static void main(String[] args) {
		badWay();
		goodWay();
	}

	private static void goodWay() {
		//Self documented
		new BetterAddress()
		.setFirstName("Joe")
		.setLastName("Smith")
		.setStreet("21st Street")
		.setCity("SomeCity")
		.setState("RefactoryState")
		.setCountry("CleanCodeCountry");
		//TODO:always do a validate
	}

	public static void badWay() {
		new Address("Joe","Smith","21st Street","SomeCity","RefactoryState","CleanCodeCountry");
	}
}
