package proje.filmSitesi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.Bolum;
import proje.filmSitesi.model.Dizi;
import proje.filmSitesi.repository.BolumRespository;
import proje.filmSitesi.repository.DiziRepository;
import proje.filmSitesi.requests.dizi.AddBolumRequest;
import proje.filmSitesi.requests.dizi.UpdateBolumRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllBolumResponse;
import proje.filmSitesi.responses.dizi.GetAllBolumResponse;
import proje.filmSitesi.service.interfaces.BolumDao;

@Service
@AllArgsConstructor
public class BolumDaoImpl implements BolumDao{
	
	private DiziRepository diziRepository;
	private BolumRespository bolumRepository;
	private IModelMapperService modelMapperService;

	@Override
	public void addBolum(AddBolumRequest addBolumRequest) {
		
	    Dizi dizi = diziRepository.findById(addBolumRequest.getDiziId())
	            .orElseThrow(() -> new RuntimeException("Dizi bulunamadı"));

	    Bolum bolum = modelMapperService.forRequest().map(addBolumRequest, Bolum.class);

	    bolum.setDizi(dizi);

	    bolumRepository.save(bolum);
	}

	@Override
	public void updateBolum(UpdateBolumRequest updateBolumRequest) {
		
		Long bolumId = updateBolumRequest.getId();

	    Bolum existingBolum = bolumRepository.findById(bolumId)
	            .orElseThrow(() -> new RuntimeException("Bolum bulunamadı"));

	    existingBolum.setDizi(diziRepository.findById(updateBolumRequest.getDiziId())
	            .orElseThrow(() -> new RuntimeException("Dizi bulunamadı")));

	    modelMapperService.forRequest().map(updateBolumRequest, existingBolum);

	    bolumRepository.save(existingBolum);
	}
		

	@Override
	public void deleteBolum(Long id) {
		
		this.bolumRepository.deleteById(id);
		
	}

	@Override
	public List<GetAllBolumResponse> getAllBolumResponse() {
		
		List<Bolum> bolums =bolumRepository.findAll();
		
		List<GetAllBolumResponse> response = bolums.stream()
				.map(bolum -> this.modelMapperService.forResponse()
						.map(bolum, GetAllBolumResponse.class)).collect(Collectors.toList());
		
		return response;
	}

	@Override
	public List<AdminGetAllBolumResponse> adminGetAllBolumResponse() {
		
		List<Bolum> bolums =bolumRepository.findAll();
		
		List<AdminGetAllBolumResponse> response = bolums.stream()
				.map(bolum -> this.modelMapperService.forResponse()
						.map(bolum, AdminGetAllBolumResponse.class)).collect(Collectors.toList());
		
		return response;	
	}

	@Override
	public void uploadBolum(Long bolumId, String path) {
		
		Bolum bolum = bolumRepository.findById(bolumId).orElse(null);
		if(bolum != null) {
			bolum.setPath(path);
			bolumRepository.save(bolum);
		}

				
	}

}
