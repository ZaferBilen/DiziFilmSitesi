package proje.filmSitesi.dao;

import java.util.List;

import proje.filmSitesi.requests.category.CreateFilmCategoryRequest;
import proje.filmSitesi.requests.category.UpdateFilmCategoryRequest;
import proje.filmSitesi.responses.category.AdminGetAllCategoryResponse;
import proje.filmSitesi.responses.category.GetAllFilmCategoryResponse;
import proje.filmSitesi.responses.category.GetFilmByCategoryResponse;

public interface FilmCategoryDao {
	
	List<GetAllFilmCategoryResponse> getAllFilmCategoryResponse();
	List<AdminGetAllCategoryResponse> adminGetAllCategoryResponse();
	List<GetFilmByCategoryResponse> getFilmByCategoryResponse(String filmCategoryName);
	void add(CreateFilmCategoryRequest createFilmCategoryRequest);
	void update(UpdateFilmCategoryRequest updateFilmCategoryRequest);
	void delete(int id);
	
}
