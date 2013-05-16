package refactoring;

public class BetterAddress {

	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String state;
	private String country;

	public BetterAddress() {
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

	public BetterAddress setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public BetterAddress setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public BetterAddress setStreet(String street) {
		this.street = street;
		return this;
	}

	public BetterAddress setCity(String city) {
		this.city = city;
		return this;
	}

	public BetterAddress setState(String state) {
		this.state = state;
		return this;
	}

	public BetterAddress setCountry(String country) {
		this.country = country;
		return this;
	}

}
