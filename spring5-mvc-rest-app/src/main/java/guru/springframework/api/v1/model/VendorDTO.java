package guru.springframework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO
{
	public static final String BASE_VENDOR_SHOP_URL_V1 = "/api/v1/shop/vendors/";

	@ApiModelProperty( value = "Vendor's name", required = true )
    private String name;
    
    //this field is not in the domain because it's calculated at Service
    //@JsonProperty( "customer_url" ) -> to change the name in the JSON conversion of the POJO
    private String vendor_url;
}
