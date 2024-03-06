package proje.filmSitesi.responses.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetAllCategoryResponse {

	private int fkid;
	
	private String name;
}
