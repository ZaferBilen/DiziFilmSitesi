package proje.filmSitesi.service.interfaces;

public interface EmailDao {

	void dogrulamaKoduGonder(String toEmail, String dogrulamaKodu);
	
}
