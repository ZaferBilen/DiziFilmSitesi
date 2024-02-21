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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import proje.filmSitesi.requests.dizi.CreateDiziRequest;
import proje.filmSitesi.requests.dizi.UpdateDiziRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetDiziByNameResponse;
import proje.filmSitesi.service.interfaces.DiziDao;

@RestController
@RequestMapping("/dizi")
public class DiziController {
	
	private DiziDao diziDao;
	
	@Autowired
	public DiziController(DiziDao diziDao) {
		this.diziDao = diziDao;
	}
	
	@GetMapping("/getalldizi")
	public ResponseEntity<List<GetAllDiziResponse>> getAllDizi() {
        List<GetAllDiziResponse> dizi = diziDao.getAllDiziResponse();
        return ResponseEntity.ok(dizi);
    }
	
	@GetMapping("/admin/getalldizi")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<AdminGetAllDiziResponse>> adminGetAllDizi() {
        List<AdminGetAllDiziResponse> dizi = diziDao.adminGetAllDiziResponse();
        return ResponseEntity.ok(dizi);
    }
	
	 @GetMapping("/{diziName}")
	 public GetDiziByNameResponse getDiziById(@PathVariable String diziName) {
	    	
	    return diziDao.getByDiziName(diziName);
	}
	 
	 @PostMapping("/admin/add-dizi")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<String> addDizi(@RequestBody CreateDiziRequest createDiziRequest){
			
		diziDao.add(createDiziRequest);
		return ResponseEntity.ok("Dizi eklendi.");
		}

	 @PutMapping("/admin/update-dizi")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<String> updateDizi(@RequestBody UpdateDiziRequest updateDiziRequest){
			
		 diziDao.update(updateDiziRequest);
		 return ResponseEntity.ok("Dizi güncellendi.");
		 
		}
	 
	 
	 @DeleteMapping("/admin/deletedizi/{id}")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<String> deleteDizi(@PathVariable Long id){
			
		 diziDao.delete(id);
		 return ResponseEntity.ok("Dizi silindi.");
		 
		}
	 
	 @PostMapping("/admin/{diziId}/upload-kapak")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<String> uploadKapak(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) {
	     if (file.isEmpty()) {
	          return ResponseEntity.badRequest().body("Dosya seçilmedi.");
	        }

	        try {
	            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload";
	            String fileName = file.getOriginalFilename();
	            String filePath = uploadDir + File.separator + fileName;
	            diziDao.uploadKapak(diziId, filePath); 
	            File dest = new File(filePath);
	            file.transferTo(dest);


	            return ResponseEntity.ok("Dizi kapağı başarıyla yüklendi: " + fileName);
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yüklenirken bir hata oluştu.");
	        }
	    }
	    
	
	 
	 @PostMapping("/admin/{diziId}/upload-fragman")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<String> uploadFragman(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) {
	     if (file.isEmpty()) {
	          return ResponseEntity.badRequest().body("Dosya seçilmedi.");
	        }

	        try {
	            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload";
	            String fileName = file.getOriginalFilename();
	            String filePath = uploadDir + File.separator + fileName;
	            diziDao.uploadFragman(diziId, filePath); 
	            File dest = new File(filePath);
	            file.transferTo(dest);
   

	            return ResponseEntity.ok("Dizi fragmanı başarıyla yüklendi: " + fileName);
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yüklenirken bir hata oluştu.");
	        }
	    }
	 
	 
	 
	 
	 
	 
}
