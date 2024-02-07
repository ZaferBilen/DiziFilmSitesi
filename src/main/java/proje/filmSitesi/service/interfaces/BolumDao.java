package proje.filmSitesi.service.interfaces;

import java.util.List;

import proje.filmSitesi.requests.dizi.AddBolumRequest;
import proje.filmSitesi.requests.dizi.UpdateBolumRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllBolumResponse;
import proje.filmSitesi.responses.dizi.GetAllBolumResponse;

public interface BolumDao {
	
	void addBolum(AddBolumRequest addBolumRequest);
	
	void updateBolum(UpdateBolumRequest updateBolumRequest);
	
	void deleteBolum(Long id);
	
	List<GetAllBolumResponse> getAllBolumResponse();
	
	List<AdminGetAllBolumResponse> adminGetAllBolumResponse();
	
	void uploadBolum(Long bolumId , String path);
}
