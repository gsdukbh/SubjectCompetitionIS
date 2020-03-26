package werls.scis.util;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * 文档 https://docs.minio.io/cn/
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年03月23日 22:20
 */
@Component
public class FileUploader {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String A_KEY = "1361404576";
    private static final String S_KEY = "lijiawei+520";
    public static final String ENDPOINT = "https://data.werls.top";
    private static final String IMGBUCKET = "img";
    MinioClient minioClient = new MinioClient(ENDPOINT, A_KEY, S_KEY);
    
    public FileUploader() throws Exception {
        super();
    }

    public static void main(String[] args) throws Exception {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, A_KEY, S_KEY);
            minioClient.putObject("java", "4.2.1.zip", "D:\\github\\SubjectCompetitionIS\\README.md");
        } catch (MinioException e) {
            System.out.println(e);
        }
    }

    public void makeBucket(String bucketName) throws Exception {
        logger.info(new Date().toString()+"创建了存储对象："+bucketName);
        minioClient.makeBucket(bucketName);
    }

    public void putObjectImg(String objectName,
                             InputStream stream,
                             long size,
                             String contentType) throws Exception {
        logger.info(new Date().toString()+" 上传了文件："+objectName+"  文件大小："+size+" 文件类型："+contentType);
        minioClient.putObject(IMGBUCKET, objectName, stream, size, contentType);
    }
    public InputStream getObject(String bucketName, String objectName) throws Exception{

        return minioClient.getObject(bucketName,objectName);
    }
    public String URl(){
        return ENDPOINT;
    }
    public void getObject(String bucketName, String objectName,String fileName) throws Exception{
        minioClient.getObject(bucketName,objectName,fileName);
    }
}
