package proje.filmSitesi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.FavoriFilmler;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseFilm;

@Repository
public interface FavoriFilmlerRepository extends JpaRepository<FavoriFilmler, Integer> {
	

	FavoriFilmler findByKullaniciIdAndFilmId(Long kullaniciId, Long filmId);
	
	List<GeKullaniciFavoriteResponseFilm> findByKullaniciId(Long kullaniciId);
	
}
