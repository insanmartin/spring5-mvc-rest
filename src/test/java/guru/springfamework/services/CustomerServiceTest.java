package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;

public class CustomerServiceTest 
{
	public static final long ID = 1L;
	public static final String FIRSTNAME = "Ivan";
	public static final String LASTNAME = "Nicolas";
	
	CustomerService customerService;
	
	@Mock
	CustomerRepository customerRepository;
	
   @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );

        customerService = new CustomerServiceImpl( CustomerMapper.INSTANCE, customerRepository );
    }

    @Test
    public void getAllCustomers() throws Exception
    {
        //given
        List<Customer> customers = Arrays.asList( new Customer(), 
        											new Customer(),
        											new Customer(),
        											new Customer() );

        when( customerRepository.findAll() ).thenReturn( customers );

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals( 4, customerDTOS.size() );
    }

    @Test
    public void getCustomerById() throws Exception
    {
        //given
        Customer customer = new Customer();
        customer.setId( ID );
        customer.setFirstName( FIRSTNAME );
        customer.setLastName( LASTNAME );

        when( customerRepository.findById( ArgumentMatchers.anyLong() ) ).thenReturn( Optional.of( customer ) );

        //when
        CustomerDTO customerDTO = customerService.getCustomerById( ID );

        //then
        assertEquals( Long.valueOf( ID ), customerDTO.getId() );
        assertEquals( FIRSTNAME, customerDTO.getFirstname() );
        assertEquals( LASTNAME, customerDTO.getLastname() );
    }
}
