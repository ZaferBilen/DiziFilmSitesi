package proje.filmSitesi.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import proje.filmSitesi.requests.film.CreateFilmRequest;
import proje.filmSitesi.requests.film.UpdateFilmRequest;
import proje.filmSitesi.responses.film.AdminGetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetFilmByNameResponse;
import proje.filmSitesi.service.interfaces.FilmDao;

@RestController
@RequestMapping("/film")
public class FilmController {
	
	private FilmDao filmDao;
	
	@Autowired
	public FilmController(FilmDao filmDao) {
		this.filmDao = filmDao;
	}
	
	
	@GetMapping("/getallfilms")
	public ResponseEntity<List<GetAllFilmsResponse>> getAllLessons() {
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
	public ResponseEntity<String> addFilm(@RequestBody CreateFilmRequest createFilmRequest){
		
		filmDao.add(createFilmRequest);
		return ResponseEntity.ok("Film eklendi.");
	}
	
	
	@PutMapping("/admin/updatefilm")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateilm(@RequestBody UpdateFilmRequest updateFilmRequest){
		
		filmDao.update(updateFilmRequest);
		return ResponseEntity.ok("Film güncellendi.");
	}
	
    @DeleteMapping("/admin/deletefilm/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteFilm(@PathVariable Long id){
		
		filmDao.delete(id);
		return ResponseEntity.ok("Film silindi.");
	}
    
    
    
    @PostMapping("/admin/{filmId}/upload-film")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> uploadFilm(@PathVariable Long filmId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Dosya seçilmedi.");
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload"; 
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            filmDao.uploadFilm(filmId, filePath);
            File dest = new File(filePath);
            file.transferTo(dest);


            return ResponseEntity.ok("Film başarıyla yüklendi: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yüklenirken bir hata oluştu.");
        }
    }
    
    
    
    @PostMapping("/admin/{filmId}/upload-kapak")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> uploadKapak(@PathVariable Long filmId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Dosya seçilmedi.");
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload"; 
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            filmDao.uploadKapak(filmId, filePath);
            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok("Film kapağı başarıyla yüklendi: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yüklenirken bir hata oluştu.");
        }
    }
    
    
    
    @PostMapping("/admin/{filmId}/upload-fragman")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> uploadFragman(@PathVariable Long filmId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Dosya seçilmedi.");
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload"; 
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            filmDao.uploadFragman(filmId, filePath);
            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok("Film fragmanı başarıyla yüklendi: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yüklenirken bir hata oluştu.");
        }
    }

}
