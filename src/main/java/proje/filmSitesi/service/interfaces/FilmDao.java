package proje.filmSitesi.service.interfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
	
	ResponseEntity<FilmResponse> uploadFragman(Long filmId, MultipartFile file) throws IOException, GeneralSecurityException;

	ResponseEntity<FilmResponse> uploadKapak(Long filmId, MultipartFile file) throws IOException, GeneralSecurityException;

	ResponseEntity<FilmResponse> uploadFilm(Long filmId, MultipartFile file)throws IOException, GeneralSecurityException;


}
