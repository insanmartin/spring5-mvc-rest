package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    
    @Test
    public void createNewCustomer() throws Exception 
    {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname( "Jim" );

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName( customerDTO.getFirstname() );
        savedCustomer.setLastName( customerDTO.getLastname() );
        savedCustomer.setId( 1L );

        when( customerRepository.save( ArgumentMatchers.any( Customer.class ) ) ).thenReturn( savedCustomer );

        //when
        CustomerDTO savedDto = customerService.createNewCustomer( customerDTO );

        //then
        assertEquals( customerDTO.getFirstname(), savedDto.getFirstname() );
        assertEquals( CustomerDTO.BASE_CUSTOMER_SHOP_URL_V1 + "1", savedDto.getCustomer_url() );
    }

    @Test
    public void saveCustomerByDTO() throws Exception
    {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname( "Jim" );

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName( customerDTO.getFirstname() );
        savedCustomer.setLastName( customerDTO.getLastname() );
        savedCustomer.setId( 1L );

        when( customerRepository.save( ArgumentMatchers.any( Customer.class ) ) ).thenReturn( savedCustomer );

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO( 1L, customerDTO );

        //then
        assertEquals( customerDTO.getFirstname(), savedDto.getFirstname() );
        assertEquals( CustomerDTO.BASE_CUSTOMER_SHOP_URL_V1 + "1", savedDto.getCustomer_url() );
    }
    
    @Test
    public void deleteCustomerById() throws Exception
    {
        Long id = 1L;

        customerService.deleteCustomerById( id );

        verify( customerRepository, times( 1 ) ).deleteById( ArgumentMatchers.anyLong() );
    }
}
