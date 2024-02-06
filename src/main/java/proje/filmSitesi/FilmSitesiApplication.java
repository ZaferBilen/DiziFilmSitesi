package proje.filmSitesi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import proje.filmSitesi.core.utilities.exception.ProblemDetails;

@SpringBootApplication
@RestControllerAdvice
public class FilmSitesiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmSitesiApplication.class, args);
	}
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetails aramaHatası(IllegalArgumentException illArgumentException) {
		
		ProblemDetails problemDetails = new ProblemDetails();
		problemDetails.setMessage(illArgumentException.getMessage());
		
		return problemDetails;
		
	}
	
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetails gecersizIdHatasi(JpaObjectRetrievalFailureException jpaObjectRetrievalFailureException) {
		
		ProblemDetails problemDetails = new ProblemDetails();
		problemDetails.setMessage(jpaObjectRetrievalFailureException.getMessage());
		
		return problemDetails;
		
	}
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetails icerikHatasi(HttpMessageNotReadableException httpMessageNotReadableException) {
		
		ProblemDetails problemDetails = new ProblemDetails();
		problemDetails.setMessage(httpMessageNotReadableException.getMessage());
		
		return problemDetails;
		
	}
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetails runtimeException(RuntimeException runtimeException) {
		
		ProblemDetails problemDetails = new ProblemDetails();
		problemDetails.setMessage(runtimeException.getMessage());
		
		return problemDetails;
		
	}
	
	@Bean   
	public ModelMapper getModelMapper() {
	return new ModelMapper();
	}

}