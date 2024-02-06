package proje.filmSitesi.rules;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.repository.FilmCategoryRepository;

@AllArgsConstructor
@Service
public class FilmCategoryBusinessRules {
	
	private FilmCategoryRepository filmCategoryRepository;

	public void checkIfFilmCategoryNameExists(String name) {
		if(this.filmCategoryRepository.existsByName(name)) {
			throw new RuntimeException("Film daha önceden eklenmiş.");
		}
	}
}
