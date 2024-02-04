package proje.filmSitesi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import proje.filmSitesi.dao.FavoriFilmlerDao;
import proje.filmSitesi.requests.favoriler.AddFavoriFilmRequest;
import proje.filmSitesi.requests.favoriler.RemoveFavoriFilmRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseFilm;

@RestController
@RequestMapping("/favori-filmler")
@AllArgsConstructor
public class FavoriFilmlerController {

	private FavoriFilmlerDao favoriFilmlerDao;

	
    @PostMapping("/addFavoriFilm")
    public ResponseEntity<String> addFavoriFilm(@RequestBody AddFavoriFilmRequest addFavoriFilmRequest) {
       
    	favoriFilmlerDao.addFavoriFilm(addFavoriFilmRequest);
        return ResponseEntity.ok("Film favorilerinize eklendi");
        
    }

    
    
    @DeleteMapping("/removeFavoriteFilm")
    public ResponseEntity<String> removeFavoriteFilm(@RequestBody RemoveFavoriFilmRequest removeKullaniciFilmRequest) {

    	Long kullaniciId = removeKullaniciFilmRequest.getKullaniciId();
    	Long filmId = removeKullaniciFilmRequest.getFilmId();
    	
    	favoriFilmlerDao.removeFavoriteFilm(kullaniciId, filmId);
        
    	return ResponseEntity.ok("Film favorilerinizden çıkarıldı");
    }

    
    @GetMapping("/getfavorifilmler/{kullaniciId}")
    public List<GeKullaniciFavoriteResponseFilm> getFavorilerByKullanici(@PathVariable Long kullaniciId) {

        return favoriFilmlerDao.getFavorilerByKullanici(kullaniciId);
        
    }
    
    
}