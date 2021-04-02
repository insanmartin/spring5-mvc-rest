package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;

@RunWith( SpringRunner.class )
//brings up a small part of the Spring context -> the repositories
@DataJpaTest
public class CustomerServiceImplIT
{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception
    {
        System.out.println( "Loading Customer Data" );
        System.out.println( customerRepository.findAll().size() );

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap( categoryRepository, customerRepository, vendorRepository );
        bootstrap.run(); //load data

        customerService = new CustomerServiceImpl( CustomerMapper.INSTANCE, customerRepository );
    }

    @Test
    public void patchCustomerUpdateFirstname() throws Exception 
    {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne( id );
        assertNotNull( originalCustomer );
        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname( updatedName );

        customerService.patchCustomer( id, customerDTO );

        Customer updatedCustomer = customerRepository.findById( id ).get();

        assertNotNull( updatedCustomer );
        assertEquals( updatedName, updatedCustomer.getFirstname() );
        assertNotEquals( originalFirstName, updatedCustomer.getFirstname() );
        assertEquals( originalLastName, updatedCustomer.getLastname() );
    }

    @Test
    public void patchCustomerUpdateLastname() throws Exception
    {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne( id );
        assertNotNull( originalCustomer );

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname( updatedName );

        customerService.patchCustomer( id, customerDTO );

        Customer updatedCustomer = customerRepository.findById( id ).get();

        assertNotNull( updatedCustomer );
        assertEquals( updatedName, updatedCustomer.getLastname() );
        assertEquals( originalFirstName, updatedCustomer.getFirstname() );
        assertNotEquals( originalLastName, updatedCustomer.getLastname() );
    }

    private Long getCustomerIdValue()
    {
        List<Customer> customers = customerRepository.findAll();

        System.out.println( "Customers Found: " + customers.size() );

        //return first id
        return customers.get(0).getId();
    }
}
