package proje.filmSitesi.service.interfaces;

import java.util.List;

import proje.filmSitesi.requests.kullanici.KullaniciGirisRequests;
import proje.filmSitesi.requests.kullanici.KullaniciKayitRequests;
import proje.filmSitesi.responses.kullanici.GetAllKullaniciResponse;

public interface KullaniciDao {
	
	void dogrulamaKoduGonder(String email);
	
	boolean sifreKural(String sifre);
	
	boolean kullaniciKayit(KullaniciKayitRequests kullaniciKayitRequests);
	
	boolean kullaniciGiris(KullaniciGirisRequests kullaniciGirisRequests);
	
	List<GetAllKullaniciResponse> getAllUsers();
	
	void delete(Long id);
	
	void cikis();
	

}
