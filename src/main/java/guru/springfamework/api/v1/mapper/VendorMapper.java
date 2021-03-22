package guru.springfamework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;

@Mapper
public interface VendorMapper 
{
	VendorMapper INSTANCE = Mappers.getMapper( VendorMapper.class );
	
	@Mapping( target = "vendor_url", ignore = true )
	VendorDTO vendorToVendorDTO( Vendor vendor );
	
	Vendor vendorDtoToVendor( VendorDTO vendorDTO );
}
