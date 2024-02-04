package proje.filmSitesi.core.utilities.mappers;

import org.modelmapper.ModelMapper;


public interface IModelMapperService {
	ModelMapper forResponse();
	ModelMapper forRequest();
}