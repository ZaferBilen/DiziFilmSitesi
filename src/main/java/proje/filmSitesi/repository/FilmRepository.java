package proje.filmSitesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
	
	Film findByName(String filmName);
	
														

}
