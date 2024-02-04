package proje.filmSitesi.dao;

import java.util.List;

import proje.filmSitesi.requests.favoriler.AddFavoriFilmRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseFilm;

public interface FavoriFilmlerDao {


    void addFavoriFilm(AddFavoriFilmRequest addFavoriFilmRequest);
    void removeFavoriteFilm(Long kullaniciId, Long filmId);
    List<GeKullaniciFavoriteResponseFilm> getFavorilerByKullanici(Long kullaniciId);

}
