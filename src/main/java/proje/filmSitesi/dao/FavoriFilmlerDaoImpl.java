package proje.filmSitesi.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.FavoriFilmler;
import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.FavoriFilmlerRepository;
import proje.filmSitesi.repository.KullaniciRepository;
import proje.filmSitesi.requests.favoriler.AddFavoriFilmRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseFilm;

@Service
@AllArgsConstructor
public class FavoriFilmlerDaoImpl implements FavoriFilmlerDao {
	
	private FavoriFilmlerRepository favoriFilmlerRepository;
	private IModelMapperService modelMapperService;
	private KullaniciRepository kullaniciRepository;
			
	@Override
	public void addFavoriFilm(AddFavoriFilmRequest addFavoriFilmRequest) {
	
		ModelMapper modelMapper = modelMapperService.forRequest();
        
		FavoriFilmler favoriler = modelMapper.map(addFavoriFilmRequest, FavoriFilmler.class);
		favoriFilmlerRepository.save(favoriler);
    }
	
	
	
	@Override
	public void removeFavoriteFilm(Long kullaniciId, Long filmId) {
		
		FavoriFilmler favoriler = favoriFilmlerRepository.findByKullaniciIdAndFilmId(kullaniciId, filmId);
		
		if(favoriler != null) {
			favoriFilmlerRepository.delete(favoriler);
		}
		
	}
	
	
	@Override
	public List<GeKullaniciFavoriteResponseFilm> getFavorilerByKullanici(Long kullaniciId) {
		
		Kullanici kullanici = kullaniciRepository.findById(kullaniciId).orElse(null);
		if(kullanici != null) {
			List<GeKullaniciFavoriteResponseFilm> response = new ArrayList<>();
			for(FavoriFilmler favoriler : kullanici.getFavoriFilmler()) {
				response.add(new GeKullaniciFavoriteResponseFilm(favoriler.getFilm().getName()));
			}
			
			return response;
		}
		return Collections.emptyList();
	}
	
		
	
}
