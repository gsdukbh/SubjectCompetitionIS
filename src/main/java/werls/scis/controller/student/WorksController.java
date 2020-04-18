package werls.scis.controller.student;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCompetition;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipOutputStream;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.student
 * @Description: TODO
 * @date Date : 2020年04月17日 19:19
 */
@RestController
@RequestMapping("/student/works")
public class WorksController {
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
            String filePath = file + works.getObjectName();
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

    @GetMapping("/")
    public Map<String, Object> findByCompetitionId() {
        Map<String, Object> res = new HashMap<>(16);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("score").descending());
        Page<ScisWorks> scisWorksPage = worksService.findByCompetitionId(2, pageable);

        return res;
    }

    @PostMapping("/findMyWorks/{id}")
    public Map<String, Object> findMyWorks(@PathVariable Integer id,
                                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "10") Integer size,
                                           @RequestParam(name = "worksName", defaultValue = "") String worksName,
                                           @RequestParam(name = "competitionId", defaultValue = "") Integer competitionId) {
        Map<String, Object> res = new HashMap<>(16);
        res.put("content", worksService.findUserIdWorksNameOrCompetition(id, worksName, competitionId, page, size));
        res.put("totalElements", worksService.findUserIdWorksNameOrCompetition(id, worksName, competitionId));
        res.put("status", 200);
        return res;
    }


    @GetMapping("/findById/{id}")
    public Map<String, Object> findById(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisWorks> scisWorks = worksService.findById(id);
        res.put("status", 200);
        scisWorks.ifPresent(scisWork -> res.put("data", scisWork));
        return res;
    }

    @PostMapping("/findAll")
    public Map<String, Object> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "10") Integer size,
                                       @RequestParam(name = "competitionId", defaultValue = "") Integer id,
                                       @RequestParam(name = "worksName", defaultValue = "") String worksName,
                                       @RequestParam(name = "userName", defaultValue = "") String userName) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("score").descending());
        res.put("status", 200);
        if (id != null && !"".equals(worksName) && !"".equals(userName)) {
            Page<ScisWorks> tem = worksService.findByNameContainingAndAuthorContainingAndCompetitionId(worksName, userName, id, pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        } else if (id != null && !"".equals(worksName)) {
            Page<ScisWorks> tem = worksService.findByNameContainingAndCompetitionId(worksName, id, pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        } else if (!"".equals(worksName) && !"".equals(userName)) {
            Page<ScisWorks> tem = worksService.findByNameContainingAndAuthorContaining(worksName, userName, pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        } else if (id != null && !"".equals(userName)) {
            Page<ScisWorks> tem = worksService.findByAuthorContainingAndCompetitionId(userName, id, pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        } else if (id != null) {
            Page<ScisWorks> tem = worksService.findByCompetitionId(id, pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        } else if (!"".equals(worksName)) {
            Page<ScisWorks> tem = worksService.findByNameLike(worksName, pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        } else if (!"".equals(userName)) {
            Page<ScisWorks> tem = worksService.findByAuthorLike(userName, pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        } else {
            Page<ScisWorks> tem = worksService.findAll(pageable);
            res.put("content", tem.getContent());
            res.put("totalElements", tem.getTotalElements());
        }

        return res;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject works) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisWorks scisWorks = JSONObject.toJavaObject(works, ScisWorks.class);
        ScisCompetition scisCompetition = new ScisCompetition();
        scisCompetition.setId(works.getInteger("competitionId"));
        scisWorks.setCompetition(scisCompetition);
        Integer integer = (Integer) worksService.find(works.getInteger("userId"), scisCompetition.getId()).get("worksId");
        if (integer == null) {
            res.put("status", 200);
            scisWorks = worksService.save(scisWorks);
            res.put("result", scisWorks);
            applyFromSerice.update(works.getInteger("userId"), scisCompetition.getId(), scisWorks.getId());
        } else {
            res.put("message", "你已经提交该比赛的作品");
            res.put("status", 403);
        }
        return res;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody JSONObject works) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        ScisWorks scisWorks = JSONObject.toJavaObject(works, ScisWorks.class);
        Optional<ScisWorks> works1 = worksService.findById(scisWorks.getId());
//        works1.ifPresent(value -> fileUploader.remove(value.getBucketName(), value.getObjectName()));
        ScisCompetition scisCompetition = new ScisCompetition();
        if (works1.isPresent()) {
            scisCompetition = works1.get().getCompetition();
            if (!scisWorks.getObjectName().equals(works1.get().getObjectName())) {
                fileUploader.remove(works1.get().getBucketName(), works1.get().getObjectName());
            }
        }
        res.put("result", worksService.save(scisWorks));
        applyFromSerice.update(works.getInteger("userId"), scisCompetition.getId(), scisWorks.getId());
        res.put("status", 200);
        return res;
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
