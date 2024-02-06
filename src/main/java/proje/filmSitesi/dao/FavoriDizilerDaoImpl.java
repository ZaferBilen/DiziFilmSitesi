package proje.filmSitesi.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.config.KullaniciInfoDetails;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.FavoriDiziler;
import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.FavoriDizilerRepository;
import proje.filmSitesi.repository.KullaniciRepository;
import proje.filmSitesi.requests.favoriler.AddFavoriDiziRequest;
import proje.filmSitesi.requests.favoriler.RemoveFavoriDiziRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseDizi;

@Service
@AllArgsConstructor
public class FavoriDizilerDaoImpl implements FavoriDizilerDao {

	private FavoriDizilerRepository favoriDizilerRepository;
	private IModelMapperService modelMapperService;
	private KullaniciRepository kullaniciRepository;
	
	@Override
	public void addFavoriDizi(AddFavoriDiziRequest addFavoriDiziRequest) {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication != null && authentication.getPrincipal() instanceof KullaniciInfoDetails) {
	            KullaniciInfoDetails kullaniciInfo = (KullaniciInfoDetails) authentication.getPrincipal();
	            Long kullaniciId = kullaniciInfo.getId();
		
		ModelMapper modelMapper = modelMapperService.forRequest();
        
	    FavoriDiziler favoriler = modelMapper.map(addFavoriDiziRequest, FavoriDiziler.class);
	    
	    Kullanici kullanici = new Kullanici();
        kullanici.setId(kullaniciId);
        favoriler.setKullanici(kullanici);
	    favoriDizilerRepository.save(favoriler);
	 }
  }

	@Override
	public void removeFavoriteDizi(RemoveFavoriDiziRequest removeFavoriDiziRequest) {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		    if (authentication != null && authentication.getPrincipal() instanceof KullaniciInfoDetails) {
		        KullaniciInfoDetails kullaniciInfo = (KullaniciInfoDetails) authentication.getPrincipal();
		        Long kullaniciId = kullaniciInfo.getId();
		        
		        Long diziId = removeFavoriDiziRequest.getDiziId();
		        FavoriDiziler favoriler = favoriDizilerRepository.findByKullaniciIdAndDiziId(kullaniciId, diziId);
		        
		        favoriDizilerRepository.delete(favoriler);
		}
	}

	@Override
	public List<GeKullaniciFavoriteResponseDizi> getFavorilerByKullanici() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof KullaniciInfoDetails) {
		    KullaniciInfoDetails kullaniciInfo = (KullaniciInfoDetails) authentication.getPrincipal();
		    Long loggedInKullaniciId = kullaniciInfo.getId();

		    Kullanici kullanici = kullaniciRepository.findById(loggedInKullaniciId).orElse(null);
		    
		if(kullanici != null) {
			List<GeKullaniciFavoriteResponseDizi> response = new ArrayList<>();
			for(FavoriDiziler favoriDiziler : kullanici.getFavoriDiziler()) {
				response.add(new GeKullaniciFavoriteResponseDizi(favoriDiziler.getDizi().getName()));
			}
			
			return response;
		   }
		}
		return Collections.emptyList();
	}

}
