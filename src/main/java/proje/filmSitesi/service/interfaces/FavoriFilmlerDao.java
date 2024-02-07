package proje.filmSitesi.service.interfaces;

import java.util.List;

import proje.filmSitesi.requests.favoriler.AddFavoriFilmRequest;
import proje.filmSitesi.requests.favoriler.RemoveFavoriFilmRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseFilm;

public interface FavoriFilmlerDao {


    void addFavoriFilm(AddFavoriFilmRequest addFavoriFilmRequest);
    void removeFavoriteFilm(RemoveFavoriFilmRequest removeFavoriFilmRequest);
    List<GeKullaniciFavoriteResponseFilm> getFavorilerByKullanici();

}
