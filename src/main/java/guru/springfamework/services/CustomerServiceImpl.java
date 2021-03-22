package guru.springfamework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;

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
					customerDTO.setCustomer_url( getCustomerUrl( customer.getId() ) );
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
					customerDTO.setCustomer_url( getCustomerUrl( customer.getId() ) );
					return customerDTO; 
				} )
				.orElseThrow( ResourceNotFoundException::new );
	}
	
	private CustomerDTO saveAndReturnDTO( Customer customer )
	{
		Customer savedCustomer = customerRepository.save( customer );
		
		CustomerDTO returnCustomerDTO = customerMapper.customerToCustomerDTO( savedCustomer );
		returnCustomerDTO.setCustomer_url( getCustomerUrl( savedCustomer.getId() ) );
		
		return returnCustomerDTO;	
	}
	
	@Override
	public CustomerDTO createNewCustomer( CustomerDTO customerDTO )
	{
		return saveAndReturnDTO( customerMapper.customerDtoToCustomer( customerDTO ) );
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
					customer.setFirstName( customerDTO.getFirstname() );
				
				if( customerDTO.getLastname() != null )
					customer.setLastName( customerDTO.getLastname() );
				
				CustomerDTO patchedCustomer = customerMapper.customerToCustomerDTO( customerRepository.save( customer ) );
				patchedCustomer.setCustomer_url( getCustomerUrl( customer.getId() ) );
				
				return patchedCustomer;
			}).orElseThrow( ResourceNotFoundException::new );
	}
	
	private String getCustomerUrl( Long id )
	{
		return CustomerDTO.BASE_CUSTOMER_SHOP_URL_V1 + id;
	}
	
	@Override
	public void deleteCustomerById( Long id )
	{
		customerRepository.deleteById( id );
	}
}
