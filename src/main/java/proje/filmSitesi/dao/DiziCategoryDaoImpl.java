package proje.filmSitesi.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.Dizi;
import proje.filmSitesi.model.DiziCategory;
import proje.filmSitesi.repository.DiziCategoryRepository;
import proje.filmSitesi.requests.category.CreateDiziCategoryRequest;
import proje.filmSitesi.requests.category.UpdateDiziCategoryRequest;
import proje.filmSitesi.responses.category.AdminGetAllDiziCategoryResponse;
import proje.filmSitesi.responses.category.GetAllDiziCategoryResponse;
import proje.filmSitesi.responses.category.GetDiziByCategoryResponse;

@Service
@AllArgsConstructor
public class DiziCategoryDaoImpl implements DiziCategoryDao {
	
	private DiziCategoryRepository diziCategoryRepository;
	private IModelMapperService modelMapperService;

	@Override
	public List<GetAllDiziCategoryResponse> getAllDiziCategoryResponse() {
		
		List<DiziCategory> categories = diziCategoryRepository.findAll();
		
		List<GetAllDiziCategoryResponse> response = categories.stream()
				.map(diziCategory -> this.modelMapperService.forResponse()
						.map(diziCategory, GetAllDiziCategoryResponse.class)).collect(Collectors.toList());
		
		return response;
	}
	
	

	@Override
	public List<AdminGetAllDiziCategoryResponse> adminGetAllDiziCategoryResponse() {
		
		List<DiziCategory> categories = diziCategoryRepository.findAll();
		
		List<AdminGetAllDiziCategoryResponse> response = categories.stream()
				.map(diziCategory -> this.modelMapperService.forResponse()
						.map(diziCategory, AdminGetAllDiziCategoryResponse.class)).collect(Collectors.toList());
		
		return response;
	}

	
	
	@Override
	public List<GetDiziByCategoryResponse> getDiziByCategoryResponse(String diziCategoryName) {
		
		DiziCategory category = diziCategoryRepository.findByName(diziCategoryName);
		
		List<Dizi> diziler = category.getDiziler();
		
		List<GetDiziByCategoryResponse> response = diziler.stream()
				.map(dizi -> this.modelMapperService.forResponse()
						.map(dizi, GetDiziByCategoryResponse.class)).collect(Collectors.toList());
		
		return response;
	}

	
	
	@Override
	public void add(CreateDiziCategoryRequest createDiziCategoryRequest) {
		
		DiziCategory diziCategory =this.modelMapperService.forRequest()
				.map(createDiziCategoryRequest, DiziCategory.class);
		this.diziCategoryRepository.save(diziCategory);
		
	}

	
	
	@Override
	public void update(UpdateDiziCategoryRequest updateDiziCategoryRequest) {
		
		DiziCategory diziCategory =this.modelMapperService.forRequest()
				.map(updateDiziCategoryRequest, DiziCategory.class);
		this.diziCategoryRepository.save(diziCategory);
		
	}

	
	
	@Override
	public void delete(int id) {
		
		this.diziCategoryRepository.deleteById(id);
		
	}

	
	
}
