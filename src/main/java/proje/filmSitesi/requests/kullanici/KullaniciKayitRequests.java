package proje.filmSitesi.requests.kullanici;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import proje.filmSitesi.model.Kullanici.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KullaniciKayitRequests {
	
	private String name;

	private String surname;
	
	private String email;
	
	private String sifre;
	
	private UserRole role;

	private String geciciDogrulamaKodu;
}
