package guru.springfamework.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;

public class CustomerControllerTest
{
	public static final String FIRSTNAME = "Ivan";
	public static final String LASTNAME = "Nicolas";
	public static final String CUSTOMER_URL = "url/customer/example";

    @Mock
    CustomerService customerService;

    //this is going to automatically inject that Customer Service into the controller
    //so we don't have to build the controller
    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );

        mockMvc = MockMvcBuilders.standaloneSetup( customerController ).build();
    }

    @Test
    public void testListCustomers() throws Exception
    {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId( 1L );
        customer1.setFirstname( FIRSTNAME );
        customer1.setLastname( LASTNAME );
        customer1.setCustomer_url( CUSTOMER_URL );
        
        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId( 2L );
        customer2.setFirstname( FIRSTNAME + " 2" );
        customer2.setLastname( LASTNAME + " 2" );
        customer2.setCustomer_url( CUSTOMER_URL  + " 2" );

        
        List<CustomerDTO> customers = Arrays.asList( customer1, customer2 );

        when( customerService.getAllCustomers() ).thenReturn( customers );

        mockMvc.perform( get( "/api/v1/customers/" )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.customers", hasSize( 2 ) ) );
    }

    @Test
    public void testGetByIdCustomer() throws Exception
    {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId( 1L );
        customer1.setFirstname( FIRSTNAME );
        customer1.setLastname( LASTNAME );
        customer1.setCustomer_url( CUSTOMER_URL );

        when( customerService.getCustomerById( ArgumentMatchers.anyLong() ) ).thenReturn( customer1 );

        mockMvc.perform( get( "/api/v1/customers/1" )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id", equalTo( 1 ) ) )
                .andExpect( jsonPath( "$.firstname", equalTo( FIRSTNAME ) ) )
                .andExpect( jsonPath( "$.lastname", equalTo( LASTNAME ) ) )
                .andExpect( jsonPath( "$.customer_url", equalTo( CUSTOMER_URL ) ) );
    }
}
