package proje.filmSitesi.requests.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBolumRequest {

	private Long id;
	
	private String bolum;
	  
	private Long diziId;
}
