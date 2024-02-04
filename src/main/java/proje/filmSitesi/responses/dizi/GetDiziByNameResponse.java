package proje.filmSitesi.responses.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDiziByNameResponse {

	 private String name;

	 private String konu;
	    
	 private String kapakPath;
	 
	 private String diziCategoryName;
}
