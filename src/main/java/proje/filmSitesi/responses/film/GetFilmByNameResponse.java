package proje.filmSitesi.responses.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFilmByNameResponse {
	
	private Long id;

	private String name;
	
	private String konu;
	
	private String yili;
	
	private String yonetmen;
	
	private String kapakPath;  
	
	private String fragmanPath;
	
	private String filmPath;
	
	private String filmCategoryName;
}
