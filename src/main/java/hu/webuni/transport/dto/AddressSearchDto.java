package hu.webuni.transport.dto;

public class AddressSearchDto {

	private String countryCode;
	private String city;
	private String zip;
	private String street;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Override
	public String toString() {
		return "AddressDto [countryCode=" + countryCode + ", city=" + city + ", zip=" + zip+ ", street=" + street + "]";
	}
	
}