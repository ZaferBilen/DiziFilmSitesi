package proje.filmSitesi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@DynamicUpdate
public class Kullanici {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String surname;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String sifre;
	
	public enum UserRole {
	    ADMIN, NORMAL
	}
	
	private UserRole role;
	
	@Transient
    private String geciciDogrulamaKodu;

    @Transient
    private LocalDateTime doğrulamaKoduSonKullanmaTarihi;
    private boolean basarılı;
    
    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL)
    private List<FavoriFilmler> favoriFilmler = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL)
    private List<FavoriDiziler> favoriDiziler = new ArrayList<>();
    
}
