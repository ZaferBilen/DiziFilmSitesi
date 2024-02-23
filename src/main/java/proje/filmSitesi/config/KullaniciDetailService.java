package proje.filmSitesi.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.KullaniciRepository;

@Configuration
public class KullaniciDetailService implements UserDetailsService {
    
	@Autowired
    private KullaniciRepository kullaniciRepository;
	
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Kullanici> user = kullaniciRepository.findByEmail(email);
        return user.map(KullaniciInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("Kullanıcı bulunamadı."));
        
    }
}