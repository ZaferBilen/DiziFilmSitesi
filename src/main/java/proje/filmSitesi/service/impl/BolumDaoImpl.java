package proje.filmSitesi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.exception.BolumNotFoundException;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.Bolum;
import proje.filmSitesi.model.Dizi;
import proje.filmSitesi.repository.BolumRespository;
import proje.filmSitesi.repository.DiziRepository;
import proje.filmSitesi.requests.dizi.AddBolumRequest;
import proje.filmSitesi.requests.dizi.UpdateBolumRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllBolumResponse;
import proje.filmSitesi.responses.dizi.BolumResponse;
import proje.filmSitesi.responses.dizi.GetAllBolumResponse;
import proje.filmSitesi.service.interfaces.BolumDao;

@Service
@AllArgsConstructor
public class BolumDaoImpl implements BolumDao{
	
	private DiziRepository diziRepository;
	private BolumRespository bolumRepository;
	private IModelMapperService modelMapperService;

	@Override
	public BolumResponse addBolum(AddBolumRequest addBolumRequest) {
		
	    Dizi dizi = diziRepository.findById(addBolumRequest.getDiziId())
	            .orElseThrow(() -> new RuntimeException("Dizi bulunamadı"));

	    Bolum bolum = modelMapperService.forRequest().map(addBolumRequest, Bolum.class);

	    bolum.setDizi(dizi);

	    bolumRepository.save(bolum);
	    
	    return responseBolum(bolum);
	}

	private BolumResponse responseBolum(Bolum bolum) {
		return new BolumResponse(
				bolum.getId(),
				bolum.getBolum(),
				bolum.getPath(),
				bolum.getDizi().getId(),
				bolum.getDizi().getName()
				);
	}

	@Override
	public BolumResponse updateBolum(UpdateBolumRequest updateBolumRequest) {
		
		Long bolumId = updateBolumRequest.getId();

	    Bolum existingBolum = bolumRepository.findById(bolumId)
	            .orElseThrow(() -> new RuntimeException("Bolum bulunamadı"));

	    existingBolum.setDizi(diziRepository.findById(updateBolumRequest.getDiziId())
	            .orElseThrow(() -> new RuntimeException("Dizi bulunamadı")));

	    modelMapperService.forRequest().map(updateBolumRequest, existingBolum);

	    Bolum updatedBolum = bolumRepository.save(existingBolum);
	    
	    return responseBolum(updatedBolum);
	}
		

	@Override
	public BolumResponse deleteBolum(Long id) {
		
		Bolum bolum = this.bolumRepository.findById(id)
				.orElseThrow(() -> new BolumNotFoundException("Dizi bulunamadı"));
		
		this.bolumRepository.deleteById(id);
		
		return responseBolum(bolum);
		
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
	public BolumResponse uploadBolum(Long bolumId, String path) {
		
		Bolum bolum = bolumRepository.findById(bolumId).orElse(null);
		if(bolum != null) {
			bolum.setPath(path);
			bolumRepository.save(bolum);
			
			return responseBolum(bolum);
		}
		return null;
				
	}

}
