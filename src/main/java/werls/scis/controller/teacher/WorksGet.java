package werls.scis.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import werls.scis.dao.pojo.ScisWorks;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.CompetitionServiceImpl;
import werls.scis.service.WorksServiceImpl;
import werls.scis.util.FileUploader;
import werls.scis.util.ZipFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年05月05日 10:22
 */
@RestController
@RequestMapping("/tea/works")
public class WorksGet {
    @Autowired
    WorksServiceImpl worksService;
    @Autowired
    ApplyFromSericeImpl applyFromSerice;

    @Autowired
    CompetitionServiceImpl competitionService;

    @Autowired
    FileUploader fileUploader;
    @Autowired
    ZipFile zipFile;

    @GetMapping("/get/File/works/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id,
                                           HttpServletRequest request) throws Exception {
        List<ScisWorks> worksList = worksService.findByCompetitionId(id);
        String file = "/temp/works/";
        String zipName = "";
        String file1 = "/temp/works";
        File zipPath = new File(file1);
        if (!zipPath.exists()) {
            zipPath.mkdir();
        }
        for (ScisWorks works : worksList) {
            InputStream inputStream = fileUploader.getObject(works.getBucketName(), works.getObjectName());
            String filePath = file +
                    works.getApplyFrom().getScisUser().getName() +
                    works.getApplyFrom().getScisUser().getLogin() +
                    works.getObjectName();
            zipName = works.getCompetition().getName();
            OutputStream outputStream = new FileOutputStream(filePath);
            byte[] bytes = new byte[2048];
            int length;
            while ((length = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.close();
            inputStream.close();
        }
        zipName = "/temp/" + zipName + System.currentTimeMillis() + "作品.zip";
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                new FileOutputStream(zipName));
        zipFile.writeZip(zipPath, "/", zipOutputStream);
        zipOutputStream.close();
        zipFile.deleteFolder(new File(file));
        InputStream inputStream = new FileInputStream(zipName);
        byte[] bytes = new byte[2048];
        int length;
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        while ((length = inputStream.read(bytes)) > 0) {
            res.write(bytes, 0, length);
        }
        inputStream.close();
        res.close();
        File t = new File(zipName);
        t.delete();
        zipName = this.getFilename(request, zipName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", zipName);
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
