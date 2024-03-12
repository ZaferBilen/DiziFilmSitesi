package proje.filmSitesi.service.interfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import proje.filmSitesi.requests.dizi.AddBolumRequest;
import proje.filmSitesi.requests.dizi.UpdateBolumRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllBolumResponse;
import proje.filmSitesi.responses.dizi.BolumResponse;
import proje.filmSitesi.responses.dizi.GetAllBolumResponse;

public interface BolumDao {
	
	BolumResponse addBolum(AddBolumRequest addBolumRequest);
	
	BolumResponse updateBolum(UpdateBolumRequest updateBolumRequest);
	
	BolumResponse deleteBolum(Long id);
	
	List<GetAllBolumResponse> getAllBolumResponse();
	
	List<AdminGetAllBolumResponse> adminGetAllBolumResponse();
	
	BolumResponse uploadBolum(Long bolumId, MultipartFile file) throws IOException, GeneralSecurityException;

	

	

	
}
