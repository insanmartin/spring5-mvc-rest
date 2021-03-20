package guru.springfamework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;

@Mapper
public interface CustomerMapper
{
	CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );
	
	@Mapping( target = "customer_url", ignore = true )
	@Mapping( source = "firstName", target = "firstname" )
	@Mapping( source = "lastName", target = "lastname" )
	CustomerDTO customerToCustomerDTO( Customer customer );
	
	@Mapping( source = "firstname", target = "firstName" )
	@Mapping( source = "lastname", target = "lastName" )
	Customer customerDtoToCustomer( CustomerDTO customerDTO );
}
