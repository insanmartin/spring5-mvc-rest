package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( tags = { "VendorDescription" } )
@RestController
@RequestMapping( VendorController.BASE_API_URL )
public class VendorController
{
	public static final String BASE_API_URL = "/api/v1/vendors";
	
	private final VendorService vendorService;
	
	public VendorController( VendorService vendorService )
	{
		this.vendorService = vendorService; 
	}

	@ApiOperation( value = "This will get a list of vendors.", notes = "Fruits vendors recorded." )
	@GetMapping
	@ResponseStatus( HttpStatus.OK )
	public VendorListDTO getAllVendors()
	{
		return new VendorListDTO( vendorService.getAllVendors() );
	}
	
	@ApiOperation( value = "Get information of a vendor.", notes = "You need the vendor's ID." )
	@GetMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public VendorDTO getVendorById( @PathVariable Long id )
	{
		return vendorService.getVendorById( id );
	}
	
	@ApiOperation( value = "Create a new vendor." )
	@PostMapping
	//@RequestBody it's a new way of binding the parameter
	//It tells Spring MVC to look in the request body and parse the content to create a VendorDTO
	@ResponseStatus( HttpStatus.CREATED )
	public VendorDTO createNewVendor( @RequestBody VendorDTO VendorDTO )
	{
		return vendorService.createNewVendor( VendorDTO );
	}
	
	@ApiOperation( value = "Update information of a vendor." )
	@PutMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public VendorDTO updateVendor( @PathVariable Long id,
									@RequestBody VendorDTO VendorDTO )
	{
		return vendorService.saveVendorByDTO( id, VendorDTO ); 
	}
	
	@ApiOperation( value = "Update a vendor property." )
	@PatchMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public VendorDTO patchVendor( @PathVariable Long id,
									@RequestBody VendorDTO VendorDTO )
	{
		return vendorService.patchVendor( id, VendorDTO );
	}
	
	@ApiOperation( value = "Delete a vendor." )
	@DeleteMapping( "/{id}" )
	@ResponseStatus( HttpStatus.OK )
	public void deleteVendor( @PathVariable Long id )
	{
		vendorService.deleteVendorById( id );
	}
}
