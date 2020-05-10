package werls.scis.controller.teacher;

import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import werls.scis.dao.pojo.ScisApplyFrom;
import werls.scis.dao.pojo.ScisCollege;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.dao.pojo.ScisWorks;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.CollegeServiceImpl;
import werls.scis.service.UserService;
import werls.scis.util.*;
import werls.scis.webSocket.WebSocket;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年04月21日 15:32
 */
@RestController
@RequestMapping("/tea/score")
public class ScoreAdminController {
    @Autowired
    ApplyFromSericeImpl applyFromSerice;
    @Autowired
    CollegeServiceImpl collegeService;
    @Autowired
    WebSocket webSocket;
    @Autowired
    Tools tools;

    @Autowired
    UserService userService;

    @CachePut(value = "Score", unless = "#result == null ", key = "'competitionId:'+#id")
    @PostMapping("/modifyInfo/{id}")
    public Map<String, Object> modifyInfo(@RequestBody ScisApplyFrom applyFrom, Integer id) {
        Map<String, Object> res = new HashMap<>(16);
        ScisWorks works = applyFrom.getWorks();
        works.setScore(applyFrom.getScore());
        applyFrom.setWorks(works);
        applyFromSerice.save(applyFrom);
        res.put("status", 200);
        return res;
    }

    @PostMapping("/delInfoAll}")
    public Map<String, Object> delInfoAll(@RequestBody List<ScisApplyFrom> applyFroms) {
        Map<String, Object> res = new HashMap<>(16);
        res.put("status", 200);
        for (ScisApplyFrom apply : applyFroms) {
            apply.setScore(0);
            apply.setGradesanking(null);
            applyFromSerice.save(apply);
        }
        return res;
    }

    @PostMapping("/delInfo}")
    public Map<String, Object> delInfo(ScisApplyFrom applyFrom) {
        Map<String, Object> res = new HashMap<>(16);
        applyFrom.setScore(0);
        applyFrom.setGradesanking(null);
        applyFromSerice.save(applyFrom);
        res.put("status", 200);
        return res;
    }

    @Cacheable(value = "Score", unless = "#result == null ", key = "'competitionId:'+#competitionId")
    @GetMapping("/getScore/analysis/{competitionId}")
    public Map<String, Object> getScoreInfo(@PathVariable Integer competitionId) {
        Map<String, Object> res = new HashMap<>(16);
        List<ScisCollege> scisColleges = collegeService.findAll();
        List<Map<String, Object>> collegeData = new ArrayList<>(16);
        List<String> collegeName = new ArrayList<>(16);
        for (ScisCollege college : scisColleges) {
            Map<String, Object> tem = new HashMap<>(16);
            List<Integer> data = new ArrayList<>(16);
            collegeName.add(college.getName());
            tem.put("name", college.getName());
            data.add(applyFromSerice.gradeDistribution(competitionId, 0, 59, college.getName()));
            data.add(applyFromSerice.gradeDistribution(competitionId, 60, 75, college.getName()));
            data.add(applyFromSerice.gradeDistribution(competitionId, 76, 85, college.getName()));
            data.add(applyFromSerice.gradeDistribution(competitionId, 86, 95, college.getName()));
            data.add(applyFromSerice.gradeDistribution(competitionId, 96, 100, college.getName()));
            tem.put("data", data);
            tem.put("type", "bar");
            tem.put("coordinateSystem", "polar");
            tem.put("stack", "a");
            collegeData.add(tem);
        }
        res.put("collegeName", collegeName);
        res.put("collegeData", collegeData);
        res.put("scoreData", tools.getScoreLevel(competitionId));
        res.put("status", 200);
        return res;
    }


