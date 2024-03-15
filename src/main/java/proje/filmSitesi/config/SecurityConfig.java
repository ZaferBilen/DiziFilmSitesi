package proje.filmSitesi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;
import proje.filmSitesi.core.utilities.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
		
	private final KullaniciDetailService kullaniciDetailService;
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final CustomLogoutHandler logoutHandler;
	
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		  
		  return http
				  .csrf(AbstractHttpConfigurer::disable)
				  .cors(withDefaults())     
	                .authorizeHttpRequests(
	                		req->req.requestMatchers("/","/swagger-ui/**", "/v3/api-docs/**","/giris" , "/kullanici/kayıt", "/kullanici/kod","/users","/films","/dizis",
	                				"/filmkategori", "/dizikategori")
	                		.permitAll()
	                		.requestMatchers("/kullanici/sil/{id}","/users/all"
	                				,"/film/admin/getallfilms","/film/admin/addfilm","/film/admin/updatefilm","/film/admin/deletefilm/{id}"
	                				,"/film/admin/{filmId}/upload-film","/film/admin/{filmId}/upload-kapak","/film/admin/{filmId}/upload-fragman"
	                				,"/film-categori/admin/getallcategories","/film-categori/admin/add-film-category","/film-categori/admin/update-film-category","/film-categori/admin/delete-film-category/{id}"
	                				,"/dizi/admin/getalldizi","/dizi/admin/add-dizi","/dizi/admin/update-dizi","/dizi/admin/deletedizi/{id}","/dizi/admin/{diziId}/upload-kapak","dizi/admin/{diziId}/upload-fragman"
	                				,"/dizi-categori/admin/getalldizicategory","/dizi-categori/admin/add-dizi-category","/dizi-categori/admin/update-dizi-category","/dizi-categori/admin/delete-dizi-category/{id}"
	                				,"/bolum/admin/addbolum","/bolum/admin/updatebolum","/bolum/admin/deletebolum/{id}","/bolum/admin/getallbolums","/bolum/admin/{bolumId}/upload-bolum")
	                		.hasAuthority("ADMIN")
	                		.anyRequest()
                            .authenticated()
	                		).userDetailsService(kullaniciDetailService)
	                .sessionManagement(session->session
	                		.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	                
	                .exceptionHandling(
	                        e->e.accessDeniedHandler(
	                                        (request, response, accessDeniedException)->response.setStatus(403)
	                                )
	                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
	                
	               .logout(l->l
	                        .logoutUrl("/cikis")
	                        .addLogoutHandler(logoutHandler)
	                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
	                        ))
	                
	                .build();
		  
	  }  
		  
		  @Bean
		    public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }

		    @Bean
		    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		        return configuration.getAuthenticationManager();
		    }
		    
		    @Bean
		    public CorsConfigurationSource corsConfigurationSource() {
		        CorsConfiguration configuration = new CorsConfiguration();
		        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://drive.google.com"));
		        configuration.setAllowedMethods(Arrays.asList("*")); 
		        configuration.setAllowedHeaders(Arrays.asList("*"));
		        configuration.setAllowCredentials(true);

		        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		        source.registerCorsConfiguration("/**", configuration);
		        return source;
		    }
		    
		    
	  }