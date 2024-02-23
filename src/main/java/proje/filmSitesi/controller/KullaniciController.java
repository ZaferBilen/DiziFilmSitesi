package proje.filmSitesi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import proje.filmSitesi.requests.kullanici.KullaniciGirisRequests;
import proje.filmSitesi.requests.kullanici.KullaniciKayitRequests;
import proje.filmSitesi.responses.kullanici.AuthenticationGirisResponse;
import proje.filmSitesi.responses.kullanici.AuthenticationResponse;
import proje.filmSitesi.service.interfaces.KullaniciDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping
public class KullaniciController {
	
	@Autowired
	private KullaniciDao kullaniciDao;
	
	@Autowired
	private KullaniciRepository kullaniciRepository;
	
	
	
	@GetMapping("/")
    public String anaSayfa(){
        return "Film sitesine hoşgeldiniz";
    }
	
	@PostMapping("/kullanici/kod")
	public ResponseEntity<Object> dogrulamaKodu(@RequestBody String email) {
	    kullaniciDao.dogrulamaKoduGonder(email);
	    return ResponseEntity.ok(Map.of("message","Doğrulama kodu e-postanıza gönderildi."));
	}
	
	@PostMapping("/kullanici/kayıt")
	public ResponseEntity<AuthenticationResponse> register(
            @RequestBody KullaniciKayitRequests request
            ) {
        return ResponseEntity.ok(kullaniciDao.kullaniciKayit(request));
    }
	
	@GetMapping("/users/all")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(kullaniciDao.getAllUsers());
    }
	
	@PostMapping("/giris")
	public ResponseEntity<AuthenticationGirisResponse> login(
            @RequestBody KullaniciGirisRequests request
    ) {
        return ResponseEntity.ok(kullaniciDao.authenticate(request));
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