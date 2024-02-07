package proje.filmSitesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.FavoriDiziler;

@Repository
public interface FavoriDizilerRepository extends JpaRepository<FavoriDiziler, Integer> {

	FavoriDiziler findByKullaniciIdAndDiziId(Long kullaniciId, Long diziId);
	

}
