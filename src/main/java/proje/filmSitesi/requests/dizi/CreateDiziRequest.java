package proje.filmSitesi.requests.dizi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDiziRequest {

	private String name;

    private String konu;

    private String yili;

    private String yonetmen;
	
	private int diziCategoryId;
}
