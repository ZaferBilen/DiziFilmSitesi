package proje.filmSitesi.service.impl;

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
import proje.filmSitesi.model.FavoriFilmler;
import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.FavoriFilmlerRepository;
import proje.filmSitesi.repository.KullaniciRepository;
import proje.filmSitesi.requests.favoriler.AddFavoriFilmRequest;
import proje.filmSitesi.requests.favoriler.RemoveFavoriFilmRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseFilm;
import proje.filmSitesi.service.interfaces.FavoriFilmlerDao;

@Service
@AllArgsConstructor
public class FavoriFilmlerDaoImpl implements FavoriFilmlerDao {
	
	private FavoriFilmlerRepository favoriFilmlerRepository;
	private IModelMapperService modelMapperService;
	private KullaniciRepository kullaniciRepository;
			
	@Override
	public void addFavoriFilm(AddFavoriFilmRequest addFavoriFilmRequest) {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication != null && authentication.getPrincipal() instanceof KullaniciInfoDetails) {
	            KullaniciInfoDetails kullaniciInfo = (KullaniciInfoDetails) authentication.getPrincipal();
	            Long kullaniciId = kullaniciInfo.getId();

	            Long filmId = addFavoriFilmRequest.getFilmId();

	            FavoriFilmler existingFavorite = favoriFilmlerRepository.findByKullaniciIdAndFilmId(kullaniciId, filmId);

	            if (existingFavorite != null) {
	                throw new RuntimeException("Bu film zaten favorilerinizde mevcut");
	            }
	            
	            ModelMapper modelMapper = modelMapperService.forRequest();
	            FavoriFilmler favoriler = modelMapper.map(addFavoriFilmRequest, FavoriFilmler.class);
	            	           
	            Kullanici kullanici = new Kullanici();
	            kullanici.setId(kullaniciId);
	            favoriler.setKullanici(kullanici);
	            
	            favoriFilmlerRepository.save(favoriler);
	        } 
	    
    }
	
	
	
	@Override
	public void removeFavoriteFilm(RemoveFavoriFilmRequest removeFavoriFilmRequest) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		    if (authentication != null && authentication.getPrincipal() instanceof KullaniciInfoDetails) {
		        KullaniciInfoDetails kullaniciInfo = (KullaniciInfoDetails) authentication.getPrincipal();
		        Long kullaniciId = kullaniciInfo.getId();

		        Long filmId = removeFavoriFilmRequest.getFilmId();
		        FavoriFilmler favoriler = favoriFilmlerRepository.findByKullaniciIdAndFilmId(kullaniciId, filmId);

		        favoriFilmlerRepository.delete(favoriler);
		    }
		        
	}
	
	
	@Override
	public List<GeKullaniciFavoriteResponseFilm> getFavorilerByKullanici() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication != null && authentication.getPrincipal() instanceof KullaniciInfoDetails) {
	        KullaniciInfoDetails kullaniciInfo = (KullaniciInfoDetails) authentication.getPrincipal();
	        Long loggedInKullaniciId = kullaniciInfo.getId();

	        Kullanici kullanici = kullaniciRepository.findById(loggedInKullaniciId).orElse(null);
	        
	        if (kullanici != null) {
	            List<GeKullaniciFavoriteResponseFilm> response = new ArrayList<>();
	            for (FavoriFilmler favoriler : kullanici.getFavoriFilmler()) {
	                response.add(new GeKullaniciFavoriteResponseFilm(favoriler.getFilm().getName(),
	                		favoriler.getFilm().getKapakPath(), 
	                		favoriler.getFilm().getFragmanPath(),
	                		favoriler.getFilm().getFilmPath()));
	            }
	            
	            return response;
	        }
	    }

	    return Collections.emptyList();
	}
	
}
