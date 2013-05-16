package refactoring;

public class Address {

	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String state;
	private String country;

	public Address(String string, String string2, String string3,
			String string4, String string5, String string6) {
		this.firstName = string;
		this.lastName = string2;
		this.street = string3;
		this.city = string4;
		this.state = string5;
		this.country = string6;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

}
