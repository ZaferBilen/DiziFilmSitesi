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

import proje.filmSitesi.requests.dizi.CreateDiziRequest;
import proje.filmSitesi.requests.dizi.UpdateDiziRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllDiziResponse;
import proje.filmSitesi.responses.dizi.DiziResponse;
import proje.filmSitesi.responses.dizi.GetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetDiziByNameResponse;
import proje.filmSitesi.service.interfaces.DiziDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	 public ResponseEntity<Object> addDizi(@RequestBody CreateDiziRequest createDiziRequest){			
			
		DiziResponse diziResponse = diziDao.add(createDiziRequest);
		return ResponseEntity.ok(Map.of("message","Dizi eklendi" + diziResponse ));
		}

	 @PutMapping("/admin/update-dizi")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<Object> updateDizi(@RequestBody UpdateDiziRequest updateDiziRequest){			
			
		 DiziResponse diziResponse = diziDao.update(updateDiziRequest);
		 return ResponseEntity.ok(Map.of("message","Dizi güncellendi" + diziResponse ));
		 
		}
	 
	 
	 @DeleteMapping("/admin/deletedizi/{id}")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<Object> deleteDizi(@PathVariable Long id){			
			
		 DiziResponse diziResponse = diziDao.delete(id);
		 return ResponseEntity.ok(Map.of("message", "Dizi silindi." + diziResponse));
		 
		}
	 
	 @PostMapping("/admin/{diziId}/upload-kapak")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<Object> uploadKapak(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) { 		
	    
		 if (file.isEmpty()) {
	         return ResponseEntity.badRequest().body(Map.of("error", "Dosya seçilmedi."));
	     }

	     try {
	         String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload";
	         String fileName = file.getOriginalFilename();
	         String filePath = uploadDir + File.separator + fileName;
	         DiziResponse diziResponse = diziDao.uploadKapak(diziId, filePath);
	         if(diziResponse != null) { 
	         File dest = new File(filePath);
	         file.transferTo(dest);

	         return ResponseEntity.ok(Map.of("status", "success", "message", "Dizi kapağı başarıyla yüklendi: " + fileName , "dizi" , diziResponse));
	         }else {
	        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Dizi bulunamadı.")); 
	         }
	     } catch (IOException e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", "Dosya yüklenirken bir hata oluştu."));
	     }
	 }
	
	 
	 @PostMapping("/admin/{diziId}/upload-fragman")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<Object> uploadFragman(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) {  
	     if (file.isEmpty()) {
	         return ResponseEntity.badRequest().body(Map.of("error", "Dosya seçilmedi."));
	     }

	     try {
	         String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload";
	         String fileName = file.getOriginalFilename();
	         String filePath = uploadDir + File.separator + fileName;
	         DiziResponse diziResponse = diziDao.uploadFragman(diziId, filePath); 
	         if(diziResponse != null) { 
	         File dest = new File(filePath);
	         file.transferTo(dest);

	         return ResponseEntity.ok(Map.of("message", "Dizi fragmanı başarıyla yüklendi: " + fileName, "dizi" , diziResponse));
	         }else {
	        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Dizi bulunamadı.")); 
	         }
	     } catch (IOException e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Dosya yüklenirken bir hata oluştu."));
	     }
	 }
	 
	 
	 
	 
	 
	 
}
