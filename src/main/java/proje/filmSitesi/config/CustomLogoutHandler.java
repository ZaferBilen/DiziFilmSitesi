package proje.filmSitesi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import proje.filmSitesi.model.Token;
import proje.filmSitesi.repository.TokenRepository;

@AllArgsConstructor
@Configuration
public class CustomLogoutHandler implements LogoutHandler {
	
	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
	            return;
	        }

	 	String token = authHeader.substring(7);
	    Token storedToken = tokenRepository.findByToken(token).orElse(null);

	       if(storedToken != null) {
	    	   storedToken.setLoggedOut(true);
	    	   tokenRepository.save(storedToken);
	     }
	}
	
}