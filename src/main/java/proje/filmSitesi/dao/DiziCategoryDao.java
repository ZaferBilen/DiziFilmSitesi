package proje.filmSitesi.dao;

import java.util.List;

import proje.filmSitesi.requests.category.CreateDiziCategoryRequest;
import proje.filmSitesi.requests.category.UpdateDiziCategoryRequest;
import proje.filmSitesi.responses.category.AdminGetAllDiziCategoryResponse;
import proje.filmSitesi.responses.category.GetAllDiziCategoryResponse;
import proje.filmSitesi.responses.category.GetDiziByCategoryResponse;

public interface DiziCategoryDao {
	
	List<GetAllDiziCategoryResponse> getAllDiziCategoryResponse();
	List<AdminGetAllDiziCategoryResponse> adminGetAllDiziCategoryResponse();
	List<GetDiziByCategoryResponse> getDiziByCategoryResponse(String diziCategoryName);
	void add(CreateDiziCategoryRequest createDiziCategoryRequest);
	void update(UpdateDiziCategoryRequest updateDiziCategoryRequest);
	void delete(int id);

}
