package proje.filmSitesi.responses.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllBolumResponse {
	
	private String diziName;
	
	private String bolum;

}
