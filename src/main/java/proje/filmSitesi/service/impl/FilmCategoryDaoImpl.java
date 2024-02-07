package proje.filmSitesi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.Film;
import proje.filmSitesi.model.FilmCategory;
import proje.filmSitesi.repository.FilmCategoryRepository;
import proje.filmSitesi.requests.category.CreateFilmCategoryRequest;
import proje.filmSitesi.requests.category.UpdateFilmCategoryRequest;
import proje.filmSitesi.responses.category.AdminGetAllCategoryResponse;
import proje.filmSitesi.responses.category.GetAllFilmCategoryResponse;
import proje.filmSitesi.responses.category.GetFilmByCategoryResponse;
import proje.filmSitesi.rules.FilmCategoryBusinessRules;
import proje.filmSitesi.service.interfaces.FilmCategoryDao;

@Service
@AllArgsConstructor
public class FilmCategoryDaoImpl implements FilmCategoryDao {
	
	private FilmCategoryRepository filmCategoryRepository;
	private IModelMapperService modelMapperService;
	private FilmCategoryBusinessRules filmCategoryBusinessRules;
	

	@Override
	public List<GetAllFilmCategoryResponse> getAllFilmCategoryResponse() {
		List<FilmCategory> categories = filmCategoryRepository.findAll();
		
		List<GetAllFilmCategoryResponse> response = categories.stream()
				.map(filmCategory -> this.modelMapperService.forResponse()
						.map(filmCategory, GetAllFilmCategoryResponse.class)).collect(Collectors.toList());
		
		return response;
	}
	
	@Override
	public List<AdminGetAllCategoryResponse> adminGetAllCategoryResponse() {
		List<FilmCategory> categories = filmCategoryRepository.findAll();
		
		List<AdminGetAllCategoryResponse> response = categories.stream()
				.map(filmCategory -> this.modelMapperService.forResponse()
						.map(filmCategory, AdminGetAllCategoryResponse.class)).collect(Collectors.toList());
		
		return response;
	}


	@Override
	public List<GetFilmByCategoryResponse> getFilmByCategoryResponse(String filmCategoryName) {
		FilmCategory category = filmCategoryRepository.findByName(filmCategoryName);
		
		List<Film> films = category.getFilms();
		
		List<GetFilmByCategoryResponse> response = films.stream()
				.map(film -> this.modelMapperService.forResponse()
						.map(film, GetFilmByCategoryResponse.class)).collect(Collectors.toList());
		
		return response;
	}

	@Override
	public void add(CreateFilmCategoryRequest createFilmCategoryRequest) {
		
		this.filmCategoryBusinessRules.checkIfFilmCategoryNameExists(createFilmCategoryRequest.getName());
		
		FilmCategory filmCategory =this.modelMapperService.forRequest()
				.map(createFilmCategoryRequest, FilmCategory.class);
		this.filmCategoryRepository.save(filmCategory);
		
	}

	@Override
	public void update(UpdateFilmCategoryRequest updateFilmCategoryRequest) {
		
		this.filmCategoryBusinessRules.checkIfFilmCategoryNameExists(updateFilmCategoryRequest.getName());
		
		FilmCategory filmCategory =this.modelMapperService.forRequest()
				.map(updateFilmCategoryRequest, FilmCategory.class);
		this.filmCategoryRepository.save(filmCategory);
		
	}

	@Override
	public void delete(int id) {
		
		this.filmCategoryRepository.deleteById(id);
		
	}

}
