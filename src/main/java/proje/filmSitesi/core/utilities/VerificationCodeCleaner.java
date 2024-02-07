package proje.filmSitesi.core.utilities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import proje.filmSitesi.model.Kullanici;
import proje.filmSitesi.repository.KullaniciRepository;

@Component
public class VerificationCodeCleaner {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Scheduled(fixedRate = 120000)
    public void cleanExpiredVerificationCodes() {
        LocalDateTime now = LocalDateTime.now();
        List<Kullanici> users = kullaniciRepository.findAll();
        for (Kullanici user : users) {
            if (user.getDoğrulamaKoduSonKullanmaTarihi().isBefore(now)) {
                user.setGeciciDogrulamaKodu(null);
                user.setDoğrulamaKoduSonKullanmaTarihi(null);
                kullaniciRepository.save(user);
            }
        }
    }
}