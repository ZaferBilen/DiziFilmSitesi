package proje.filmSitesi.controller;

import java.util.List;

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
import proje.filmSitesi.requests.category.CreateDiziCategoryRequest;
import proje.filmSitesi.requests.category.UpdateDiziCategoryRequest;
import proje.filmSitesi.responses.category.AdminGetAllDiziCategoryResponse;
import proje.filmSitesi.responses.category.GetAllDiziCategoryResponse;
import proje.filmSitesi.responses.category.GetDiziByCategoryResponse;
import proje.filmSitesi.service.interfaces.DiziCategoryDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/dizi-categori")  
@AllArgsConstructor
public class DiziCategoryController {
	
	private DiziCategoryDao diziCategoryDao;
	
	@GetMapping("/getalldizicategory")
	public List<GetAllDiziCategoryResponse> getAllDiziCategoryResponse(){
		
		return diziCategoryDao.getAllDiziCategoryResponse();
	}
	
	
	@GetMapping("/admin/getalldizicategory")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<AdminGetAllDiziCategoryResponse>> adminGetAllCategoryResponse(){
		
		List<AdminGetAllDiziCategoryResponse> categories = diziCategoryDao.adminGetAllDiziCategoryResponse();
		return ResponseEntity.ok(categories);

	}
	
	@GetMapping("/{diziCategoryName}")
	public ResponseEntity<List<GetDiziByCategoryResponse>> getDiziByCategory(@PathVariable String diziCategoryName){
		
		List<GetDiziByCategoryResponse> diziCategoryResponses = diziCategoryDao.getDiziByCategoryResponse(diziCategoryName);
			
		return new ResponseEntity<>(diziCategoryResponses, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/admin/add-dizi-category")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> add(@RequestBody CreateDiziCategoryRequest createDiziCategoryRequest) {
		diziCategoryDao.add(createDiziCategoryRequest);
        return ResponseEntity.ok("Dizi Kategorisi eklendi.");
    }
	
	
	@PutMapping("/admin/add-dizi-category")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> update(@RequestBody UpdateDiziCategoryRequest updateDiziCategoryRequest) {
		diziCategoryDao.update(updateDiziCategoryRequest);
        return ResponseEntity.ok("Dizi Kategorisi güncellendi.");
    }
	
	
	@DeleteMapping("/admin/delete-dizi-category/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<String> delete(@PathVariable int id){
		diziCategoryDao.delete(id);
		return ResponseEntity.ok("Kategori silindi." );
	}

}
