package proje.filmSitesi.service.interfaces;

import java.util.List;

import proje.filmSitesi.requests.film.CreateFilmRequest;
import proje.filmSitesi.requests.film.UpdateFilmRequest;
import proje.filmSitesi.responses.film.AdminGetAllFilmsResponse;
import proje.filmSitesi.responses.film.FilmResponse;
import proje.filmSitesi.responses.film.GetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetFilmByNameResponse;

public interface FilmDao {
	
	List<GetAllFilmsResponse> getAllFilmsResponse();
	
	List<AdminGetAllFilmsResponse> adminGetAllFilmsResponse();
	
	GetFilmByNameResponse getByFilmName(String filmName);
	
	FilmResponse add(CreateFilmRequest createFilmRequest);
	
	FilmResponse update(UpdateFilmRequest updateFilmRequest);
	
	FilmResponse delete(Long id);
	
	FilmResponse uploadFilm(Long filmId , String filmPath);
	
	FilmResponse uploadKapak(Long filmId , String kapakPath);
	
	FilmResponse uploadFragman(Long filmId , String fragmanPath);


}
