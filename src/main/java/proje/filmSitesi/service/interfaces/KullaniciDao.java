package proje.filmSitesi.service.interfaces;

import java.util.List;

import proje.filmSitesi.requests.kullanici.KullaniciGirisRequests;
import proje.filmSitesi.requests.kullanici.KullaniciKayitRequests;
import proje.filmSitesi.responses.kullanici.AuthenticationGirisResponse;
import proje.filmSitesi.responses.kullanici.AuthenticationResponse;
import proje.filmSitesi.responses.kullanici.GetAllKullaniciResponse;

public interface KullaniciDao {
	
	void dogrulamaKoduGonder(String email);
	
	boolean sifreKural(String sifre);
	
	public AuthenticationResponse kullaniciKayit(KullaniciKayitRequests kullaniciKayitRequests);
	
	public AuthenticationGirisResponse authenticate(KullaniciGirisRequests KullaniciGirisRequests);	
	
	List<GetAllKullaniciResponse> getAllUsers();
	
	void delete(Long id);
	
	void cikis();
	

}
