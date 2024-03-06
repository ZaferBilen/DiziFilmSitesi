package proje.filmSitesi.responses.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BolumResponse {
   
	private int id;

    private String bolum;

    private String path;
    
    private Long diziId;

    private String diziName;
}
