package proje.filmSitesi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import proje.filmSitesi.requests.category.CreateFilmCategoryRequest;
import proje.filmSitesi.requests.category.UpdateFilmCategoryRequest;
import proje.filmSitesi.responses.category.AdminGetAllCategoryResponse;
import proje.filmSitesi.responses.category.GetAllFilmCategoryResponse;
import proje.filmSitesi.responses.category.GetFilmByCategoryResponse;
import proje.filmSitesi.service.interfaces.FilmCategoryDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/film-categori")  
@AllArgsConstructor
public class FilmCategoryController {

	private FilmCategoryDao filmCategoryDao;
		
	@GetMapping("/getallcategory")
	public List<GetAllFilmCategoryResponse> getAllFilmCategoryResponse(){	
		
		return filmCategoryDao.getAllFilmCategoryResponse();
	}
	
	@GetMapping("/admin/getallcategories")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<AdminGetAllCategoryResponse>> adminGetAllCategoryResponse(){
		
		List<AdminGetAllCategoryResponse> categories = filmCategoryDao.adminGetAllCategoryResponse();
		return ResponseEntity.ok(categories);

	}
	
	
	@GetMapping("/{filmCategoryName}")
	public ResponseEntity<List<GetFilmByCategoryResponse>> getFilmByCategory(@PathVariable String filmCategoryName){
	
		List<GetFilmByCategoryResponse> filmCategoryResponses = filmCategoryDao.getFilmByCategoryResponse(filmCategoryName);
			
		return new ResponseEntity<>(filmCategoryResponses, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/admin/add-film-category")  
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> add(@RequestBody CreateFilmCategoryRequest createFilmCategoryRequest) {  
		filmCategoryDao.add(createFilmCategoryRequest);
        return ResponseEntity.ok(Map.of("message" ,"Film Kategorisi eklendi."));
    }

	
	@PutMapping("/admin/update-film-category")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> update(@RequestBody UpdateFilmCategoryRequest updateFilmCategoryRequest) {			
		filmCategoryDao.update(updateFilmCategoryRequest);
        return ResponseEntity.ok(Map.of("message","Film Kategorisi güncellendi"));
    }
	
	
	@DeleteMapping("/admin/delete-film-category/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<Object> delete(@PathVariable int id){				
		filmCategoryDao.delete(id);
		return ResponseEntity.ok(Map.of("message","Kategori silindi." ));
	}
	

}




