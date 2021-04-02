package guru.springframework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class oldCustomerDTO
{
    private Long id;
    
    @ApiModelProperty( value = "IN: This is the first name", required = true )
    private String firstname;
    
    @ApiModelProperty( required = true )
    private String lastname;
    
    //this field is not in the domain because it's calculated at Service
    //@JsonProperty( "customer_url" ) -> to change the name in the JSON conversion of the POJO
    private String customer_url;
}
