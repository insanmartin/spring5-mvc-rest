package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;

//@RestController returns a ResponseBody, combines @Controller and @ResponseBody
//when we were using @Controller we force to return ResponseEntity 
//(quite similar to ResponseBody) but ResponseBody gives us more control
@RestController
@RequestMapping( CategoryController.BASE_API_URL )
public class CategoryController
{
	public static final String BASE_API_URL = "/api/v1/categories"; 
	
	private final CategoryService categoryService;
	
	public CategoryController( CategoryService categoryService )
	{
		this.categoryService = categoryService; 
	}

	//now Springs binds the return object to ResponseBody for us
	//we set up the status we want to return by the new annotation @ResponseStatus
	@GetMapping
	@ResponseStatus( HttpStatus.OK )
	public CategoryListDTO getAllCategories()
	{
		return new CategoryListDTO( categoryService.getAllCategories() ); 
	}
	
	@GetMapping( "/{name}" )
	@ResponseStatus( HttpStatus.OK )
	public CategoryDTO getCategoryByName( @PathVariable String name )
	{
		return categoryService.getCategoryByName( name );
	}
}
