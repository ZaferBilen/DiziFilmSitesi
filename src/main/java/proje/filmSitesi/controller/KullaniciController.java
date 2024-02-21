package proje.filmSitesi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.KullaniciRepository;
import proje.filmSitesi.requests.kullanici.EmailRequest;
import proje.filmSitesi.requests.kullanici.KullaniciGirisRequests;
import proje.filmSitesi.requests.kullanici.KullaniciKayitRequests;
import proje.filmSitesi.service.interfaces.KullaniciDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping
public class KullaniciController {
	
	@Autowired
	private KullaniciDao kullaniciDao;
	
	@Autowired
	private KullaniciRepository kullaniciRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/")
    public String anaSayfa(){
        return "Film sitesine hoşgeldiniz";
    }
	
	@PostMapping("/kullanici/kod")
	public ResponseEntity<Object> dogrulamaKodu(@RequestBody EmailRequest emailRequest) {
	    String email = emailRequest.getEmail();
	    kullaniciDao.dogrulamaKoduGonder(email);
	    return ResponseEntity.ok(Map.of("message","Doğrulama kodu e-postanıza gönderildi."));
	}
	
	@PostMapping("/kullanici/kayıt")
	public ResponseEntity<Object> kullaniciKaydi(@RequestBody KullaniciKayitRequests kullaniciKayitRequests) {

	    if (!kullaniciDao.sifreKural(kullaniciKayitRequests.getSifre())) {
	        return ResponseEntity.badRequest().body("Şifreniz en az 6 karakter uzunluğunda olmalı ve bir büyük harf, bir küçük harf ve bir rakam içermelidir.");
	    }

	    boolean basarili = kullaniciDao.kullaniciKayit(kullaniciKayitRequests);
	    if (basarili) {
	        return ResponseEntity.ok("Kullanıcı kaydı başarıyla tamamlandı."); //
	    } else {
	        return ResponseEntity.status(400).body("Geçersiz doğrulama kodu veya kayıt başarısız oldu.");
	    }
	}
	
	@GetMapping("/users/all")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(kullaniciDao.getAllUsers());
    }
	
	@PostMapping("/giris")
	public ResponseEntity<String> kimlikDogrulama(@RequestBody KullaniciGirisRequests kullaniciGirisRequests) {
	    
	    Authentication authentication = authenticationManager
	            .authenticate(new UsernamePasswordAuthenticationToken(kullaniciGirisRequests.getEmail(), kullaniciGirisRequests.getSifre()));
	   
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    return new ResponseEntity<>("Kullanıcı başarıyla giriş yaptı", HttpStatus.OK);
	}
	

	@DeleteMapping("/kullanici/sil/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		kullaniciDao.delete(id);
        return ResponseEntity.ok("Kullanıcı silindi");
    }
	
	@DeleteMapping("/kullanici/hesap-sil")
    public ResponseEntity<String> deleteMyAccount() {
        Long currentUserId = getCurrentUserId();
        kullaniciDao.delete(currentUserId);
        return ResponseEntity.ok("Hesabınız başarıyla silindi");
    }
	
	private Long getCurrentUserId() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	    Kullanici kullanici = kullaniciRepository.findByEmail(userDetails.getUsername()).orElseThrow();
	    return kullanici.getId();
	}
	
	@PostMapping("/cikis")
	public ResponseEntity<String> cikis() {
	    kullaniciDao.cikis();
	    return ResponseEntity.ok("Çıkış başarıyla gerçekleşti");
	}
}
