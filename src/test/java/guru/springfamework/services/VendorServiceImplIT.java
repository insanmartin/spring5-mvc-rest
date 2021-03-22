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

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;

@RunWith( SpringRunner.class )
//brings up a small part of the Spring context -> the repositories
@DataJpaTest
public class VendorServiceImplIT
{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception
    {
        System.out.println( "Loading Vendor Data" );
        System.out.println( vendorRepository.findAll().size() );

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap( categoryRepository, customerRepository, vendorRepository );
        bootstrap.run(); //load data

        vendorService = new VendorServiceImpl( VendorMapper.INSTANCE, vendorRepository );
    }

    @Test
    public void patchVendorUpdateName() throws Exception 
    {
        String updatedName = "UpdatedName";
        long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne( id );
        assertNotNull( originalVendor );
        String originalName = originalVendor.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName( updatedName );

        vendorService.patchVendor( id, vendorDTO );

        Vendor updatedVendor = vendorRepository.findById( id ).get();

        assertNotNull( updatedVendor );
        assertEquals( updatedName, updatedVendor.getName());
        assertNotEquals( originalName, updatedVendor.getName() );
    }

    private Long getVendorIdValue()
    {
        List<Vendor> vendors = vendorRepository.findAll();

        System.out.println( "Vendors Found: " + vendors.size() );

        //return first id
        return vendors.get(0).getId();
    }
}
