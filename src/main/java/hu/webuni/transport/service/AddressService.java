package hu.webuni.transport.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.transport.exception.InvalidAddressAttributeException;
import hu.webuni.transport.model.Address;
import hu.webuni.transport.repository.AddressRepository;

@Service
public class AddressService {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	AddressRepository addressRepository;
	
	@Transactional
	public Address save(Address address) {
		log.debug("called AddressService.save()");
		
		if(address==null) {
			throw new InvalidAddressAttributeException("Address is null!");
		}
		if(address.getAddressId()!=null) {
			throw new InvalidAddressAttributeException("Address Id is not null!");
		}
		
		return addressRepository.save(address);
	}
	
	@Transactional
	public List<Address> findAll(){
		log.debug("called AddressService.findAll()");
		
		return addressRepository.findAll();
	}
	
	@Transactional
	public Address findById(Long id){
		log.debug("called AddressService.findById()");
		
		Address address = addressRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return address;
		
	}
	
	@Transactional
	public void delete(Long id) {
		log.debug("called AddressService.delete()");
		
		addressRepository.deleteById(id);
		
		log.debug("finished AddressService.delete()");
	}
	
	@Transactional
	public Address modify(Long id, Address changedAddress) {

		log.debug("called AddressService.modify()");
		findById(id);

		changedAddress.setAddressId(id);
		
		return addressRepository.save(changedAddress);

	}
	
	public Page<Address> findAddressByExample(Address example, Pageable page){
		

		String country 		= example.getCountryCode();
		String city 		= example.getCity();
		String zip 			= example.getZip();
		String street 		= example.getStreet();
		
		Specification <Address> spec = Specification.where(null);
		
		if(StringUtils.hasText(country)) {
			spec = spec.and(AddressSpecifications.hasCountry(country));
		}
		if(StringUtils.hasText(city)) {
			spec = spec.and(AddressSpecifications.hasCity(city));
		}
		if(StringUtils.hasText(zip)) {
			spec = spec.and(AddressSpecifications.hasZip(zip));
		}
		if(StringUtils.hasText(street)) {
			spec = spec.and(AddressSpecifications.hasStreet(street));
		}
		
		return addressRepository.findAll(spec,page);
	}
		
}
