package proje.filmSitesi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "diziler")
public class Dizi {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String konu;

    private String yili;

    private String yonetmen;

    private String kapakPath;

    private String fragmanPath;

    @OneToMany(mappedBy = "dizi" , cascade = CascadeType.ALL)
    private List<Bolum> bolumList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "dizi_category_id")
    private DiziCategory diziCategory;
    
    
    @OneToMany(mappedBy = "dizi")
    private List<FavoriDiziler> favoriDizilerList;

}
