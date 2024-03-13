package proje.filmSitesi.model;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "filmler")
@DynamicUpdate
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
	
    @OneToMany(mappedBy = "film")
    private List<FavoriFilmler> favoriFilmlerList;
    
}
