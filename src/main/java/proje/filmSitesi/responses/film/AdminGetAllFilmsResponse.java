package proje.filmSitesi.responses.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetAllFilmsResponse {

	private Long id ;
	
	private String name;
	
	private String kapakPath;
	
	private String konu;
	
	private String filmCategoryName;
	
}
