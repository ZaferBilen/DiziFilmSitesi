package proje.filmSitesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.DiziCategory;

@Repository
public interface DiziCategoryRepository extends JpaRepository<DiziCategory, Integer>{
	
	DiziCategory findByName(String diziCategoryName);
	
	boolean existsByName(String name);

}
