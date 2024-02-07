package proje.filmSitesi.core.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DogrulamaKoduOnBellegi {
    private static final Map<String, String> verificationCodes = new HashMap<>();
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    
    public static void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
        scheduleCodeRemoval(email);
    }

    public static String getVerificationCode(String email) {
        return verificationCodes.get(email);
    }

    public static void removeVerificationCode(String email) {
        verificationCodes.remove(email);
    }

    private static void scheduleCodeRemoval(String email) {
    	
        executorService.schedule(() -> {
            removeVerificationCode(email);
        }, 120, TimeUnit.SECONDS);
    }
}