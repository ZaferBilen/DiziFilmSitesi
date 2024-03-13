package proje.filmSitesi.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

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
	 public ResponseEntity<DiziResponse> addDizi(@RequestBody CreateDiziRequest createDiziRequest){			
			
		DiziResponse diziResponse = diziDao.add(createDiziRequest);
		return ResponseEntity.ok(diziResponse);
		}

	 @PutMapping("/admin/update-dizi")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<DiziResponse> updateDizi(@RequestBody UpdateDiziRequest updateDiziRequest){			
			
		 DiziResponse diziResponse = diziDao.update(updateDiziRequest);
		 return ResponseEntity.ok(diziResponse);
		 
		}
	 
	 @DeleteMapping("/admin/deletedizi/{id}")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<DiziResponse> deleteDizi(@PathVariable Long id){			
			
		 DiziResponse diziResponse = diziDao.delete(id);
		 return ResponseEntity.ok(diziResponse);
		 
		}
	 
	 @PostMapping("/admin/{diziId}/upload-kapak")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<DiziResponse> uploadKapak(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) {
	     try {
	         DiziResponse response = diziDao.uploadKapak(diziId, file);
	         return ResponseEntity.ok(response);
	     } catch (IOException | GeneralSecurityException e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	     }
	 }
	
	 
	 @PostMapping("/admin/{diziId}/upload-fragman")
	 @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<DiziResponse> uploadFragman(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) {
	     try {
	         DiziResponse response = diziDao.uploadFragman(diziId, file);
	         return ResponseEntity.ok(response);
	     } catch (IOException | GeneralSecurityException e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	     }
	 }
	 
	/* 
	 @PostMapping("/admin/{diziId}/upload-kapak")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<DiziResponse> uploadKapak(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) {
	        try {
	            System.out.println("1)");
	            try {
	                // 2 saniye uyuma
	                Thread.sleep(3000);
	            } catch (InterruptedException e) {
	                // Uyku kesintiye uğrarsa (interrupted), burası çalışır
	                e.printStackTrace();
	            }
	            System.out.println("2)");
	            DiziResponse diziResponse = diziDao.uploadKapak(diziId, file);
	            return ResponseEntity.ok(diziResponse);
	        } catch (IOException | GeneralSecurityException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    @PostMapping("/admin/{diziId}/upload-fragman")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<DiziResponse> uploadFragman(@PathVariable Long diziId, @RequestPart("file") MultipartFile file) {
	        try {
	            DiziResponse diziResponse = diziDao.uploadFragman(diziId, file);
	            return ResponseEntity.ok(diziResponse);
	        } catch (IOException | GeneralSecurityException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	 
	 */
}
