package hu.webuni.transport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="ADDRESS_SEQUENCE_GENERATOR", sequenceName="ADDRESS_SEQUENCE", initialValue=1, allocationSize=10)
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ADDRESS_SEQUENCE_GENERATOR")
	private Long addressId;
	
	private String countryCode;
	private String city;
	private String zip;
	private String street;
	private String houseNumber;
	private Double latitude;
	private Double longitude;

	public Address() {

	}

	public Address(Long addressId, String countryCode, String city, String zip, String street, String houseNumber,
			Double latitude, Double longitude) {
		super();
		this.addressId = addressId;
		this.countryCode = countryCode;
		this.city = city;
		this.zip = zip;
		this.street = street;
		this.houseNumber = houseNumber;
		this.latitude = latitude;
		this.longitude = longitude;
	}

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
		return "Address [addressId=" + addressId + ", countryCode=" + countryCode + ", city=" + city + ", zip=" + zip
				+ ", street=" + street + ", houseNumber=" + houseNumber + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
		
}
