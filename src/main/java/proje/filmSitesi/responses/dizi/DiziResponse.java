package proje.filmSitesi.responses.dizi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiziResponse {
    
	private Long id;
	
    private String name;
    
    private String konu;
    
    private String yili;
    
    private String yonetmen;
    
    private String kapakPath;

    private String fragmanPath;
    
    private List<BolumResponse> bolumler;
    
    private int diziCategoryDkid;
    
    
    
}
