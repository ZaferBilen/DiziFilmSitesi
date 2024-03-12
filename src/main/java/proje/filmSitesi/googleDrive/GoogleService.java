package proje.filmSitesi.googleDrive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

@org.springframework.stereotype.Service
public class GoogleService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoodleCredentials();

    private static String getPathToGoodleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        return filePath.toString();
    }

    public ResFragman uploadVideoToDriveFragman(File file) throws GeneralSecurityException, IOException {
        ResFragman res = new ResFragman();

        try {
            String folderId = "1joi1ouv0p7VOzzrGvHf6fsfjjQNr1rjv";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            
            String mimeType = Files.probeContentType(file.toPath());
            
            FileContent mediaContent = new FileContent(mimeType, file);
            
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
                
            String videoUrlFragman = "https://drive.google.com/file/d/" + uploadedFile.getId();
            System.out.println("Video URLFragman: " + videoUrlFragman);
            
            file.delete();
            
            res.setStatusFragman(200);
            res.setMessageFragman("Video başarıyla Google Drive'a yüklendi");
            res.setUrlFragman(videoUrlFragman);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            res.setStatusFragman(500);
            res.setMessageFragman(e.getMessage());
        }
        return res;
    }

    public ResKapak uploadImageToDriveKapak(File file) throws GeneralSecurityException, IOException {
        ResKapak res = new ResKapak();

        try {
            String folderId = "1joi1ouv0p7VOzzrGvHf6fsfjjQNr1rjv";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            
            String mimeType = Files.probeContentType(file.toPath());
            
            FileContent mediaContent = new FileContent(mimeType, file);
            
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrlKapak = "https://lh3.googleusercontent.com/d/" + uploadedFile.getId();
            System.out.println("IMAGE URLKapak: " + imageUrlKapak);
            file.delete();
            res.setStatusKapak(200);
            res.setMessageKapak("Image Successfully Uploaded To Drive");
            res.setUrlKapak(imageUrlKapak);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            res.setStatusKapak(500);
            res.setMessageKapak(e.getMessage());
        }
        return res;
    }
    
    public ResBolum uploadVideoToDriveBolum(File file) throws GeneralSecurityException, IOException {
        ResBolum res = new ResBolum();

        try {
            String folderId = "1joi1ouv0p7VOzzrGvHf6fsfjjQNr1rjv";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            
            String mimeType = Files.probeContentType(file.toPath());
            
            FileContent mediaContent = new FileContent(mimeType, file);
            
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            
            String videoUrlBolum = "https://drive.google.com/file/d/" + uploadedFile.getId();
            System.out.println("Video URLBolum: " + videoUrlBolum);
            
            file.delete();
            
            res.setStatusBolum(200);
            res.setMessageBolum("Video successfully uploaded to Google Drive");
            res.setUrlBolum(videoUrlBolum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            res.setStatusBolum(500);
            res.setMessageBolum(e.getMessage());
        }
        return res;
    }
   

    
    private Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();
        
    }
        //----------------------------------------------------------------------------------------
        
        
        public ResFragmanFilm uploadVideoToDriveFragmanFilm(File file) throws GeneralSecurityException, IOException {
        	ResFragmanFilm res = new ResFragmanFilm();

            try {
                String folderId = "1joi1ouv0p7VOzzrGvHf6fsfjjQNr1rjv";
                Drive drive = createDriveService();
                com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
                fileMetaData.setName(file.getName());
                fileMetaData.setParents(Collections.singletonList(folderId));
                
                String mimeType = Files.probeContentType(file.toPath());
                
                FileContent mediaContent = new FileContent(mimeType, file);
                
                com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                        .setFields("id").execute();
                
                String videoUrlFragmanFilm = "https://drive.google.com/file/d/" + uploadedFile.getId();
                System.out.println("Video URLFilmFragman: " + videoUrlFragmanFilm);
                
                file.delete();
                
                res.setStatusFragmanFilm(200);
                res.setMessageFragmanFilm("Video başarıyla Google Drive'a yüklendi");
                res.setUrlFragmanFilm(videoUrlFragmanFilm);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                res.setStatusFragmanFilm(500);
                res.setMessageFragmanFilm(e.getMessage());
            }
            return res;
        }
        

        public ResKapakFilm uploadImageToDriveKapakFilm(File file) throws GeneralSecurityException, IOException {
        	
        	ResKapakFilm res = new ResKapakFilm();

            try {
                String folderId = "1joi1ouv0p7VOzzrGvHf6fsfjjQNr1rjv";
                Drive drive = createDriveService();
                com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
                fileMetaData.setName(file.getName());
                fileMetaData.setParents(Collections.singletonList(folderId));
                
                String mimeType = Files.probeContentType(file.toPath());
                
                FileContent mediaContent = new FileContent(mimeType, file);
                
                com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                        .setFields("id").execute();
                String imageUrlKapakFilm = "https://lh3.googleusercontent.com/d/" + uploadedFile.getId();
                System.out.println("IMAGE URLFilmKapak: " + imageUrlKapakFilm);
                file.delete();
                res.setStatusKapakFilm(200);
                res.setMessageKapakFilm("Image Successfully Uploaded To Drive");
                res.setUrlKapakFilm(imageUrlKapakFilm);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                res.setStatusKapakFilm(500);
                res.setMessageKapakFilm(e.getMessage());
            }
            return res;
        }
        
        public ResFilm uploadVideoToDriveFilm(File file) throws GeneralSecurityException, IOException {
        	ResFilm res = new ResFilm();

            try {
                String folderId = "1joi1ouv0p7VOzzrGvHf6fsfjjQNr1rjv";
                Drive drive = createDriveService();
                com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
                fileMetaData.setName(file.getName());
                fileMetaData.setParents(Collections.singletonList(folderId));
                
                String mimeType = Files.probeContentType(file.toPath());
                
                FileContent mediaContent = new FileContent(mimeType, file);
                
                com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                        .setFields("id").execute();
                
                
                String videoUrlFilm = "https://drive.google.com/file/d/" + uploadedFile.getId();
                System.out.println("Video URLFilm: " + videoUrlFilm);
                
                file.delete();
                
                res.setStatusFilm(200);
                res.setMessageFilm("Video başarıyla Google Drive'a yüklendi");
                res.setUrlFilm(videoUrlFilm);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                res.setStatusFilm(500);
                res.setMessageFilm(e.getMessage());
            }
            return res;
        }
        
   
}