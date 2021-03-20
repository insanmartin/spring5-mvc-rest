package guru.springfamework.services;

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

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;

@RunWith( SpringRunner.class )
//brings up a small part of the Spring context -> the repositories
@DataJpaTest
public class CustomerServiceImplIT
{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception
    {
        System.out.println( "Loading Customer Data" );
        System.out.println( customerRepository.findAll().size() );

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap( categoryRepository, customerRepository );
        bootstrap.run(); //load data

        customerService = new CustomerServiceImpl( CustomerMapper.INSTANCE, customerRepository );
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception 
    {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne( id );
        assertNotNull( originalCustomer );
        //save original first name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname( updatedName );

        customerService.patchCustomer( id, customerDTO );

        Customer updatedCustomer = customerRepository.findById( id ).get();

        assertNotNull( updatedCustomer );
        assertEquals( updatedName, updatedCustomer.getFirstName());
        assertNotEquals( originalFirstName, updatedCustomer );
        assertEquals( originalLastName, updatedCustomer.getLastName() );
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception
    {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne( id );
        assertNotNull( originalCustomer );

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname( updatedName );

        customerService.patchCustomer( id, customerDTO );

        Customer updatedCustomer = customerRepository.findById( id ).get();

        assertNotNull( updatedCustomer );
        assertEquals( updatedName, updatedCustomer.getLastName() );
        assertEquals( originalFirstName, updatedCustomer.getFirstName() );
        assertNotEquals( originalLastName, updatedCustomer.getLastName() );
    }

    private Long getCustomerIdValue()
    {
        List<Customer> customers = customerRepository.findAll();

        System.out.println( "Customers Found: " + customers.size() );

        //return first id
        return customers.get(0).getId();
    }
}
