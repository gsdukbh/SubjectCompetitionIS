package werls.scis.util;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * 文档 https://docs.minio.io/cn/
 *
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年03月23日 22:20
 */
@Component
public class FileUploader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value(value = "${minio.ACCESSKEY}")
    private String aKey ="root";
    @Value(value = "${minio.SECRETKEY}")
    private String sKey;
    @Value(value = "${minio.url}")
    private String url;

    private static final String IMG = "img";
//    MinioClient minioClient = new MinioClient(url, aKey, sKey);

    public FileUploader() throws Exception {
        super();

    }

    public void makeBucket(String bucketName) throws Exception {
        MinioClient minioClient = new MinioClient(url, aKey, sKey);

        logger.info("创建了存储对象：" + bucketName);
        minioClient.makeBucket(bucketName);
    }

    @Async
    public void putObject(String bucketName,
                          String objectName,
                          InputStream stream,
                          long size,
                          String contentType) throws Exception {
        MinioClient minioClient = new MinioClient(url, aKey, sKey);

        logger.info(" 上传了文件：" + objectName + "  文件大小：" + size + " 文件类型：" + contentType);
        minioClient.putObject(bucketName, objectName, stream, size, contentType);
    }

    @Async
    public void putObjectImg(String objectName,
                             InputStream stream,
                             long size,
                             String contentType) throws Exception {
        MinioClient minioClient = new MinioClient(url, aKey, sKey);

        logger.info(" 上传了文件：" + objectName + "  文件大小：" + size + " 文件类型：" + contentType);
        minioClient.putObject(IMG, objectName, stream, size, contentType);
    }

    public InputStream getObject(String bucketName, String objectName) throws Exception {
        MinioClient minioClient = new MinioClient(url, aKey, sKey);

        logger.info(" 下传了文件：" + objectName);
        return minioClient.getObject(bucketName, objectName);
    }

    public String URl() {
        return url;
    }

    public void getObject(String bucketName, String objectName, String fileName) throws Exception {
        MinioClient minioClient = new MinioClient(url, aKey, sKey);

        minioClient.getObject(bucketName, objectName, fileName);
    }

    public void remove(String bucketName, String objectName){
        try {
            MinioClient minioClient = new MinioClient(url, aKey, sKey);
            minioClient.removeObject(bucketName, objectName);
            logger.info(" 删除文件：" + "/" + bucketName + objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
