package proje.filmSitesi.responses.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllDiziResponse {
	
	private String name;

	private String kapakPath;
	
	private String konu;
	
	private String diziCategoryName;
	
}
