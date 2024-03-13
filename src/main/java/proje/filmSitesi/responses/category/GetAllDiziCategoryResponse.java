package proje.filmSitesi.responses.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllDiziCategoryResponse {
	
	private int dkid;

	private String name;
}
