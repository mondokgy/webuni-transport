package hu.webuni.transport.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.transport.dto.AddressDto;
import hu.webuni.transport.exception.InvalidAddressAttributeException;
import hu.webuni.transport.mapper.AddressMapper;
import hu.webuni.transport.model.Address;
import hu.webuni.transport.service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressMapper addressMapper;
	
	@GetMapping
	public List<AddressDto> getAll(){
		
		log.debug("Address restapi controller, /api/addresses, get, getAll start");
		
		List<AddressDto> allAddress = addressMapper.addressesToDto(addressService.findAll());
		
		log.debug("Address restapi controller, /api/addresses, get, getAll end");
		
		return allAddress;
			
	}
	
	@GetMapping("/{id}")
	public AddressDto getById(@PathVariable Long id){
		
		log.debug("Address restapi controller, /api/addresses/"+id+", get, getById start");
		
		Address address = addressService.findById(id);
				
		log.debug("Address restapi controller, /api/addresses/"+id+", get, getById start");
		
		return addressMapper.addressToDto(address);
	}
	
	@PostMapping
	public AddressDto createAddress(@Valid @RequestBody AddressDto addressDto) {
		
		log.debug("Address restapi controller, /api/addresses, post, createAddress start");		
		log.debug("Request body:"+addressDto.toString());		
		Address address = new Address();
		try {
			address = addressService.save(addressMapper.dtoToAddress(addressDto));
		}catch (InvalidAddressAttributeException e) {
			log.debug("InvalidAddressAttributeException, message: "+e.getMessage());		
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
		log.debug("Address restapi controller, /api/addresses, post, createAddress end");
		
		return  addressMapper.addressToDto(address);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AddressDto> modifyAddress(@PathVariable Long id, @Valid @RequestBody AddressDto addressDto) {
		
		log.debug("Address restapi controller, /{id}, put, modifyAddress start");
		try {
			if(id != addressDto.getAddressId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid addressId in requestBody!");
			}
			addressDto = addressMapper.addressToDto(addressService.modify(id, addressMapper.dtoToAddress(addressDto)));
			log.debug("restapi controller, /{id}, put, modifyAddress end");		
			return ResponseEntity.ok(addressDto);
		}catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AddressDto> deleteAddress(@PathVariable Long id) {
		
		log.debug("Address restapi controller, /api/addresses/\"+id+\", delete, deleteAddress start");
		try {
			addressService.delete(id);
		}catch (Exception e) {
		}
		log.debug("Address restapi controller, /api/addresses/\"+id+\", delete, deleteAddress end");
		
		return ResponseEntity.ok(null);
	}
	

	@PostMapping("/search")
	public ResponseEntity<List<AddressDto>> getByParams(@RequestBody AddressDto addressDto,Pageable page){	
		
		log.debug("Address restapi controller, /api/addresses/search, search by example start");
		
		if (addressDto == null || 
				(addressDto.getCity() == null &&
					 addressDto.getCountryCode() == null &&
					 addressDto.getStreet() == null &&
					 addressDto.getZip() == null)
			) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All search parameter are null");
		}
		
		Page<Address> addressList = addressService.findAddressByExample(addressMapper.dtoToAddress(addressDto),page);
		List<AddressDto> listAddress = addressMapper.addressesToDto(addressList.getContent());
		
		
		
		log.debug("Address restapi controller, /api/addresses/search, search by example end");

		HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Count", Long.toString(addressList.getTotalElements()));
	        
	    return new ResponseEntity<>(listAddress, headers, HttpStatus.OK);
		
	}
}
