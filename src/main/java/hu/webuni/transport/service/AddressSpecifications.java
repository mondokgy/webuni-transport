package hu.webuni.transport.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import hu.webuni.transport.model.Address;
import hu.webuni.transport.model.Address_;

public class AddressSpecifications {
	public static Specification<Address> hasCountry(String country){
		return (root,cq,cb) -> cb.equal(root.get(Address_.countryCode), country);
	}
	public static Specification<Address> hasCity(String city){
		return (root,cq,cb) -> cb.like(cb.upper(root.get(Address_.city)), StringUtils.upperCase(city) + "%" );
	}
	public static Specification<Address> hasZip(String zip) {
		return (root,cq,cb) -> cb.equal(root.get(Address_.zip),zip);
	}
	public static Specification<Address> hasStreet(String street) {
		return (root,cq,cb) -> cb.like(cb.upper(root.get(Address_.street)), StringUtils.upperCase(street) + "%" );
	}

}
