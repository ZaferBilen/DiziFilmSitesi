package proje.filmSitesi.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import proje.filmSitesi.requests.dizi.AddBolumRequest;
import proje.filmSitesi.requests.dizi.UpdateBolumRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllBolumResponse;
import proje.filmSitesi.responses.dizi.BolumResponse;
import proje.filmSitesi.responses.dizi.GetAllBolumResponse;
import proje.filmSitesi.service.interfaces.BolumDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/bolum")  
@AllArgsConstructor
public class BolumController {
	
	private BolumDao bolumDao;
	
	@PostMapping("/admin/addbolum")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> addBolum(@RequestBody AddBolumRequest addBolumRequest){  
		
		BolumResponse bolumResponse = bolumDao.addBolum(addBolumRequest);
		return ResponseEntity.ok(Map.of("message","Bölüm yüklendi." ,"bölüm" , bolumResponse));
		
	}
	
	@PutMapping("/admin/updatebolum")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateBolum(@RequestBody UpdateBolumRequest updateBolumRequest) {		
        
		BolumResponse bolumResponse = bolumDao.updateBolum(updateBolumRequest);
        return ResponseEntity.ok(Map.of("message","Bölüm güncellendi." ,"bölüm" , bolumResponse));
        
    }

	
    @DeleteMapping("/admin/deletebolum/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteBolum(@PathVariable Long id) {		
       
    	BolumResponse bolumResponse = bolumDao.deleteBolum(id);
        return ResponseEntity.ok(Map.of("message","Bölüm silindi.","bölüm" , bolumResponse));
        
    }
    

    @GetMapping("/getallbolums")
    public ResponseEntity<List<GetAllBolumResponse>> getAllBolumResponse() {
       
    	List<GetAllBolumResponse> bolum = bolumDao.getAllBolumResponse();
        return ResponseEntity.ok(bolum);
        
    }

    
    @GetMapping("/admin/getallbolums")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AdminGetAllBolumResponse>> adminGetAllBolumResponse() {
        
    	List<AdminGetAllBolumResponse> bolum = bolumDao.adminGetAllBolumResponse();
        return ResponseEntity.ok(bolum);
        
    }
    

    
    @PostMapping("/admin/{bolumId}/upload-bolum")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> uploadBolum(@PathVariable Long bolumId, @RequestPart("file") MultipartFile file) {		
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dosya seçilmedi."));
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload";
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;

            BolumResponse bolumResponse = bolumDao.uploadBolum(bolumId, filePath); 
            
            if(bolumResponse != null) {
            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok(Map.of("message", "Bölüm dosyası başarıyla yüklendi: " + fileName, "dizi" , bolumResponse));
            }else {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Bölüm bulunamadı.")); 
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Dosya yüklenirken bir hata oluştu."));
        }
    }
    
    
    
    
}
