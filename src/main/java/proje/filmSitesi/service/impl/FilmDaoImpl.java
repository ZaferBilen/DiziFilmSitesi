package proje.filmSitesi.service.impl;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.exception.FilmNotFoundException;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.googleDrive.GoogleService;
import proje.filmSitesi.googleDrive.ResFilm;
import proje.filmSitesi.googleDrive.ResFragmanFilm;
import proje.filmSitesi.googleDrive.ResKapakFilm;
import proje.filmSitesi.model.Film;
import proje.filmSitesi.repository.FilmRepository;
import proje.filmSitesi.requests.film.CreateFilmRequest;
import proje.filmSitesi.requests.film.UpdateFilmRequest;
import proje.filmSitesi.responses.film.AdminGetAllFilmsResponse;
import proje.filmSitesi.responses.film.FilmResponse;
import proje.filmSitesi.responses.film.GetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetFilmByNameResponse;
import proje.filmSitesi.service.interfaces.FilmDao;

@Service
@AllArgsConstructor
public class FilmDaoImpl implements FilmDao{
	
	private FilmRepository filmRepository;
	private IModelMapperService modelMapperService;
	
	private GoogleService driveService;

	@Override
	public List<GetAllFilmsResponse> getAllFilmsResponse() {
		List<Film> films = filmRepository.findAll();
		
		List<GetAllFilmsResponse> filmsResponse = films.stream()
				.map(film -> this.modelMapperService.forResponse()
						.map(film, GetAllFilmsResponse.class)).collect(Collectors.toList());
		
		return filmsResponse;
	}
	
	@Override
	public List<AdminGetAllFilmsResponse> adminGetAllFilmsResponse() {
		List<Film> films = filmRepository.findAll();
		
		List<AdminGetAllFilmsResponse> filmsResponse = films.stream()
				.map(film -> this.modelMapperService.forResponse()
						.map(film, AdminGetAllFilmsResponse.class)).collect(Collectors.toList());
		
		return filmsResponse;
	}
	

	@Override
	public GetFilmByNameResponse getByFilmName(String filmName) {
		
		Film film = this.filmRepository.findByName(filmName);
		
		GetFilmByNameResponse response = this.modelMapperService.forResponse()
				.map(film, GetFilmByNameResponse.class);
		return response;
	}

	@Override
	public FilmResponse add(CreateFilmRequest createFilmRequest) {
		
		Film film = this.modelMapperService.forRequest()
				.map(createFilmRequest, Film.class);
		
		film = this.filmRepository.save(film);
		
		return responseFilm(film);
		
	}

	private FilmResponse responseFilm(Film film) {
		return new FilmResponse(
				film.getId(),
				film.getName(),
				film.getKonu(),
				film.getYili(),
				film.getYonetmen(),
				film.getKapakPath(),
				film.getFragmanPath(),
				film.getFilmPath(),
				film.getFilmCategory().getFkid()
				);
				
	}

	@Override
	public FilmResponse update(UpdateFilmRequest updateFilmRequest) {
		
		Film film = this.modelMapperService.forRequest()
				.map(updateFilmRequest, Film.class);
		
		film = this.filmRepository.save(film);
		
		return responseFilm(film);
	}

	@Override
	public FilmResponse delete(Long id) {
		
		Film film = filmRepository.findById(id)
				.orElseThrow(() -> new FilmNotFoundException("Film bulunamadı")); 
		
		this.filmRepository.deleteById(id);
		
		return responseFilm(film);
		
	}

	@Override
	public ResponseEntity<FilmResponse> uploadFilm(Long filmId, MultipartFile file) throws IOException, GeneralSecurityException {
		
		Film film = filmRepository.findById(filmId).orElse(null);
	    if (film != null) {
	    	ResFilm res = driveService.uploadVideoToDriveFilm(convert(file));
	        if (res != null && res.getStatusFilm() == 200) {
	        	film.setFilmPath(res.getUrlFilm());
	            filmRepository.save(film);
	            return ResponseEntity.ok(responseFilm(film));
	        }
	    }
	    return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<FilmResponse> uploadKapak(Long filmId, MultipartFile file) throws IOException, GeneralSecurityException {
		
		Film film = filmRepository.findById(filmId).orElse(null);
	    if (film != null) {
	        ResKapakFilm res = driveService.uploadImageToDriveKapakFilm(convert(file));
	        if (res != null && res.getStatusKapakFilm() == 200) {
	        	film.setKapakPath(res.getUrlKapakFilm());
	        	filmRepository.save(film);
	            return ResponseEntity.ok(responseFilm(film));
	        }
	    }
	    return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<FilmResponse> uploadFragman(Long filmId, MultipartFile file) throws IOException, GeneralSecurityException {
		Film film = filmRepository.findById(filmId).orElse(null);
	    if (film != null) {
	    	ResFragmanFilm res = driveService.uploadVideoToDriveFragmanFilm(convert(file));
	        if (res != null && res.getStatusFragmanFilm() == 200) {
	        	film.setFragmanPath(res.getUrlFragmanFilm());
	            filmRepository.save(film);
	            return ResponseEntity.ok(responseFilm(film));
	        }
	    }
	    return ResponseEntity.notFound().build();
	}

	private File convert(MultipartFile file) throws IOException {
        File convFile = File.createTempFile("temp", null);
        file.transferTo(convFile);
        return convFile;
    }
	
}
