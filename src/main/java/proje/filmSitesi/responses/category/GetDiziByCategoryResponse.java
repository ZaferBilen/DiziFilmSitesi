package proje.filmSitesi.responses.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDiziByCategoryResponse {

	private String diziCategoryName;
	private String dizilerName;
}
