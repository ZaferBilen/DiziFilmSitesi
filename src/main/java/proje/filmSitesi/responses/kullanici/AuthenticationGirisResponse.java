package proje.filmSitesi.responses.kullanici;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import proje.filmSitesi.model.Kullanici.UserRole;

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
	
}
