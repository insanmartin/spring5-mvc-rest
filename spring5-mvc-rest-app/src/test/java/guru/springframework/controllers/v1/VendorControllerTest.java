package guru.springframework.controllers.v1;

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.services.ResourceNotFoundException;
import guru.springframework.services.VendorService;

@RunWith(SpringRunner.class)
//brings up the web frontend part of the Spring context
//I'm telling Spring the class I want to wired to MockMvc in the @Autowired annotation
@WebMvcTest( controllers = {VendorController.class} )
public class VendorControllerTest
{
	public static final String NAME = "Ivan Co";
	public static final String VENDOR_URL = "url/vendor/example";

    @MockBean  //provided by Spring Context
    VendorService vendorService;
    
    @Autowired
    MockMvc mockMvc; //provided by Spring Context

    @Test
    public void testListVendors() throws Exception
    {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName( NAME );
        vendor1.setVendor_url( VENDOR_URL );
        
        VendorDTO vendor2 = new VendorDTO();
        vendor2.setName( NAME + " 2" );
        vendor2.setVendor_url( VENDOR_URL  + " 2" );

        VendorDTO vendor3 = new VendorDTO();
        vendor3.setName( NAME + " 3" );
        vendor3.setVendor_url( VENDOR_URL  + " 3" );
        
        List<VendorDTO> vendors = Arrays.asList( vendor1, vendor2, vendor3 );

        
        given( vendorService.getAllVendors() ).willReturn( vendors );

        mockMvc.perform( get( VendorController.BASE_API_URL )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.vendors", hasSize( 3 ) ) );
    }
    
    @Test
    public void testGetByIdVendor() throws Exception
    {
    	VendorDTO vendor1 = new VendorDTO();
    	vendor1.setName( NAME );
        vendor1.setVendor_url( VENDOR_URL );

        given( vendorService.getVendorById( ArgumentMatchers.anyLong() ) ).willReturn( vendor1 );

        mockMvc.perform( get( VendorController.BASE_API_URL + "/1" )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.name", equalTo( NAME ) ) )
                .andExpect( jsonPath( "$.vendor_url", equalTo( VENDOR_URL ) ) );
    }

    @Test
    public void createNewVendor() throws Exception
    {
        //given
    	VendorDTO vendor = new VendorDTO();
    	vendor.setName( "Fred Co" );

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName( vendor.getName() );
        returnDTO.setVendor_url( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1" );

        given( vendorService.createNewVendor( vendor ) ).willReturn( returnDTO );

        //when/then
        //MvcResult result = 
        mockMvc.perform( post( VendorController.BASE_API_URL )
                    .contentType( MediaType.APPLICATION_JSON )
                    .content( asJsonString( vendor ) ) )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath( "$.name", equalTo( "Fred Co" ) ) )
                .andExpect( jsonPath( "$.vendor_url", equalTo( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1" ) ) )
                .andReturn();
        
        //System.out.println( "->" + result.getResponse().getContentAsString() + "<-" );
    }

    @Test
    public void testUpdateVendor() throws Exception
    {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName( "Fred Co" );

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName( vendor.getName() );
        returnDTO.setVendor_url( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1"  );

        given( vendorService.saveVendorByDTO( ArgumentMatchers.anyLong(), ArgumentMatchers.any( VendorDTO.class ) ) )
        									.willReturn( returnDTO );

        //when/then
        mockMvc.perform( put( VendorController.BASE_API_URL + "/1" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( vendor ) ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.name", equalTo( "Fred Co" ) ))
                .andExpect( jsonPath( "$.vendor_url", equalTo( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1" ) ) );
    }
    
    @Test
    public void testPatchVendor() throws Exception
    {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName( "Fred Co" );

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName( vendor.getName() );
        returnDTO.setVendor_url( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1" );

        given( vendorService.patchVendor( ArgumentMatchers.anyLong(), ArgumentMatchers.any( VendorDTO.class ) ) )
        															.willReturn( returnDTO );

        mockMvc.perform( patch( VendorController.BASE_API_URL + "/1" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( vendor ) ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.name", equalTo( "Fred Co" ) ) )
                .andExpect( jsonPath( "$.vendor_url", equalTo( VendorDTO.BASE_VENDOR_SHOP_URL_V1 + "1" ) ) );
    }
    
    @Test
    public void testDeleteVendor() throws Exception
    {
        mockMvc.perform( delete( VendorController.BASE_API_URL + "/1" )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );

        then( vendorService ).should().deleteVendorById( ArgumentMatchers.anyLong() );
    }
    

    @Test
    public void testNotFoundException() throws Exception
    {
        given( vendorService.getVendorById( ArgumentMatchers.anyLong( ) ) )
        								.willThrow( ResourceNotFoundException.class );

        mockMvc.perform( get( VendorController.BASE_API_URL + "/222" )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() );
    }
}
