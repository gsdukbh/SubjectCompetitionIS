package werls.scis.util;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年03月23日 22:50
 */
@SpringBootTest
class FileUploaderTest {
    @Autowired
    FileUploader fileUploader;
    @Test
    void putObject() {
      try {
          fileUploader.makeBucket("img");
      }catch (Exception e){
          System.out.println(e);
      }
    }

    @Test
    void getObject() {
        try {
            fileUploader.getObject("img","wa.jpg","wa.jpg");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}