    @GetMapping("/getModel/{competitionId}")
    public ResponseEntity<byte[]> getModel(HttpServletRequest request, @PathVariable Integer competitionId) {
        Score score = new Score();
        score.setCompetitionId(competitionId);
        List<Score> scoreList = new ArrayList<>();
        List<ScisApplyFrom> applyFroms = applyFromSerice.findAllByCompetitionId(competitionId);
        for (ScisApplyFrom apply : applyFroms) {
            score.setCompetitionId(competitionId);
            score.setCompetitionName(apply.getCompetition() != null ? apply.getCompetition().getName() : null);
//            score.setName(apply.getScisUser() != null ? apply.getScisUser().getName() : null);
//            score.setLogin(apply.getScisUser() != null ? apply.getScisUser().getLogin() : null);
            scoreList.add(score);
        }
        String fileName = "/temp/成绩提交模板-" + System.currentTimeMillis() + ".xlsx";
        ResponseEntity<byte[]> responseEntity = null;
        try {
            EasyExcel.write(fileName, Score.class).sheet("模板").doWrite(scoreList);
            File file = new File(fileName);
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[2048];
            int length;
            ByteArrayOutputStream res = new ByteArrayOutputStream();
            while ((length = inputStream.read(bytes)) > 0) {
                res.write(bytes, 0, length);
            }
            inputStream.close();
            res.close();
            file.delete();
            fileName = tools.getFilename(request, fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity<>(res.toByteArray(),
                    headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/getScoreInfo/{competitionId}")
    public ResponseEntity<byte[]> getScoreInfo(HttpServletRequest request, @PathVariable Integer competitionId) {
        List<DateScoreToExcel> excealList = new ArrayList<>();
        String fileName = null;
        List<Map<String, Object>> tem = applyFromSerice.findCompetitionId(competitionId);
        for (Map<String, Object> map : tem) {
            DateScoreToExcel dateScoreToExcel = new DateScoreToExcel();
            fileName = (String) map.get("competitionName");
            dateScoreToExcel.setCompetitionId((Integer) map.get("competitionId"));
            dateScoreToExcel.setCompetitionName((String) map.get("competitionName"));
            dateScoreToExcel.setGrades((String) map.get("grades"));
            dateScoreToExcel.setScore((Integer) map.get("score"));
            if (dateScoreToExcel.getScore() != null) {
                Integer userId = (Integer) map.get("userId");
                Optional<ScisUser> userOptional = userService.findById(userId);
                if (userOptional.isPresent()) {
                    ScisUser user = userOptional.get();
                    dateScoreToExcel.setName(user.getName());
                    dateScoreToExcel.setLogin(user.getLogin());
                    dateScoreToExcel.setClassName(user.getScisClass().getName());
                    dateScoreToExcel.setEmail(user.getEmail());
                    dateScoreToExcel.setIdentity(user.getIdentity());
                    dateScoreToExcel.setCollegeName(user.getScisClass().getMajor().getCollege().getName());
                    dateScoreToExcel.setPhone(user.getPhone());
                    dateScoreToExcel.setSex(user.getSex());
                    dateScoreToExcel.setPhone(user.getPhone());
                    dateScoreToExcel.setMajorName(user.getScisClass().getMajor().getName());
                }
                excealList.add(dateScoreToExcel);
            }

        }
        fileName = "/temp/" + fileName + "-成绩-" + System.currentTimeMillis() + ".xlsx";
        ResponseEntity<byte[]> responseEntity = null;
        try {
            EasyExcel.write(fileName, DateScoreToExcel.class).sheet("模板").doWrite(excealList);
            File file = new File(fileName);
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[2048];
            int length;
            ByteArrayOutputStream res = new ByteArrayOutputStream();
            while ((length = inputStream.read(bytes)) > 0) {
                res.write(bytes, 0, length);
            }
            inputStream.close();
            res.close();
            file.delete();
            fileName = tools.getFilename(request, fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity<>(res.toByteArray(),
                    headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseEntity;
    }

    @CachePut(value = "Score", unless = "#result == null ", key = "'competitionId:'+#competitionId")
    @PostMapping("/upScoreInfo")
    public Map<String, Object> upScoreInfo(@RequestParam("file") MultipartFile file,
                                           @RequestParam("id") Integer id,
                                           @RequestParam("competitionId") Integer competitionId) {
        Map<String, Object> res = new HashMap<>(16);
        String fileName = "/temp/" + file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            File file1 = new File(fileName);
            OutputStream outputStream = new FileOutputStream(file1);
            byte[] bytes = new byte[2048];
            int length;
            while ((length = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.close();
            inputStream.close();
            EasyExcel.read(fileName, Score.class, new ExcelToScore(id, competitionId, webSocket, tools)).sheet().doRead();
            file1.delete();
            res.put("status", 200);
        } catch (Exception e) {
            res.put("status", 403);
            e.printStackTrace();
        }
        return res;
    }
}
