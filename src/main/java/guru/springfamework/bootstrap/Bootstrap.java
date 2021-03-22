package guru.springfamework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;

//Spring Boot specific class, only works with it
@Component
public class Bootstrap implements CommandLineRunner
{
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;
	private VendorRepository vendorRepository;

	public Bootstrap( CategoryRepository categoryRepository,
						CustomerRepository customerRepository,
						VendorRepository vendorRepository )
	{
		super();
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	public void run( String... args ) throws Exception
	{
		loadCategories();
		loadCustomers();
		loadVendors();
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
	
	private void loadVendors() throws Exception
	{
        Vendor western = new Vendor();
        western.setName( "Western Tasty Fruits Ltd." );
        
        Vendor exotic = new Vendor();
        exotic.setName( "Exotic Fruits Company" );
        
        Vendor home = new Vendor();
        home.setName( "Home Fruits" );
        
        Vendor fun = new Vendor();
        fun.setName( "Fun Fresh Fruits Ltd." );
        
        Vendor nuts = new Vendor();
        nuts.setName( "Nuts for Nuts Company" );
        
        Vendor franks = new Vendor();
        franks.setName( "Franks Fresh Fruits from France Ltd." );
        
        Vendor frische = new Vendor();
        frische.setName( "Frische Früchte GmbH" );

        vendorRepository.save( western );
        vendorRepository.save( exotic );
        vendorRepository.save( home );
        vendorRepository.save( fun );
        vendorRepository.save( nuts );
        vendorRepository.save( franks );
        vendorRepository.save( frische );

        System.out.println( "Data Loaded, vendors = " + vendorRepository.count() );
	}
}
