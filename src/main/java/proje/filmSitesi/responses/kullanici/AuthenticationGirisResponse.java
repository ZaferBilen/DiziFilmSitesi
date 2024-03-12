package proje.filmSitesi.responses.kullanici;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import proje.filmSitesi.model.Kullanici.UserRole;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseDizi;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseFilm;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationGirisResponse {

	private String token;
	
	private Long id;
	
	private String name;

	private String surname;
	
	private String email;
	
	private UserRole role;
	
	private List<GeKullaniciFavoriteResponseDizi> favoriDiziler;
	
	private List<GeKullaniciFavoriteResponseFilm> favoriFilmler;
	
	
}
