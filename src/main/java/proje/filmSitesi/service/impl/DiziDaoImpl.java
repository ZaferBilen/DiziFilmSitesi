package proje.filmSitesi.service.impl;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.exception.DiziNotFoundException;
import proje.filmSitesi.core.utilities.mappers.IModelMapperService;
import proje.filmSitesi.googleDrive.GoogleService;
import proje.filmSitesi.googleDrive.ResFragman;
import proje.filmSitesi.googleDrive.ResKapak;
import proje.filmSitesi.model.Bolum;
import proje.filmSitesi.model.Dizi;
import proje.filmSitesi.repository.DiziRepository;
import proje.filmSitesi.requests.dizi.CreateDiziRequest;
import proje.filmSitesi.requests.dizi.UpdateDiziRequest;
import proje.filmSitesi.responses.dizi.AdminGetAllDiziResponse;
import proje.filmSitesi.responses.dizi.BolumResponse;
import proje.filmSitesi.responses.dizi.DiziResponse;
import proje.filmSitesi.responses.dizi.GetAllDiziResponse;
import proje.filmSitesi.responses.dizi.GetDiziByNameResponse;
import proje.filmSitesi.service.interfaces.DiziDao;

@Service
@AllArgsConstructor
public class DiziDaoImpl implements DiziDao{
	
	private DiziRepository diziRepository;
	private IModelMapperService modelMapperService;
	
	private GoogleService driveService;

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


	public DiziResponse responseDizi(Dizi dizi) {

	    List<BolumResponse> bolumList = new ArrayList<>();

	    if (dizi.getBolumList() != null && !dizi.getBolumList().isEmpty()) {
	        for (Bolum bolum : dizi.getBolumList()) {
	            bolumList.add(new BolumResponse(
	                    bolum.getId(),
	                    bolum.getBolum(),
	                    bolum.getBolumPath(),
	                    dizi.getId(), 
	                    dizi.getName() 
	            ));
	        }
	    }

	    return new DiziResponse(
	            dizi.getId(),
	            dizi.getName(),
	            dizi.getKonu(),
	            dizi.getYili(),
	            dizi.getYonetmen(),
	            dizi.getKapakPath(),
	            dizi.getFragmanPath(),
	            bolumList,
	            dizi.getDiziCategory() != null ? dizi.getDiziCategory().getDkid() : null
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
	public DiziResponse uploadKapak(Long diziId, MultipartFile file) throws IOException, GeneralSecurityException {
	    Dizi dizi = diziRepository.findById(diziId).orElse(null);
	    if (dizi != null) {
	        ResKapak res = driveService.uploadImageToDriveKapak(convert(file));
	        if (res != null && res.getStatusKapak() == 200) {
	            dizi.setKapakPath(res.getUrlKapak());
	            diziRepository.save(dizi);
	            return responseDizi(dizi);
	        }
	    }
	    return null;
	}

	
	@Override
	public DiziResponse uploadFragman(Long diziId, MultipartFile file) throws IOException, GeneralSecurityException {
	    Dizi dizi = diziRepository.findById(diziId).orElse(null);
	    if (dizi != null) {
	    	ResFragman res = driveService.uploadVideoToDriveFragman(convert(file));
	        if (res != null && res.getStatusFragman() == 200) {
	            dizi.setFragmanPath(res.getUrlFragman());
	            diziRepository.save(dizi);
	            return responseDizi(dizi);
	        }
	    }
	    return null;
	}
	
	private File convert(MultipartFile file) throws IOException {
        File convFile = File.createTempFile("temp", null);
        file.transferTo(convFile);
        return convFile;
    }

	
}

