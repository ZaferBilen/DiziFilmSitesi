package proje.filmSitesi.service.interfaces;

import java.util.List;

import proje.filmSitesi.requests.dizi.CreateDiziRequest;
import proje.filmSitesi.requests.dizi.UpdateDiziRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllDiziResponse;
import proje.filmSitesi.responses.dizi.DiziResponse;
import proje.filmSitesi.responses.dizi.GetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetDiziByNameResponse;

public interface DiziDao {

	List<GetAllDiziResponse> getAllDiziResponse();
	
	List<AdminGetAllDiziResponse> adminGetAllDiziResponse();
	
	GetDiziByNameResponse getByDiziName(String diziName);
	
	DiziResponse add(CreateDiziRequest createDiziRequest);
	
	DiziResponse update(UpdateDiziRequest updateDiziRequest); 
	
	DiziResponse delete(Long id);
	
	DiziResponse uploadKapak(Long diziId , String kapakPath);
	
	DiziResponse uploadFragman(Long diziId , String fragmanPath);
}
