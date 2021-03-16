package guru.springfamework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;

//Spring Boot specific class, only works with it
@Component
public class Bootstrap implements CommandLineRunner
{
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;

	public Bootstrap( CategoryRepository categoryRepository,
						CustomerRepository customerRepository )
	{
		super();
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	public void run( String... args ) throws Exception
	{
		loadCategories();
		loadCustomers();
	}
	
	private void loadCategories() throws Exception
	{
        Category fruits = new Category();
        fruits.setName( "Fruits" );

        Category dried = new Category();
        dried.setName( "Dried" );

        Category fresh = new Category();
        fresh.setName( "Fresh" );

        Category exotic = new Category();
        exotic.setName( "Exotic" );

        Category nuts = new Category();
        nuts.setName( "Nuts" );

        categoryRepository.save( fruits );
        categoryRepository.save( dried );
        categoryRepository.save( fresh );
        categoryRepository.save( exotic );
        categoryRepository.save( nuts );

        System.out.println( "Data Loaded, categories = " + categoryRepository.count() );
	}
	
	private void loadCustomers() throws Exception
	{
		Long id = 1L;
		
        Customer david = new Customer();
        david.setId( id++ );
        david.setFirstName( "David" );
        david.setLastName( "Winter" );

        Customer alice = new Customer();
        alice.setId( id++ );
        alice.setFirstName( "Alice" );
        alice.setLastName( "Eastman" );

        Customer michael = new Customer();
        michael.setId( id++ );
        michael.setFirstName( "Micheal" );
        michael.setLastName( "Weston" );

        Customer sam = new Customer();
        sam.setId( id++ );
        sam.setFirstName( "Sam" );
        sam.setLastName( "Axe" );

        Customer joe = new Customer();
        joe.setId( id++ );
        joe.setFirstName( "Joe" );
        joe.setLastName( "Buck" );

        Customer claudette = new Customer();
        claudette.setId( id++ );
        claudette.setFirstName( "Claudette" );
        claudette.setLastName( "Morell" );

        Customer jingle = new Customer();
        jingle.setId( id++ );
        jingle.setFirstName( "Jingle" );
        jingle.setLastName( "Joe" );

        Customer mama = new Customer();
        mama.setId( id++ );
        mama.setFirstName( "Mama" );
        mama.setLastName( "Merkel" );
        
        
        customerRepository.save( david );
      	customerRepository.save( alice );
      	customerRepository.save( michael );
      	customerRepository.save( sam );
      	customerRepository.save( joe );
      	customerRepository.save( claudette );
      	customerRepository.save( jingle );
      	customerRepository.save( mama );

        System.out.println( "Data Loaded, customers = " + customerRepository.count() );
	}
}
