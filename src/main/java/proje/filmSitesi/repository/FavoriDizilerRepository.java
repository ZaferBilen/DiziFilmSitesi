package proje.filmSitesi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.FavoriDiziler;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseDizi;

@Repository
public interface FavoriDizilerRepository extends JpaRepository<FavoriDiziler, Integer> {

	FavoriDiziler findByKullaniciIdAndDiziId(Long kullaniciId, Long diziId);
	
	List<GeKullaniciFavoriteResponseDizi> findByKullaniciId(Long kullaniciId);

}
