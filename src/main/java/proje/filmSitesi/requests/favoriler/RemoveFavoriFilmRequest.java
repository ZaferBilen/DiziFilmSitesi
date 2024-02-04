package proje.filmSitesi.requests.favoriler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFavoriFilmRequest {

	private Long kullaniciId;
	
	private Long filmId;
}
