package proje.filmSitesi.dao;

import java.util.List;

import proje.filmSitesi.requests.film.CreateFilmRequest;
import proje.filmSitesi.requests.film.UpdateFilmRequest;
import proje.filmSitesi.responses.film.AdminGetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetFilmByNameResponse;

public interface FilmDao {
	
	List<GetAllFilmsResponse> getAllFilmsResponse();
	
	List<AdminGetAllFilmsResponse> adminGetAllFilmsResponse();
	
	GetFilmByNameResponse getByFilmName(String filmName);
	
	void add(CreateFilmRequest createFilmRequest);
	
	void update(UpdateFilmRequest updateFilmRequest);
	
	void delete(Long id);
	
	void uploadFilm(Long filmId , String filmPath);
	
	void uploadKapak(Long filmId , String kapakPath);
	
	void uploadFragman(Long filmId , String fragmanPath);


}
