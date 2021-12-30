package hu.webuni.transport.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.transport.dto.AddressDto;
import hu.webuni.transport.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	List<AddressDto> addressesToDto(List<Address> addresses);
	List<Address> dtoToAddresses(List<AddressDto> addressesDto);
	AddressDto addressToDto(Address address);
	Address dtoToAddress(AddressDto addressDto);
}
