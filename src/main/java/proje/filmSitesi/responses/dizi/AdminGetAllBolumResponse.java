package proje.filmSitesi.responses.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetAllBolumResponse {

	private Long id;
	
	private String diziName;
	
	private String bolum;	
}
