package proje.filmSitesi.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.Film;
import proje.filmSitesi.repository.FilmRepository;
import proje.filmSitesi.requests.film.CreateFilmRequest;
import proje.filmSitesi.requests.film.UpdateFilmRequest;
import proje.filmSitesi.responses.film.AdminGetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetAllFilmsResponse;
import proje.filmSitesi.responses.film.GetFilmByNameResponse;

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
	public void add(CreateFilmRequest createFilmRequest) {
		
		Film film = this.modelMapperService.forRequest()
				.map(createFilmRequest, Film.class);
		
		this.filmRepository.save(film);
		
	}

	@Override
	public void update(UpdateFilmRequest updateFilmRequest) {
		
		Film film = this.modelMapperService.forRequest()
				.map(updateFilmRequest, Film.class);
		
		this.filmRepository.save(film);
		
	}

	@Override
	public void delete(Long id) {
		
		this.filmRepository.deleteById(id);
		
	}

	@Override
	public void uploadFilm(Long filmId, String filmPath) {
		
		Film film = filmRepository.findById(filmId).orElse(null);
		if(film != null) {
			film.setFilmPath(filmPath);
			filmRepository.save(film);
			
		}
		
	}

	@Override
	public void uploadKapak(Long filmId, String kapakPath) {
		
		Film film = filmRepository.findById(filmId).orElse(null);
		if(film != null) {
			film.setKapakPath(kapakPath);
			filmRepository.save(film);
			
		}
	}

	@Override
	public void uploadFragman(Long filmId, String fragmanPath) {
		Film film = filmRepository.findById(filmId).orElse(null);
		if(film != null) {
			film.setFragmanPath(fragmanPath);
			filmRepository.save(film);
			
		}
	}

}
