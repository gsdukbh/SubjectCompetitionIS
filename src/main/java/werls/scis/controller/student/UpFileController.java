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
import sun.misc.BASE64Encoder;
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

    @PostMapping("/img")
    public Map<String, Object> saveImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = new ConcurrentHashMap<>(5);
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


    @GetMapping("/getFile")
    public ResponseEntity<byte[]> download(
            HttpServletRequest request,
            @RequestParam("bucketName") String bucketName,
            @RequestParam("objectName") String objectName) throws Exception {
        InputStream inputStream = fileUploader.getObject(bucketName, objectName);
        byte[] bytes = new byte[2048];
        int length;
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        while ((length = inputStream.read(bytes)) > 0) {
            res.write(bytes, 0, length);
        }
        inputStream.close();

        res.close();

        objectName = this.getFilename(request, objectName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", objectName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(res.toByteArray(),
                headers, HttpStatus.OK);
    }

    public String getFilename(HttpServletRequest request,
                              String filename) throws Exception {
        // IE不同版本User-Agent中出现的关键词
        String[] iEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : iEBrowserKeyWords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示
                return URLEncoder.encode(filename, "UTF-8");
            }
        }

        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
}
