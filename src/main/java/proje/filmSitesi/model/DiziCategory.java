package proje.filmSitesi.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dizi_categories")
@DynamicUpdate
public class DiziCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dkid;
	
	private String name;
	
	@OneToMany(mappedBy = "diziCategory", fetch = FetchType.LAZY)
	private List<Dizi> diziler = new ArrayList<>();
}
