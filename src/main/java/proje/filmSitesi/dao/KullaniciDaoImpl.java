package proje.filmSitesi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.KullaniciRepository;
import proje.filmSitesi.requests.kullanici.KullaniciGirisRequests;
import proje.filmSitesi.requests.kullanici.KullaniciKayitRequests;
import proje.filmSitesi.responses.kullanici.GetAllKullaniciResponse;
import proje.filmSitesi.util.DogrulamaKoduOnBellegi;

@Service
@AllArgsConstructor
public class KullaniciDaoImpl implements KullaniciDao{
	
	private KullaniciRepository kullaniciRepository;
	
	private PasswordEncoder passwordEncoder;
    
    private EmailDao emailDao;
    
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
	public boolean kullaniciKayit(KullaniciKayitRequests kullaniciKayitRequests) {
	    String email = kullaniciKayitRequests.getEmail();
	    String dogrulamaKodu = kullaniciKayitRequests.getGeciciDogrulamaKodu();
	    String sifre = kullaniciKayitRequests.getSifre();
	    String name = kullaniciKayitRequests.getName();
	    String surname = kullaniciKayitRequests.getSurname();
	    String role = kullaniciKayitRequests.getRole();

	    String cachedCode = DogrulamaKoduOnBellegi.getVerificationCode(email);

	    if (cachedCode == null || !cachedCode.equals(dogrulamaKodu)) {
	        return false;
	    }

	    DogrulamaKoduOnBellegi.removeVerificationCode(email);
	    Kullanici kullanici = new Kullanici();
	    kullanici.setName(name);
	    kullanici.setSurname(surname);
	    kullanici.setEmail(email);
	    kullanici.setSifre(passwordEncoder.encode(sifre));
	    kullanici.setRole(role);

	    kullaniciRepository.save(kullanici);
	    return true;
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
	public boolean kullaniciGiris(KullaniciGirisRequests kullaniciGirisRequests) {
		 String email = kullaniciGirisRequests.getEmail();
		    String sifre = kullaniciGirisRequests.getSifre();
		    Kullanici kullanici = kullaniciRepository.findByEmail(email).orElse(null);

		    if (kullanici != null) {    
		    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		        if (passwordEncoder.matches(sifre, kullanici.getSifre())) {
		            return true;
		        }
		    }
		    return false;
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
