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
	
	@Mapping( source = "firstName", target = "firstname" )
	@Mapping( source = "lastName", target = "lastname" )
	CustomerDTO customerToCustomerDTO( Customer customer );
}
