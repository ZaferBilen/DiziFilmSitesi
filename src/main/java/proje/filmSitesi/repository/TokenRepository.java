package proje.filmSitesi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import proje.filmSitesi.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{

	
	@Query("""
	        select t from Token t join t.kullanici u
	        where u.id = :kullaniciId and t.loggedOut = false
	        """)
		List<Token> findAllTokensByUser(Long kullaniciId);
	
		Optional<Token> findByToken(String token);
	
}
