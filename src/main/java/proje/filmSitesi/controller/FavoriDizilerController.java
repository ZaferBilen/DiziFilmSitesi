package proje.filmSitesi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import proje.filmSitesi.requests.favoriler.AddFavoriDiziRequest;
import proje.filmSitesi.requests.favoriler.RemoveFavoriDiziRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseDizi;
import proje.filmSitesi.service.interfaces.FavoriDizilerDao;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/favori-diziler")
@AllArgsConstructor
public class FavoriDizilerController {
	
	private FavoriDizilerDao favoriDizilerDao;

		@PostMapping("/addFavoriDizi")
    public ResponseEntity<Object> addFavoriDizi(@RequestBody AddFavoriDiziRequest addFavoriDiziRequest) {		
        
			favoriDizilerDao.addFavoriDizi(addFavoriDiziRequest);
		return ResponseEntity.ok(Map.of("message","Dizi favorilerinize eklendi"));
		
    }
		
	@DeleteMapping("/removeFavoriteDizi")
	public ResponseEntity<Object> removeFavoriteDizi(@RequestBody RemoveFavoriDiziRequest removeKullaniciDiziRequest ) {		
	    	
	    favoriDizilerDao.removeFavoriteDizi(removeKullaniciDiziRequest);
	    	
	    return ResponseEntity.ok(Map.of("message","Dizi favorilerinizden çıkarıldı"));
	        
	    }
	
    @GetMapping("/getfavoridiziler")
    public List<GeKullaniciFavoriteResponseDizi> getFavorilerByKullanici() {

        return favoriDizilerDao.getFavorilerByKullanici();
        
    }
    
    
	
}
