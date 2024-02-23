package proje.filmSitesi.config;

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
	                .authorizeHttpRequests(
	                		req->req.requestMatchers("/","/swagger-ui/**", "/v3/api-docs/**","/giris" , "/kullanici/kayıt", "/kullanici/kod")
	                		.permitAll()
	                		.requestMatchers("/kullanici/sil/{id}","/users/all","/admin/getallfilms","/admin/addfilm","/admin/updatefilm","/admin/{filmId}/upload-film"
	                				,"/admin/{filmId}/upload-kapak","/admin/{filmId}/upload-fragman","/admin/getallcategories","/admin/add-film-category"
	                				,"/admin/update-film-category","/admin/getalldizi","/admin/add-dizi","/admin/update-dizi","/admin/deletedizi/{id}"
	                				,"/admin/{diziId}/upload-kapak","/admin/{diziId}/upload-fragman","/admin/getalldizicategory","/admin/add-dizi-category"
	                				,"/admin/add-dizi-category","/admin/delete-dizi-category/{id}","/admin/addbolum","/admin/updatebolum"
	                				,"/admin/deletebolum/{id}","/admin/getallbolums","/admin/{bolumId}/upload-bolum")
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
        
		  
	  }