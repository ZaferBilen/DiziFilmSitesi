package proje.filmSitesi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SpringBootApplication
@RestControllerAdvice
public class FilmSitesiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmSitesiApplication.class, args);
	}
	
	@Bean   
	public ModelMapper getModelMapper() {
	return new ModelMapper();
	}

}