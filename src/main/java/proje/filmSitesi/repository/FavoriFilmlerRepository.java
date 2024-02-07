package proje.filmSitesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.FavoriFilmler;

@Repository
public interface FavoriFilmlerRepository extends JpaRepository<FavoriFilmler, Integer> {
	

	FavoriFilmler findByKullaniciIdAndFilmId(Long kullaniciId, Long filmId);
	
	
}
