package proje.filmSitesi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "filmler")
public class Film {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private String konu;
	
	private String yili;
	
	private String yonetmen;
	
	private String kapakPath;  
	
	private String fragmanPath;
	
	private String filmPath;
	
	@ManyToOne
	@JoinColumn(name = "film_category_id")
	private FilmCategory filmCategory;    
	
}
