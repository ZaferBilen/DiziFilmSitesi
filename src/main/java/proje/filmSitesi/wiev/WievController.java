package proje.filmSitesi.wiev;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import proje.filmSitesi.responses.category.GetAllDiziCategoryResponse;
import proje.filmSitesi.responses.category.GetAllFilmCategoryResponse;
import proje.filmSitesi.responses.dizi.GetAllDiziResponse;
import proje.filmSitesi.responses.film.GetAllFilmsResponse;
import proje.filmSitesi.responses.kullanici.GetAllKullaniciResponse;
import proje.filmSitesi.service.interfaces.DiziCategoryDao;
import proje.filmSitesi.service.interfaces.DiziDao;
import proje.filmSitesi.service.interfaces.FilmCategoryDao;
import proje.filmSitesi.service.interfaces.FilmDao;
import proje.filmSitesi.service.interfaces.KullaniciDao;

@Controller
@AllArgsConstructor
public class WievController {
	
	private KullaniciDao kullaniciService;
	private FilmDao filmDao;
	private DiziDao diziDao;
	private FilmCategoryDao filmCategoryDao;
	private DiziCategoryDao diziCategoryDao;

	
    @GetMapping("/users")
    public String getDataUsers(Model model) {
        List<GetAllKullaniciResponse> users = kullaniciService.getAllUsers();
        model.addAttribute("users", users); 
        return "users";
    }
    
    @GetMapping("/films")
    public String getDataFilms(Model model) {
        List<GetAllFilmsResponse> films = filmDao.getAllFilmsResponse();
        model.addAttribute("films", films); 
        return "films"; 
    }
    
    @GetMapping("/dizis")
    public String getDataDizis(Model model) {
        List<GetAllDiziResponse> dizis = diziDao.getAllDiziResponse();
        model.addAttribute("dizis", dizis); 
        return "dizis"; 
    }

    @GetMapping("/filmkategori")
    public String getDataFilmKategorileri(Model model) {
        List<GetAllFilmCategoryResponse> categories = filmCategoryDao.getAllFilmCategoryResponse();
        model.addAttribute("filmkategorileri", categories); 
        return "filmKategorileri"; 
    }
    
    @GetMapping("/dizikategori")
    public String getDataDiziKategorileri(Model model) {
        List<GetAllDiziCategoryResponse> categories = diziCategoryDao.getAllDiziCategoryResponse();
        model.addAttribute("dizikategorileri", categories); 
        return "diziKategorileri"; 
    }

}
