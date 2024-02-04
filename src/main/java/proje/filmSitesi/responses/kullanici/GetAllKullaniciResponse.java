package proje.filmSitesi.responses.kullanici;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllKullaniciResponse {

	private Long id;
	
	private String name;
	
	private String surname;

	private String email;
	
	private String role;
}
