package proje.filmSitesi.responses.favoriler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import proje.filmSitesi.responses.dizi.KullaniciBolumResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeKullaniciFavoriteResponseDizi {

	private String diziName;
	
	private String diziKapakPath;
	
	private String diziFragmanPath;
	
	private List<KullaniciBolumResponse> bolumler;
	
	
}
