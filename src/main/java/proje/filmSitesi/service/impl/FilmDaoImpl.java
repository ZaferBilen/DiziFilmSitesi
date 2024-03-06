package proje.filmSitesi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.exception.FilmNotFoundException;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
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
	public FilmResponse uploadFilm(Long filmId, String filmPath) {
		
		Film film = filmRepository.findById(filmId).orElse(null);
		if(film != null) {
			film.setFilmPath(filmPath);
			filmRepository.save(film);
			
			return responseFilm(film);
			
		}
		return null;
	}

	@Override
	public FilmResponse uploadKapak(Long filmId, String kapakPath) {
		
		Film film = filmRepository.findById(filmId).orElse(null);
		if(film != null) {
			film.setKapakPath(kapakPath);
			filmRepository.save(film);
			
			return responseFilm(film);
			
		}
		return null;
	}

	@Override
	public FilmResponse uploadFragman(Long filmId, String fragmanPath) {
		Film film = filmRepository.findById(filmId).orElse(null);
		if(film != null) {
			film.setFragmanPath(fragmanPath);
			filmRepository.save(film);
			
			return responseFilm(film);
			
		}
		return null;
	}

}
