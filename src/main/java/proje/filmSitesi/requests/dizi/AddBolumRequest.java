package proje.filmSitesi.requests.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBolumRequest {
  
	  private Long diziId;
	  
	  private String bolum;

}
