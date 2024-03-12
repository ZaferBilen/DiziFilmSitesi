package proje.filmSitesi.service.interfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
	
	ResponseEntity<DiziResponse> uploadKapak(Long diziId, MultipartFile file) throws IOException, GeneralSecurityException;
	
	ResponseEntity<DiziResponse> uploadFragman(Long diziId, MultipartFile file) throws IOException, GeneralSecurityException;

	

	
}
