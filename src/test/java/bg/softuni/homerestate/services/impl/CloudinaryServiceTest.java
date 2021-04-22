package bg.softuni.homerestate.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CloudinaryServiceTest {
    private CloudinaryServiceImpl serviceToBeTested;

    @Mock
    Cloudinary mockCloudinary;
    @Mock
    Uploader mockUploader;

    @BeforeEach
    public void setUp() {
        serviceToBeTested =
                new CloudinaryServiceImpl(mockCloudinary);
    }

    @Test
    public void uploadImageTest() throws IOException {
        Path path = Paths.get("/path/to/the/file.txt");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException ignored) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        File tmpFile = File.createTempFile("tmp",result.getOriginalFilename());
        result.transferTo(tmpFile);
        Map<String, Object> config = new HashMap<>();
        config.put("url",1);
        config.put("api_key",2);
        config.put("api_secret",3);
        when(mockCloudinary.uploader()).thenReturn(mockUploader);
        when(mockUploader.upload(any(),anyMap())).thenReturn(config);
        String actual = serviceToBeTested.uploadImage(result);
        Mockito.verify(mockCloudinary,times(1)).uploader();
        Assertions.assertEquals("1",actual);
    }


}
