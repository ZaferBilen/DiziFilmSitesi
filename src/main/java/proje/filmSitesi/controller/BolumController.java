package proje.filmSitesi.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

import lombok.AllArgsConstructor;
import proje.filmSitesi.dao.BolumDao;
import proje.filmSitesi.requests.dizi.AddBolumRequest;
import proje.filmSitesi.requests.dizi.UpdateBolumRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllBolumResponse;
import proje.filmSitesi.responses.dizi.GetAllBolumResponse;

@RestController
@RequestMapping("/bolum")  
@AllArgsConstructor
public class BolumController {
	
	private BolumDao bolumDao;
	
	@PostMapping("/admin/addbolum")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addBolum(AddBolumRequest addBolumRequest){
		
		bolumDao.addBolum(addBolumRequest);
		return ResponseEntity.ok("Bölüm yüklendi.");
		
	}
	
	@PutMapping("/admin/updatebolum")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateBolum(@RequestBody UpdateBolumRequest updateBolumRequest) {
        
		bolumDao.updateBolum(updateBolumRequest);
        return ResponseEntity.ok("Bölüm güncellendi.");
        
    }

	
    @DeleteMapping("/admin/deletebolum/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteBolum(@PathVariable Long id) {
       
    	bolumDao.deleteBolum(id);
        return ResponseEntity.ok("Bölüm silindi.");
        
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
    public ResponseEntity<String> uploadBolum(@PathVariable Long bolumId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Dosya seçilmedi.");
        }

        try {
            String uploadDir = "C:\\Users\\Zafer\\Desktop\\Upload";
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;

            bolumDao.uploadBolum(bolumId, filePath); 

            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok("Bölüm dosyası başarıyla yüklendi: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yüklenirken bir hata oluştu.");
        }
    }
    
    
    
    
}
