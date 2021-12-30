package hu.webuni.transport.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddressDto {
	private Long addressId;
	
	@NotBlank(message = "Country ISO code cannot be null")
	@Size(min = 2, message = "Country ISO code too short}")
	@Size(max = 2, message = "Country ISO code too long}")
	private String countryCode;
	@NotBlank(message = "City cannot be null")
	private String city;
	@NotBlank(message = "Zip cannot be null")
	private String zip;
	@NotBlank(message = "Street cannot be null")
	private String street;
	@NotBlank(message = "House number cannot be null")
	private String houseNumber;
	private Double latitude;
	private Double longitude;
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
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
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "AddressDto [addressId=" + addressId + ", countryCode=" + countryCode + ", city=" + city + ", zip=" + zip
				+ ", street=" + street + ", houseNumber=" + houseNumber + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
	
}