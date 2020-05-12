package werls.scis.util;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String A_KEY = "1361404576";

    private static final String S_KEY = "lijiawei+520";

    //    public static final String URL="https://data.werls.top";
    public static final String URL = "http://127.0.0.1:9000";

    //    public static final String ENDPOINT="http://120.78.148.61:9000";
    public static final String ENDPOINT = "http://127.0.0.1:9000";

    private static final String IMG = "img";
    MinioClient minioClient = new MinioClient(ENDPOINT, A_KEY, S_KEY);

    public FileUploader() throws Exception {
        super();
    }

    public void makeBucket(String bucketName) throws Exception {
        logger.info("创建了存储对象：" + bucketName);
        minioClient.makeBucket(bucketName);
    }

    @Async
    public void putObject(String bucketName,
                          String objectName,
                          InputStream stream,
                          long size,
                          String contentType) throws Exception {
        logger.info(" 上传了文件：" + objectName + "  文件大小：" + size + " 文件类型：" + contentType);
        minioClient.putObject(bucketName, objectName, stream, size, contentType);
    }

    @Async
    public void putObjectImg(String objectName,
                             InputStream stream,
                             long size,
                             String contentType) throws Exception {
        logger.info(" 上传了文件：" + objectName + "  文件大小：" + size + " 文件类型：" + contentType);
        minioClient.putObject(IMG, objectName, stream, size, contentType);
    }

    public InputStream getObject(String bucketName, String objectName) throws Exception {
        logger.info(" 下传了文件：" + objectName);
        return minioClient.getObject(bucketName, objectName);
    }

    public String URl() {
        return URL;
    }

    public void getObject(String bucketName, String objectName, String fileName) throws Exception {
        minioClient.getObject(bucketName, objectName, fileName);
    }

    public void remove(String bucketName, String objectName) {
        try {
            minioClient.removeObject(bucketName, objectName);
            logger.info(" 删除文件：" + "/" + bucketName + objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
