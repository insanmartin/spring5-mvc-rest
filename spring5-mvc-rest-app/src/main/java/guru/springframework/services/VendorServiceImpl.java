package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService
{
	private final VendorMapper vendorMapper;
	private final VendorRepository vendorRepository;
	
	public VendorServiceImpl( VendorMapper vendorMapper,
								VendorRepository vendorRepository )
	{
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}
	
	private String getVendorUrl( Long id )
	{
		return VendorDTO.BASE_VENDOR_SHOP_URL_V1 + id;
	}
	
	@Override
	public List<VendorDTO> getAllVendors()
	{
		return vendorRepository.findAll()
								.stream()
								.map( vendor -> { 
									VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO( vendor );
									vendorDTO.setVendor_url( getVendorUrl( vendor.getId() ) );
									return vendorDTO;
								} )
								.collect( Collectors.toList() );
	}
	
	@Override
	public VendorDTO getVendorById( Long id )
	{
		return vendorRepository.findById( id )
								.map( vendor -> { 
									VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO( vendor );
									vendorDTO.setVendor_url( getVendorUrl( vendor.getId() ) );
									return vendorDTO;
								} )
								.orElseThrow( ResourceNotFoundException::new );
	}

	private VendorDTO saveAndReturnDTO( Vendor vendor )
	{
		Vendor savedVendor = vendorRepository.save( vendor );
		
		VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO( savedVendor );
		returnVendorDTO.setVendor_url( getVendorUrl( savedVendor.getId() ) );
		
		return returnVendorDTO;	
	}
	
	@Override
	public VendorDTO createNewVendor( VendorDTO vendorDTO )
	{
		return saveAndReturnDTO( vendorMapper.vendorDtoToVendor( vendorDTO ) );
	}
	
	@Override
	public VendorDTO saveVendorByDTO( Long id, VendorDTO vendorDTO )
	{
		Vendor vendor = vendorMapper.vendorDtoToVendor( vendorDTO );
		vendor.setId( id );
		
		return saveAndReturnDTO( vendor );
	}
	
	@Override
	public VendorDTO patchVendor( Long id, VendorDTO vendorDTO )
	{
		return vendorRepository.findById( id ).map( vendor -> {
			
			if( vendorDTO.getName() != null )
				vendor.setName( vendorDTO.getName() );
			
			VendorDTO patchedVendor = vendorMapper.vendorToVendorDTO( vendorRepository.save( vendor ) );
			patchedVendor.setVendor_url( getVendorUrl( vendor.getId() ) );
			
			return patchedVendor;
		}).orElseThrow( ResourceNotFoundException::new );
	}
	
	@Override
	public void deleteVendorById( Long id )
	{
		vendorRepository.deleteById( id );
	}
}
