package guru.springframework.services;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;

public class VendorServiceTest
{
	public static final long ID = 1L;
	public static final String NAME = "Ivan Fruit Co";
	
	VendorService vendorService;
	
	@Mock
	VendorRepository vendorRepository;
	
   @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );

        vendorService = new VendorServiceImpl( VendorMapper.INSTANCE, vendorRepository );
    }

    @Test
    public void getAllVendors() throws Exception
    {
        //given
        List<Vendor> vendors = Arrays.asList( new Vendor(), 
       											new Vendor(),
       											new Vendor() );
        given( vendorRepository.findAll() ).willReturn( vendors );

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        then( vendorRepository ).should( times( 1 ) ).findAll();
        assertEquals( 3, vendorDTOS.size() );
    }
    
    @Test
    public void getVendorById() throws Exception
    {
        //given
        Vendor vendor = new Vendor();
        vendor.setId( ID );
        vendor.setName( NAME );

        //mockito BDD syntax
        given( vendorRepository.findById( ArgumentMatchers.anyLong() ) ).willReturn( Optional.of( vendor ) );
        //when( vendorRepository.findById( ArgumentMatchers.anyLong() ) ).thenReturn( Optional.of( vendor ) );

        //when
        VendorDTO vendorDTO = vendorService.getVendorById( ID );

        //then
        then( vendorRepository ).should( times( 1 ) ).findById( ArgumentMatchers.anyLong() );
        
        assertEquals( NAME, vendorDTO.getName() );
    }
    
    @Test( expected = ResourceNotFoundException.class )
    public void getVendorByIdNotFound() throws Exception
    {
        //given
        //mockito BBD syntax since mockito 1.10.0
        given( vendorRepository.findById( ArgumentMatchers.anyLong() ) ).willReturn( Optional.empty() );

        //when
        //VendorDTO vendorDTO = 
        vendorService.getVendorById( 1L );

        //then
        then( vendorRepository ).should( times( 1 ) ).findById( ArgumentMatchers.anyLong() );
    }
    
    @Test
    public void createNewVendor() throws Exception 
    {
        //given
    	VendorDTO vendorDTO = new VendorDTO();
    	vendorDTO.setName( "Jim Co" );

        Vendor savedVendor = new Vendor();
        savedVendor.setName( vendorDTO.getName() );
        savedVendor.setId( 1L );

        given( vendorRepository.save( ArgumentMatchers.any( Vendor.class ) ) ).willReturn( savedVendor );

        //when
        VendorDTO savedDto = vendorService.createNewVendor( vendorDTO );

        //then
        // 'should' defaults to times = 1
        then( vendorRepository ).should().save( ArgumentMatchers.any( Vendor.class ) );
        assertEquals( vendorDTO.getName(), savedDto.getName() );
        assertEquals( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1", savedDto.getVendor_url() );
    }

    @Test
    public void saveVendorByDTO() throws Exception
    {
        //given
    	VendorDTO vendorDTO = new VendorDTO();
    	vendorDTO.setName( "Jim Co" );

    	Vendor savedVendor = new Vendor();
    	savedVendor.setName( vendorDTO.getName() );
        savedVendor.setId( 1L );

        given( vendorRepository.save( ArgumentMatchers.any( Vendor.class ) ) ).willReturn( savedVendor );

        //when
        VendorDTO savedDto = vendorService.saveVendorByDTO( 1L, vendorDTO );

        //then
        // 'should' defaults to times = 1
        then( vendorRepository ).should().save( ArgumentMatchers.any( Vendor.class ) );
        assertEquals( vendorDTO.getName(), savedDto.getName() );
        assertEquals( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1", savedDto.getVendor_url() );
    }
    
    @Test
    public void patchVendor() throws Exception
    {
        //given
    	VendorDTO vendorDTO = new VendorDTO();
    	vendorDTO.setName( "Jim Co" );

    	Vendor savedVendor = new Vendor();
    	savedVendor.setName( vendorDTO.getName() );
        savedVendor.setId( 1L );    	

        given( vendorRepository.findById( ArgumentMatchers.anyLong() ) ).willReturn( Optional.of( savedVendor ) );
        given( vendorRepository.save( ArgumentMatchers.any( Vendor.class ) ) ).willReturn( savedVendor );

        //when

        VendorDTO savedVendorDTO = vendorService.patchVendor( 1L, vendorDTO );

        //then
        // 'should' defaults to times = 1
        then( vendorRepository ).should().save( ArgumentMatchers.any( Vendor.class ) );
        then( vendorRepository ).should( times( 1 ) ).findById( ArgumentMatchers.anyLong() );
        assertThat( savedVendorDTO.getVendor_url(), containsString( "1" ) );
    }
    
    @Test
    public void deleteVendorById() throws Exception
    {
        //when
        vendorService.deleteVendorById( 1L );

        //then
        then( vendorRepository ).should().deleteById( ArgumentMatchers.anyLong() );
    }

}
