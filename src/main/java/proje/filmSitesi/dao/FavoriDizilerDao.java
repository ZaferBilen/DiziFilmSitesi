package proje.filmSitesi.dao;

import java.util.List;

import proje.filmSitesi.requests.favoriler.AddFavoriDiziRequest;
import proje.filmSitesi.responses.favoriler.GeKullaniciFavoriteResponseDizi;

public interface FavoriDizilerDao {

	void addFavoriDizi(AddFavoriDiziRequest addFavoriDiziRequest);
	void removeFavoriteDizi(Long kullaniciId, Long diziId);
	List<GeKullaniciFavoriteResponseDizi> getFavorilerByKullanici(Long kullaniciId);
}
