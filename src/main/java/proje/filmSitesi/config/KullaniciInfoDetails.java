package proje.filmSitesi.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import proje.filmSitesi.model.Kullanici;


public class KullaniciInfoDetails implements UserDetails {
	private Long id;
    private String email;
    private String sifre;
    private List<GrantedAuthority> roles;

    public KullaniciInfoDetails(Kullanici kullanici){
    	this.id = kullanici.getId();
        this.email = kullanici.getEmail();
        this.sifre = kullanici.getSifre();
        this.roles = List.of(new SimpleGrantedAuthority(kullanici.getRole().toString()));

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.sifre;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}