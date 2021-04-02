package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import guru.springframework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService
{
	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;
	
	public CustomerServiceImpl( CustomerMapper customerMapper,
								CustomerRepository customerRepository )
	{
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}
	
	@Override
	public List<CustomerDTO> getAllCustomers()
	{
		return customerRepository
				.findAll()
				.stream()
				.map( customer -> { 
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO( customer ); 
					customerDTO.setCustomerUrl( getCustomerUrl( customer.getId() ) );
					return customerDTO; 
				} )
				.collect( Collectors.toList() );
	}

	@Override
	public CustomerDTO getCustomerById( Long id )
	{
		return customerRepository.findById( id )
				.map( customer -> { 
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO( customer ); 
					customerDTO.setCustomerUrl( getCustomerUrl( customer.getId() ) );
					return customerDTO; 
				} )
				.orElseThrow( ResourceNotFoundException::new );
	}
	
	private CustomerDTO saveAndReturnDTO( Customer customer )
	{
		Customer savedCustomer = customerRepository.save( customer );
		
		CustomerDTO returnCustomerDTO = customerMapper.customerToCustomerDTO( savedCustomer );
		returnCustomerDTO.setCustomerUrl( getCustomerUrl( savedCustomer.getId() ) );
		
		return returnCustomerDTO;	
	}
	
	@Override
	public CustomerDTO createNewCustomer( CustomerDTO customerDTO )
	{
		Customer customer = customerMapper.customerDtoToCustomer( customerDTO );
		return saveAndReturnDTO( customer );
	}
	
	@Override
	public CustomerDTO saveCustomerByDTO( Long id, CustomerDTO customerDTO )
	{
		Customer customer = customerMapper.customerDtoToCustomer( customerDTO );
		customer.setId( id );
		
		return saveAndReturnDTO( customer );
	}

	@Override
	public CustomerDTO patchCustomer( Long id, CustomerDTO customerDTO )
	{
		return customerRepository.findById( id ).map( customer -> {
			
				if( customerDTO.getFirstname() != null )
					customer.setFirstname( customerDTO.getFirstname() );
				
				if( customerDTO.getLastname() != null )
					customer.setLastname( customerDTO.getLastname() );
				
				CustomerDTO patchedCustomer = customerMapper.customerToCustomerDTO( customerRepository.save( customer ) );
				patchedCustomer.setCustomerUrl( getCustomerUrl( customer.getId() ) );
				
				return patchedCustomer;
			}).orElseThrow( ResourceNotFoundException::new );
	}
	
	private String getCustomerUrl( Long id )
	{
		return CustomerController.BASE_CUSTOMER_SHOP_URL_V1 + id;
	}
	
	@Override
	public void deleteCustomerById( Long id )
	{
		customerRepository.deleteById( id );
	}
}
