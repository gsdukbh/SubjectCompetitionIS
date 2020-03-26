package werls.scis.controller.student;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import werls.scis.util.FileUploader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年03月23日 22:09
 */
@RestController
@RequestMapping("/i/upFile")
public class UpFileController {

    @Resource
    FileUploader fileUploader;

    @PostMapping("/img")
    public Map<String, Object> saveImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new ConcurrentHashMap<>(5);
        String regex = "^image.*";
        if (!file.isEmpty() && Objects.requireNonNull(file.getContentType()).matches(regex)) {
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + file.getOriginalFilename();

            res.put("status", 200);
            res.put("fileSize", file.getSize());
            res.put("fileName", file.getOriginalFilename());
            res.put("contentType", file.getContentType());
            try {
                fileUploader.putObjectImg(
                        fileName,
                        file.getInputStream(),
                        file.getSize(),
                        file.getContentType());
                res.put("message", "success");
                res.put("markdown", "![" + file.getOriginalFilename() + "](" + fileUploader.URl() + "/img/" + fileName + ")");
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                res.put("message", "fail");
            }
            return res;
        } else {
            res.put("status", 403);
        }
        return res;
    }

    @GetMapping("/getObject")
    public Map<String, Object> getObject(@RequestParam("bucketName") String bucketName,
                                         @RequestParam("objectName") String objectName) {
        Map<String, Object> res = new ConcurrentHashMap<>(5);
        try {
            String string = null;
            InputStream inputStream = fileUploader.getObject(bucketName, objectName);
            byte[] bytes = new byte[2048];

            while (inputStream.read(bytes) > 0) {
                string = new String(bytes);
            }
            inputStream.close();
            BASE64Encoder encoder = new BASE64Encoder();
            res.put("status", 200);
            res.put("date", encoder.encode(string.getBytes()));
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", 403);
            return res;
        }
    }

    @GetMapping("/getFile")
    public ResponseEntity<byte[]> download(
            @RequestParam("bucketName") String bucketName,
            @RequestParam("objectName") String objectName) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", objectName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String string = null;
        byte[] bytes = new byte[2048];
        int i = 0;
        List<Byte> res;
        InputStream inputStream = fileUploader.getObject(bucketName, objectName);
        while (inputStream.read(bytes) > 0) {
            string = new String(bytes);
            System.out.println(i++);
        }
        System.out.println(string.length());
        inputStream.close();
//        return new ResponseEntity<byte[]>(string.getBytes(),
//                headers, HttpStatus.OK);
        return null;
    }
}
