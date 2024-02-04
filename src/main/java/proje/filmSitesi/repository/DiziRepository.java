package proje.filmSitesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proje.filmSitesi.model.Dizi;

@Repository
public interface DiziRepository extends JpaRepository<Dizi, Long>{
	
	Dizi findByName(String diziName);
	

}
