package proje.filmSitesi.service.interfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.web.multipart.MultipartFile;

import proje.filmSitesi.requests.dizi.AddBolumRequest;
import proje.filmSitesi.requests.dizi.UpdateBolumRequest;
import proje.filmSitesi.responses.dizi.BolumResponse;

public interface BolumDao {
	
	BolumResponse addBolum(AddBolumRequest addBolumRequest);
	
	BolumResponse updateBolum(UpdateBolumRequest updateBolumRequest);
	
	BolumResponse deleteBolum(Long id);
	
	BolumResponse uploadBolum(Long bolumId, MultipartFile file) throws IOException, GeneralSecurityException;

	

	

	
}
