package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.model.CustomerDTO;
import guru.springframework.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//deprecated
//@Api( description = "This is my Customer Controller" )
//new way is creating tags in the Swagger Configuration
@Api( tags = { "CustomerDescription" } )
@RestController
@RequestMapping( CustomerController.BASE_API_URL )
public class CustomerController
{
	public static final String BASE_CUSTOMER_SHOP_URL_V1 = "/api/v1/shop/customer/";
	public static final String BASE_API_URL = "/api/v1/customers";
	
	private final CustomerService customerService;
	
	public CustomerController( CustomerService customerService )
	{
		this.customerService = customerService; 
	}
	
	@ApiOperation( value = "IN: This will get a list of customers.", notes = "IN: These are some notes about the API." )
	@GetMapping
	@ResponseStatus( HttpStatus.OK )
	public CustomerListDTO getAllCustomers()
	{
		CustomerListDTO customerListDTO = new CustomerListDTO();
		customerListDTO.getCustomers().addAll( customerService.getAllCustomers() );
		return customerListDTO;
	}

	@GetMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public CustomerDTO getCustomerById( @PathVariable Long id )
	{
		return customerService.getCustomerById( id );
	}

	@PostMapping
	//@RequestBody it's a new way of binding the parameter
	//It tells Spring MVC to look in the request body and parse the content to create a CustomerDTO
	@ResponseStatus( HttpStatus.CREATED )
	public CustomerDTO createNewCustomer( @RequestBody CustomerDTO customerDTO )
	{
		return customerService.createNewCustomer( customerDTO );
	}
	
	@PutMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public CustomerDTO updateCustomer( @PathVariable Long id,
														@RequestBody CustomerDTO customerDTO )
	{
		return customerService.saveCustomerByDTO( id, customerDTO ); 
	}
	
	@PatchMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public CustomerDTO patchCustomer( @PathVariable Long id,
														@RequestBody CustomerDTO customerDTO )
	{
		return customerService.patchCustomer( id, customerDTO );
	}
	
	@DeleteMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public void deleteCustomer( @PathVariable Long id )
	{
		customerService.deleteCustomerById( id );
	}
}
