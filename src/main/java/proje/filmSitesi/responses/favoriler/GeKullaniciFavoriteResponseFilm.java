package proje.filmSitesi.responses.favoriler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeKullaniciFavoriteResponseFilm {
	
	private int id;
	
	private Long filmId;

	private String filmName;
	
	private String filmKapakPath;  
	
	private String filmFragmanPath;
	
	private String filmFilmPath;
}
