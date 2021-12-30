package hu.webuni.transport.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.transport.dto.AddressSearchDto;
import hu.webuni.transport.model.Address;

@Mapper(componentModel = "spring")
public interface AddressSearchMapper {
	List<AddressSearchDto> addressesToSearchDto(List<Address> addresses);
	List<Address> searchDtoToAddresses(List<AddressSearchDto> addressSearchDto);
	AddressSearchDto addressToSearchDto(Address address);
	Address searchDtoToAddress(AddressSearchDto addressSearchDto);
}
