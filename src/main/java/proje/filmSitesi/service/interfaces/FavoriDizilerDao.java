package proje.filmSitesi.service.interfaces;

import java.util.List;

import proje.filmSitesi.requests.favoriler.AddFavoriDiziRequest;
import proje.filmSitesi.requests.favoriler.RemoveFavoriDiziRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseDizi;

public interface FavoriDizilerDao {

	void addFavoriDizi(AddFavoriDiziRequest addFavoriDiziRequest);
	void removeFavoriteDizi(RemoveFavoriDiziRequest removeFavoriDiziRequest);
	List<GeKullaniciFavoriteResponseDizi> getFavorilerByKullanici();
}
