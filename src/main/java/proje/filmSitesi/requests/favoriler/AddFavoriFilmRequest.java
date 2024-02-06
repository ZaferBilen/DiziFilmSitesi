package proje.filmSitesi.requests.favoriler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFavoriFilmRequest {

	private Long filmId;
	
}
