package proje.filmSitesi.responses.dizi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllDiziResponse {
	
	private Long id ;
	
	private String name;

	private String kapakPath;
	
	private String fragmanPath;
	
	private String konu;
	
	private String diziCategoryName;
	
	private List<DiziBolumleriResponse> bolum;
	
}
