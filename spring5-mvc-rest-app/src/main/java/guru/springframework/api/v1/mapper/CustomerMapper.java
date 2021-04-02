package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;

@Mapper
public interface CustomerMapper
{
	CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );
	
	//@Mapping( target = "customerUrl", ignore = true )
	//@Mapping( source = "firstName", target = "firstname" )
	//@Mapping( source = "lastName", target = "lastname" )
	CustomerDTO customerToCustomerDTO( Customer customer );
	
	//@Mapping( source = "firstname", target = "firstName" )
	//@Mapping( source = "lastname", target = "lastName" )
	Customer customerDtoToCustomer( CustomerDTO customerDTO );
}
