package proje.filmSitesi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.Kullanici;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Long>{
	
	Optional<Kullanici> findByEmail(String email);

}
