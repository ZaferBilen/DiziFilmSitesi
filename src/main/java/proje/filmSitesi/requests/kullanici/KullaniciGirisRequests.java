package proje.filmSitesi.requests.kullanici;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KullaniciGirisRequests {

	private String email;
	private String sifre;
}
