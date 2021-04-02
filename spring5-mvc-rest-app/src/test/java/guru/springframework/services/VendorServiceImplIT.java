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

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;

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
