package proje.filmSitesi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.model.Dizi;
import proje.filmSitesi.repository.DiziRepository;
import proje.filmSitesi.requests.dizi.CreateDiziRequest;
import proje.filmSitesi.requests.dizi.UpdateDiziRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllDiziResponse;
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
	public void add(CreateDiziRequest createDiziRequest) {
		
		Dizi dizi = this.modelMapperService.forRequest()
				.map(createDiziRequest, Dizi.class);
		
		this.diziRepository.save(dizi);
		
	}

	@Override
	public void update(UpdateDiziRequest updateDiziRequest) {
		
		Dizi dizi = this.modelMapperService.forRequest()
				.map(updateDiziRequest, Dizi.class);
		
		this.diziRepository.save(dizi);
		
	}

	@Override
	public void delete(Long id) {
		
		this.diziRepository.deleteById(id);
		
	}

	@Override
	public void uploadKapak(Long diziId, String kapakPath) {
		
		 Dizi dizi = diziRepository.findById(diziId).orElse(null);
		 if(dizi !=null) {
			 dizi.setKapakPath(kapakPath);
			 diziRepository.save(dizi);
			 
		 }
		
	}

	@Override
	public void uploadFragman(Long diziId, String fragmanPath) {
		
		Dizi dizi = diziRepository.findById(diziId).orElse(null);
		 if(dizi !=null) {
			 dizi.setKapakPath(fragmanPath);
			 diziRepository.save(dizi);
			 
		 }
		
	}

	
}

