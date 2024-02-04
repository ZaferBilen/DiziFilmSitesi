package proje.filmSitesi.requests.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDiziCategoryRequest {
	
	private int id;
	private String name;
}
