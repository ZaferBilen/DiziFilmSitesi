package proje.filmSitesi.dao;

import java.util.List;

import proje.filmSitesi.requests.dizi.CreateDiziRequest;
import proje.filmSitesi.requests.dizi.UpdateDiziRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetDiziByNameResponse;

public interface DiziDao {

	List<GetAllDiziResponse> getAllDiziResponse();
	
	List<AdminGetAllDiziResponse> adminGetAllDiziResponse();
	
	GetDiziByNameResponse getByDiziName(String diziName);
	
	void add(CreateDiziRequest createDiziRequest);
	
	void update(UpdateDiziRequest updateDiziRequest); 
	
	void delete(Long id);
	
	void uploadKapak(Long diziId , String kapakPath);
	
	void uploadFragman(Long diziId , String fragmanPath);
}
