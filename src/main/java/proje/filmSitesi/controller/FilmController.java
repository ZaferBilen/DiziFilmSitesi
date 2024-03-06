package proje.filmSitesi.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import proje.filmSitesi.requests.film.CreateFilmRequest;
import proje.filmSitesi.requests.film.UpdateFilmRequest;
import proje.filmSitesi.responses.film.AdminGetAllFilmsResponse;
import proje.filmSitesi.responses.film.FilmResponse;
import proje.filmSitesi.responses.film.GetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetFilmByNameResponse;
import proje.filmSitesi.service.interfaces.FilmDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/film")
public class FilmController {
	
	private FilmDao filmDao;
	
	@Autowired
	public FilmController(FilmDao filmDao) {
		this.filmDao = filmDao;
	}
	
	
	@GetMapping("/getallfilms")
	public ResponseEntity<List<GetAllFilmsResponse>> getAllFilms() {
        List<GetAllFilmsResponse> films = filmDao.getAllFilmsResponse();
        return ResponseEntity.ok(films);
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/admin/getallfilms")
	public ResponseEntity<List<AdminGetAllFilmsResponse>> adminGetAllFilms() {
        List<AdminGetAllFilmsResponse> films = filmDao.adminGetAllFilmsResponse();
        return ResponseEntity.ok(films);
    }
	
	
	 @GetMapping("/{filmName}")
	 public GetFilmByNameResponse getFilmById(@PathVariable String filmName) {
	    	
	    return filmDao.getByFilmName(filmName);
	}
	 
	 
	@PostMapping("/admin/addfilm")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> addFilm(@RequestBody CreateFilmRequest createFilmRequest){			
		
		FilmResponse filmResponse = filmDao.add(createFilmRequest);
		return ResponseEntity.ok(Map.of("message","Film eklendi.","film" , filmResponse));
	}
	
	
	@PutMapping("/admin/updatefilm")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateilm(@RequestBody UpdateFilmRequest updateFilmRequest){				
		
		FilmResponse filmResponse = filmDao.update(updateFilmRequest);
		return ResponseEntity.ok(Map.of("message","Film güncellendi.","film" , filmResponse));
	}
	
    @DeleteMapping("/admin/deletefilm/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> deleteFilm(@PathVariable Long id){				
		
    	FilmResponse filmResponse = filmDao.delete(id);
		return ResponseEntity.ok(Map.of("message","Film silindi.","film" , filmResponse));
	}
    
    
    
    @PostMapping("/admin/{filmId}/upload-film")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> uploadFilm(@PathVariable Long filmId, @RequestPart("file") MultipartFile file) {			
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dosya seçilmedi."));
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload"; 
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            FilmResponse filmResponse = filmDao.uploadFilm(filmId, filePath);
            if(filmResponse != null ) {
            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok(Map.of("message", "Film başarıyla yüklendi: " + fileName, "film" , filmResponse));
            }else {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Film bulunamadı.")); 
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Dosya yüklenirken bir hata oluştu."));
        }
    }
    
    
    
    @PostMapping("/admin/{filmId}/upload-kapak")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> uploadKapak(@PathVariable Long filmId, @RequestPart("file") MultipartFile file) {				
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dosya seçilmedi."));
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload"; 
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            FilmResponse filmResponse = filmDao.uploadKapak(filmId, filePath);
            if(filmResponse != null ) {
            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok(Map.of("message", "Film kapağı başarıyla yüklendi: " + fileName, "film" , filmResponse));
            }else {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Film bulunamadı.")); 
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Dosya yüklenirken bir hata oluştu."));
        }
    }
    
    
    
    @PostMapping("/admin/{filmId}/upload-fragman")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> uploadFragman(@PathVariable Long filmId, @RequestPart("file") MultipartFile file) {			
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dosya seçilmedi."));
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload"; 
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            FilmResponse filmResponse = filmDao.uploadFragman(filmId, filePath);
            if(filmResponse != null ) {
            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok(Map.of("message", "Film fragmanı başarıyla yüklendi: " + fileName, "film" , filmResponse));
            }else {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Film bulunamadı.")); 
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Dosya yüklenirken bir hata oluştu."));
        }
    }

}
