package proje.filmSitesi.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import proje.filmSitesi.model.Kullanici;

@Service
@AllArgsConstructor
public class JwtService {
	
	private final String SECRET_KEY = "5471c3365d27095a24eba2a8d995cccedd0b766000b46526bfa093cf985fa96d";
	
	
	
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
	
    
    public boolean isValid(String token, UserDetails kullanici) {
        String email = extractUsername(token);
        return (email.equals(kullanici.getUsername())) && !isTokenExpired(token);
    }
    
	
    private boolean isTokenExpired(String token) {
		
    	return extractExpiration(token).before(new Date());
	}


    private Date extractExpiration(String token) {
        
    	return extractClaim(token, Claims::getExpiration);
    }


	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
	
	
	 private Claims extractAllClaims(String token) {
	        return Jwts
	        		.parser()
	        		.verifyWith(getSigninKey())
	        		.build()
	        		.parseSignedClaims(token)
	        		.getPayload();
	        		
	 }
    
	public String generateToken(Kullanici kullanici) {
		
		 String token = Jwts
	                .builder()
	                .subject(kullanici.getEmail())
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis()+ 24*60*60*1000))
	                .signWith(getSigninKey())
	                .compact();
		 
		 return token;
	                
	}

	private SecretKey getSigninKey() {
       
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	    }
	


}
