package proje.filmSitesi.model;

import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "favori_diziler")
@DynamicUpdate
public class FavoriDiziler {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;
    
    @ManyToOne
    @JoinColumn(name = "dizi_id")
    private Dizi dizi;
    
}
