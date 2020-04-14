package werls.scis.controller.open;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.util.FileUploader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.open
 * @Description: TODO
 * @date Date : 2020年04月14日 15:32
 */
@RestController
@RequestMapping("/public/file")
public class GetFileController {
    @Resource
    FileUploader fileUploader;

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
