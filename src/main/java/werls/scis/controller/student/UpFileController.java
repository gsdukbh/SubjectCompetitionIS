package werls.scis.controller.student;


import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import werls.scis.util.FileUploader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @PostMapping("/works")
    public Map<String, Object> saveWorks(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + file.getOriginalFilename();
        try {
            fileUploader.putObject(
                    "works",
                    fileName,
                    file.getInputStream(),
                    file.getSize(),
                    file.getContentType()
            );
            res.put("fileSize", file.getSize());
            res.put("status", 200);
            res.put("bucketName", "works");
            res.put("objectName", fileName);
        } catch (Exception e) {
            res.put("status", 403);
            e.printStackTrace();
        }
        return res;
    }

    @PostMapping("/annex")
    public Map<String, Object> saveAnnex(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + file.getOriginalFilename();
        try {
            fileUploader.putObject(
                    "annex",
                    fileName,
                    file.getInputStream(),
                    file.getSize(),
                    file.getContentType()
            );
            res.put("fileSize", file.getSize());
            res.put("status", 200);
            res.put("bucketName", "annex");
            res.put("objectName", fileName);

        } catch (Exception e) {
            res.put("status", 403);
            e.printStackTrace();
        }
        return res;
    }


    @PostMapping("/img")
    public Map<String, Object> saveImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        String regex = "^image.*";
        if (!file.isEmpty() && Objects.requireNonNull(file.getContentType()).matches(regex)) {
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + file.getOriginalFilename();
            try {
                fileUploader.putObjectImg(
                        fileName,
                        file.getInputStream(),
                        file.getSize(),
                        file.getContentType());
                res.put("status", 200);
                res.put("fileSize", file.getSize());
                res.put("fileName", file.getOriginalFilename());
                res.put("contentType", file.getContentType());
                res.put("message", "success");
                res.put("markdown", "![" + file.getOriginalFilename() + "](" + fileUploader.URl() + "/img/" + fileName + ")");
                res.put("img", fileUploader.URl() + "/img/" + fileName);
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                res.put("status", 403);
                res.put("message", "服务器错误");
            }
            return res;
        } else {
            res.put("status", 403);
            res.put("message", "错误的文件类型：" + file.getContentType());
        }
        return res;
    }

}
