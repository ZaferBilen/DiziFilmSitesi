package proje.filmSitesi.requests.kullanici;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KullaniciKayitRequests {
	
	private String name;

	private String surname;
	
	private String email;
	
	private String sifre;
	
	private String role;

	private String geciciDogrulamaKodu;
}
