package proje.filmSitesi.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.FavoriDiziler;
import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.FavoriDizilerRepository;
import proje.filmSitesi.repository.KullaniciRepository;
import proje.filmSitesi.requests.favoriler.AddFavoriDiziRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseDizi;

@Service
@AllArgsConstructor
public class FavoriDizilerDaoImpl implements FavoriDizilerDao {

	private FavoriDizilerRepository favoriDizilerRepository;
	private IModelMapperService modelMapperService;
	private KullaniciRepository kullaniciRepository;
	
	@Override
	public void addFavoriDizi(AddFavoriDiziRequest addFavoriDiziRequest) {
		
		ModelMapper modelMapper = modelMapperService.forRequest();
        
	    FavoriDiziler favoriDiziler = modelMapper.map(addFavoriDiziRequest, FavoriDiziler.class);
	    favoriDizilerRepository.save(favoriDiziler);
    }

	@Override
	public void removeFavoriteDizi(Long kullaniciId, Long diziId) {
		
		FavoriDiziler favoriDiziler = favoriDizilerRepository.findByKullaniciIdAndDiziId(kullaniciId, diziId);
		
		if(favoriDiziler != null) {
			favoriDizilerRepository.delete(favoriDiziler);
		}
	}

	@Override
	public List<GeKullaniciFavoriteResponseDizi> getFavorilerByKullanici(Long kullaniciId) {
		
		Kullanici kullanici = kullaniciRepository.findById(kullaniciId).orElse(null);
		if(kullanici != null) {
			List<GeKullaniciFavoriteResponseDizi> response = new ArrayList<>();
			for(FavoriDiziler favoriDiziler : kullanici.getFavoriDiziler()) {
				response.add(new GeKullaniciFavoriteResponseDizi(favoriDiziler.getDizi().getName()));
			}
			
			return response;
		}
		return Collections.emptyList();
	}

}
