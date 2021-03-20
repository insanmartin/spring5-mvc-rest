package guru.springfamework.api.v1.model;

import lombok.Data;

@Data
public class CustomerDTO
{
	public static final String BASE_CUSTOMER_URL_V1 = "/api/v1/shop/customer/";

    private Long id;
    private String firstname;
    private String lastname;
    
    //this field is not in the domain because it's calculated at Service
    //@JsonProperty( "customer_url" ) -> to change the name in the JSON conversion of the POJO
    private String customer_url;
}
