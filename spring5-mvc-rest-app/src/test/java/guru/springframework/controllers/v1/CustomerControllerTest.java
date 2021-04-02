package guru.springframework.controllers.v1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.model.CustomerDTO;
import guru.springframework.services.CustomerService;
import guru.springframework.services.ResourceNotFoundException;

public class CustomerControllerTest extends AbstractRestControllerTest
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

        mockMvc = MockMvcBuilders.standaloneSetup( customerController )
        		.setControllerAdvice( new RestResponseEntityExceptionHandler() )
        		.build();
    }

    @Test
    public void testListCustomers() throws Exception
    {
        CustomerDTO customer1 = new CustomerDTO();
        //customer1.setId( 1L );
        customer1.setFirstname( FIRSTNAME );
        customer1.setLastname( LASTNAME );
        customer1.setCustomerUrl( CUSTOMER_URL );
        
        CustomerDTO customer2 = new CustomerDTO();
        //customer2.setId( 2L );
        customer2.setFirstname( FIRSTNAME + " 2" );
        customer2.setLastname( LASTNAME + " 2" );
        customer2.setCustomerUrl( CUSTOMER_URL  + " 2" );

        
        List<CustomerDTO> customers = Arrays.asList( customer1, customer2 );

        when( customerService.getAllCustomers() ).thenReturn( customers );

        mockMvc.perform( get( CustomerController.BASE_API_URL )
        		.accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.customers", hasSize( 2 ) ) );
    }

    @Test
    public void testGetByIdCustomer() throws Exception
    {
        CustomerDTO customer1 = new CustomerDTO();
        //customer1.setId( 1L );
        customer1.setFirstname( FIRSTNAME );
        customer1.setLastname( LASTNAME );
        customer1.setCustomerUrl( CUSTOMER_URL );

        when( customerService.getCustomerById( ArgumentMatchers.anyLong() ) ).thenReturn( customer1 );

        mockMvc.perform( get( CustomerController.BASE_API_URL + "/1" )
        		.accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                //.andExpect( jsonPath( "$.id", equalTo( 1 ) ) )
                .andExpect( jsonPath( "$.firstname", equalTo( FIRSTNAME ) ) )
                .andExpect( jsonPath( "$.lastname", equalTo( LASTNAME ) ) )
                .andExpect( jsonPath( "$.customerUrl", equalTo( CUSTOMER_URL ) ) );
    }
    
    @Test
    public void createNewCustomer() throws Exception
    {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname( "Fred" );
        customer.setLastname( "Flintstone" );

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname( customer.getFirstname() );
        returnDTO.setLastname( customer.getLastname() );
        returnDTO.setCustomerUrl( CustomerController.BASE_CUSTOMER_SHOP_URL_V1 + "1" );

        //With this sentence the test doesn't work because the service is returning a null object
        //I think this is because the object CustomerDTO doesn't implement the equals hashCode methods
        //Therefore mockito does not consider that the condition is fullfilled and 
        //it does not return the expected return object
        //when( customerService.createNewCustomer( customer ) ).thenReturn( returnDTO );
        when( customerService.createNewCustomer( ArgumentMatchers.any( CustomerDTO.class ) ) ).thenReturn( returnDTO );

        //when/then
        //MvcResult result = 
        mockMvc.perform( post( CustomerController.BASE_API_URL )
        			.accept( MediaType.APPLICATION_JSON )
                    .contentType( MediaType.APPLICATION_JSON )
                    .content( asJsonString( customer ) ) )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath( "$.firstname", equalTo( "Fred" ) ) )
                .andExpect( jsonPath( "$.customerUrl", equalTo( CustomerController.BASE_CUSTOMER_SHOP_URL_V1 + "1" ) ) )
                //this returns the response in the MvcResult object
                .andReturn();

        //Now you can write out the real content 
        //System.out.println( "->" + result.getResponse().getContentAsString() + "<-" );
    }

    @Test
    public void testUpdateCustomer() throws Exception
    {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname( "Fred" );
        customer.setLastname( "Flintstone" );

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname( customer.getFirstname() );
        returnDTO.setLastname( customer.getLastname() );
        returnDTO.setCustomerUrl( CustomerController.BASE_CUSTOMER_SHOP_URL_V1 + "1"  );

        when( customerService.saveCustomerByDTO( ArgumentMatchers.anyLong(), ArgumentMatchers.any( CustomerDTO.class ) ) )
        									.thenReturn( returnDTO );

        //when/then
        mockMvc.perform( put( CustomerController.BASE_API_URL + "/1" )
        		.accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( customer ) ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.firstname", equalTo( "Fred" ) ))
                .andExpect( jsonPath( "$.lastname", equalTo( "Flintstone" ) ) )
                .andExpect( jsonPath( "$.customerUrl", equalTo( CustomerController.BASE_CUSTOMER_SHOP_URL_V1 + "1" ) ) );
    }
    
    @Test
    public void testPatchCustomer() throws Exception
    {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname( "Fred" );

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname( customer.getFirstname() );
        returnDTO.setLastname( "Flintstone" );
        returnDTO.setCustomerUrl( CustomerController.BASE_CUSTOMER_SHOP_URL_V1 + "1" );

        when( customerService.patchCustomer( ArgumentMatchers.anyLong(), ArgumentMatchers.any( CustomerDTO.class ) ) )
        															.thenReturn( returnDTO );

        mockMvc.perform( patch( CustomerController.BASE_API_URL + "/1" )
        		.accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( customer ) ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.firstname", equalTo( "Fred" ) ) )
                .andExpect( jsonPath( "$.lastname", equalTo( "Flintstone" ) ) )
                .andExpect( jsonPath( "$.customerUrl", equalTo( CustomerController.BASE_CUSTOMER_SHOP_URL_V1 + "1" ) ) );
    }
    
    @Test
    public void testDeleteCustomer() throws Exception
    {
        mockMvc.perform( delete( CustomerController.BASE_API_URL + "/1" )
        		.accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );

        verify( customerService ).deleteCustomerById( ArgumentMatchers.anyLong() );
    }
    

    @Test
    public void testNotFoundException() throws Exception
    {
        when( customerService.getCustomerById( ArgumentMatchers.anyLong( ) ) )
        								.thenThrow( ResourceNotFoundException.class );

        mockMvc.perform( get( CustomerController.BASE_API_URL + "/222" )
        		.accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() );
    }
}
