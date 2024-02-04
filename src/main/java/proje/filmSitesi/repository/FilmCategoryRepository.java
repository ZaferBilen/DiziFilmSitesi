package proje.filmSitesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.FilmCategory;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, Integer> {

	FilmCategory findByName(String filmCategoryName);

	boolean existsByName(String name);
}
