package guru.springfamework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;

public class CustomerMapperTest
{
	public static final long ID = 1L;
	public static final String FIRSTNAME = "Ivan";
	public static final String LASTNAME = "Nicolas";
			
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
    @Test
    public void categoryToCategoryDTO() throws Exception
    {
        //given
        Customer customer = new Customer();
        customer.setId( ID );
        customer.setFirstName( FIRSTNAME );
        customer.setLastName( LASTNAME );
        
        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO( customer );

        //then
        assertEquals( Long.valueOf( ID ), customerDTO.getId() );
        assertEquals( FIRSTNAME, customerDTO.getFirstname() );
        assertEquals( LASTNAME, customerDTO.getLastname() );
    }	
}
