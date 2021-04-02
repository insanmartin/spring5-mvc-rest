package guru.springframework.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig //extends WebMvcConfigurationSupport
{
	@Bean
	//Docket is a Swagger configuration
	public Docket api()
	{
		//this is the standard configuration
		//there are other types of configurations
		return new Docket( DocumentationType.SWAGGER_2 )
				.select()
				//any APIs and any Request Handler
				//we can configure withs API we want to expose
				.apis( RequestHandlerSelectors.any() )
				.paths( PathSelectors.any() )
				.build()
				.pathMapping( "/" )
				//definition of tags – name = value
				.tags( new Tag( "CustomerDescription", "IN: This is my Customer Controller" ),
						new Tag( "VendorDescription", "This is my Vendor Controller" ) )
				.apiInfo( metaData() );
	}
	
	
    private ApiInfo metaData()
    {
        Contact contact = new Contact( 
        		"Iván Nicolás",	//name
        		"https://github.com/insanmartin/spring5-mvc-rest", //url
                "insanmartin@gmail.com" ); //email

        return new ApiInfo(
                "Spring Framework Guru",	//title
                "Spring Framework 5: Beginner to Guru",	//description
                "1.0",	//version
                "Terms of Service: blah",	//terms
                contact,	//contact info
                "Apache License Version 2.0",	//license
                "https://www.apache.org/licenses/LICENSE-2.0", //licence URL
                new ArrayList<>() );	//vendor extensions
    }
	
	
/*	
	//Spring Boot configures the Swagger UI automatically for us
	//if we are not using Spring Boot we can do the configuration manually with this code
	@Override
	protected void addResourceHandlers( ResourceHandlerRegistry registry )
	{
		registry.addResourceHandler( "swagger-ui.html" )
				.addResourceLocations( "classpath:/META-INF/resources/" );
		
		registry.addResourceHandler( "/webjars/**" )
				.addResourceLocations( "classpath:/META-INF/resources/webjars/" );
	}
*/
}
