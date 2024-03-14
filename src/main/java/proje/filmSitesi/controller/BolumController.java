package proje.filmSitesi.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import proje.filmSitesi.responses.dizi.BolumResponse;
import proje.filmSitesi.service.interfaces.BolumDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/bolum")  
@AllArgsConstructor
public class BolumController {
	
	private BolumDao bolumDao;
	
	@PostMapping("/admin/addbolum")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BolumResponse> addBolum(@RequestBody AddBolumRequest addBolumRequest){  
		
		BolumResponse bolumResponse = bolumDao.addBolum(addBolumRequest);
		return ResponseEntity.ok(bolumResponse);
		
	}
	
	@PutMapping("/admin/updatebolum")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BolumResponse> updateBolum(@RequestBody UpdateBolumRequest updateBolumRequest) {		
        
		BolumResponse bolumResponse = bolumDao.updateBolum(updateBolumRequest);
        return ResponseEntity.ok(bolumResponse);
        
    }

	
    @DeleteMapping("/admin/deletebolum/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BolumResponse> deleteBolum(@PathVariable Long id) {		
       
    	BolumResponse bolumResponse = bolumDao.deleteBolum(id);
        return ResponseEntity.ok(bolumResponse);
        
    }
    

    

    @PostMapping("/admin/{bolumId}/upload-bolum")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BolumResponse> uploadBolum(@PathVariable Long bolumId, @RequestPart("file") MultipartFile file) throws InterruptedException {
        try {
            return ResponseEntity.ok(bolumDao.uploadBolum(bolumId, file));
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    
    
}
