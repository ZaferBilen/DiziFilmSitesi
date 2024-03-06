package proje.filmSitesi.service.interfaces;

import java.util.List;

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
	
	BolumResponse uploadBolum(Long bolumId , String path);
}
