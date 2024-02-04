package proje.filmSitesi.requests.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFilmRequest {
	
	private String name;
	
	private String konu;
	
	private String yili;
	
	private String yonetmen;	
	
	private int filmCategoryId;

}
