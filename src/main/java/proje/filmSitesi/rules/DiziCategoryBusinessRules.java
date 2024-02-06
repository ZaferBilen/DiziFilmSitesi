package proje.filmSitesi.rules;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.repository.DiziCategoryRepository;

@AllArgsConstructor
@Service
public class DiziCategoryBusinessRules {

	private DiziCategoryRepository diziCategoryRepository;

	public void checkIfDiziCategoryNameExists(String name) {
		if(this.diziCategoryRepository.existsByName(name)) {
			throw new RuntimeException("Dizi daha önceden eklenmiş.");
		}
	}
}
