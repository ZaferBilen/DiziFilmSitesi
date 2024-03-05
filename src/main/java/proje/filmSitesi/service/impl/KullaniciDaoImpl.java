package proje.filmSitesi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.DogrulamaKoduOnBellegi;
import proje.filmSitesi.core.utilities.exception.EmailAlreadyExistsException;
import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.model.Kullanici.UserRole;
import proje.filmSitesi.repository.KullaniciRepository;
import proje.filmSitesi.requests.kullanici.KullaniciGirisRequests;
import proje.filmSitesi.requests.kullanici.KullaniciKayitRequests;
import proje.filmSitesi.responses.kullanici.AuthenticationGirisResponse;
import proje.filmSitesi.responses.kullanici.AuthenticationResponse;
import proje.filmSitesi.responses.kullanici.GetAllKullaniciResponse;
import proje.filmSitesi.service.JwtService;
import proje.filmSitesi.service.interfaces.EmailDao;
import proje.filmSitesi.service.interfaces.KullaniciDao;

@Service
@AllArgsConstructor
public class KullaniciDaoImpl implements KullaniciDao{
	
	private KullaniciRepository kullaniciRepository;
	
	private PasswordEncoder passwordEncoder;
    
    private EmailDao emailDao;
    
    private final JwtService jwtService;
    
    private final AuthenticationManager authenticationManager;
    
    
	private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6); 
    }

	@Override
	public void dogrulamaKoduGonder(String email) {
		String dogrulamaKodu = generateVerificationCode();
        DogrulamaKoduOnBellegi.saveVerificationCode(email, dogrulamaKodu);

        emailDao.dogrulamaKoduGonder(email, dogrulamaKodu);
	}

	@Override
	public boolean sifreKural(String sifre) {
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%*? ^:;+-._,])[a-zA-Z\\d@#$%*? ^:;+-._,]{6,}$";
        return Pattern.matches(passwordRegex, sifre);
	}

	@Override
	public AuthenticationResponse kullaniciKayit(KullaniciKayitRequests kullaniciKayitRequests) {
	    String email = kullaniciKayitRequests.getEmail();
	    String dogrulamaKodu = kullaniciKayitRequests.getGeciciDogrulamaKodu();
	    String sifre = kullaniciKayitRequests.getSifre();
	    String name = kullaniciKayitRequests.getName();
	    String surname = kullaniciKayitRequests.getSurname();
	    UserRole role = kullaniciKayitRequests.getRole();
	    
	    
	    if (kullaniciRepository.existsByEmail(email)) {
	        throw new EmailAlreadyExistsException("Bu email adresiyle daha önceden kayıt yapılmış.");
	    }

	    String cachedCode = DogrulamaKoduOnBellegi.getVerificationCode(email);

	    if (cachedCode == null || !cachedCode.equals(dogrulamaKodu)) {
	        
	    }

	    DogrulamaKoduOnBellegi.removeVerificationCode(email);
	    Kullanici kullanici = new Kullanici();
	    kullanici.setName(name);
	    kullanici.setSurname(surname);
	    kullanici.setEmail(email);
	    kullanici.setSifre(passwordEncoder.encode(sifre));
	    kullanici.setRole(role);

	    kullaniciRepository.save(kullanici);
	    
	    String jwt = jwtService.generateToken(kullanici);
	    
	    return new AuthenticationResponse(jwt);
	}
	
	
	public AuthenticationGirisResponse authenticate(KullaniciGirisRequests kullaniciGirisRequests) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		kullaniciGirisRequests.getEmail(),
                		kullaniciGirisRequests.getSifre()
                )
        );
        
        Kullanici kullanici = kullaniciRepository.findByEmail(kullaniciGirisRequests.getEmail()).orElseThrow();
        String jwt = jwtService.generateToken(kullanici);
        Long id = kullanici.getId(); 
        String name = kullanici.getName();
        String surname = kullanici.getSurname();
        String email = kullanici.getEmail();
        UserRole role = kullanici.getRole();
        
        return new AuthenticationGirisResponse(jwt, id, name, surname, email, role);
	
	}
	@Override
	public List<GetAllKullaniciResponse> getAllUsers(){
		List<Kullanici> users = kullaniciRepository.findAll();
		List<GetAllKullaniciResponse> userResponse = new ArrayList<>();
		
		for (Kullanici user : users) {
			
			GetAllKullaniciResponse response = new GetAllKullaniciResponse();
			response.setId(user.getId());
			response.setName(user.getName());
			response.setSurname(user.getSurname());
			response.setEmail(user.getEmail());
			response.setRole(user.getRole());
			
			userResponse.add(response);
			
		}
		
		return userResponse;
		
	}

	@Override
	public void delete(Long id) {
		
		this.kullaniciRepository.deleteById(id);
		
	}
	
	@Override
	public void cikis() {
	    SecurityContextHolder.clearContext();
	    }

}
