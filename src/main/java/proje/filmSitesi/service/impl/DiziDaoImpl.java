package proje.filmSitesi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.exception.DiziNotFoundException;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.Dizi;
import proje.filmSitesi.repository.DiziRepository;
import proje.filmSitesi.requests.dizi.CreateDiziRequest;
import proje.filmSitesi.requests.dizi.UpdateDiziRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllDiziResponse;
import proje.filmSitesi.responses.dizi.DiziResponse;
import proje.filmSitesi.responses.dizi.GetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetDiziByNameResponse;
import proje.filmSitesi.service.interfaces.DiziDao;

@Service
@AllArgsConstructor
public class DiziDaoImpl implements DiziDao{
	
	private DiziRepository diziRepository;
	private IModelMapperService modelMapperService;

	@Override
	public List<GetAllDiziResponse> getAllDiziResponse() {
		List<Dizi> diziler = diziRepository.findAll();
		
		List<GetAllDiziResponse> diziResponse = diziler.stream()
				.map(dizi -> this.modelMapperService.forResponse()
						.map(dizi, GetAllDiziResponse.class)).collect(Collectors.toList());
		
		return diziResponse;
	}
	

	@Override
	public List<AdminGetAllDiziResponse> adminGetAllDiziResponse() {
		List<Dizi> diziler = diziRepository.findAll();
		
		List<AdminGetAllDiziResponse> diziResponse = diziler.stream()
				.map(dizi -> this.modelMapperService.forResponse()
						.map(dizi, AdminGetAllDiziResponse.class)).collect(Collectors.toList());
		
		return diziResponse;
	}

	@Override
	public GetDiziByNameResponse getByDiziName(String diziName) {
		Dizi dizi = this.diziRepository.findByName(diziName);
		
		GetDiziByNameResponse response = this.modelMapperService.forResponse()
				.map(dizi, GetDiziByNameResponse.class);
		return response;
	}
	
	
	@Override
	public DiziResponse add(CreateDiziRequest createDiziRequest) {
		
		Dizi dizi = this.modelMapperService.forRequest()
				.map(createDiziRequest, Dizi.class);
		
		dizi = this.diziRepository.save(dizi);
		
		return responseDizi(dizi);
		
	}


	private DiziResponse responseDizi(Dizi dizi) {
		return new DiziResponse(
				dizi.getId(),
		        dizi.getName(),
		        dizi.getKonu(),
		        dizi.getYili(),
		        dizi.getYonetmen(),
		        dizi.getFragmanPath(),
		        dizi.getKapakPath(),
		        dizi.getDiziCategory().getDkid()
		 );
	}

	@Override
	public DiziResponse update(UpdateDiziRequest updateDiziRequest) {
		
		Dizi dizi = this.modelMapperService.forRequest()
				.map(updateDiziRequest, Dizi.class);
		
		dizi = this.diziRepository.save(dizi);
		
		return responseDizi(dizi);
		
	}

	@Override
	public DiziResponse  delete(Long id) {
		Dizi dizi =diziRepository.findById(id)
				.orElseThrow(() -> new DiziNotFoundException("Dizi bulunamadı"));
                
		this.diziRepository.deleteById(id);
		
		return responseDizi(dizi);
		
	}

	@Override
	public DiziResponse uploadKapak(Long diziId, String kapakPath) {
		
		 Dizi dizi = diziRepository.findById(diziId).orElse(null);
		 if(dizi !=null) {
			 dizi.setKapakPath(kapakPath);
			 diziRepository.save(dizi);
			 
			 return responseDizi(dizi);
			 
		 }
		return null;
		
	}

	@Override
	public DiziResponse uploadFragman(Long diziId, String fragmanPath) {
		
		Dizi dizi = diziRepository.findById(diziId).orElse(null);
		 if(dizi !=null) {
			 dizi.setFragmanPath(fragmanPath);
			 diziRepository.save(dizi);
			 
			 return responseDizi(dizi);
			 
		 }
		return null;
	}

	
}

