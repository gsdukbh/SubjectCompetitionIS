package werls.scis.controller.teacher;

import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.CompetitionServiceImpl;
import werls.scis.service.TeamServiceImpl;
import werls.scis.service.UserService;
import werls.scis.util.ApplyFromToExceal;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年04月16日 22:31
 */
@RestController
@RequestMapping("/tea/apply")
public class ApplyAdminController {

    @Autowired
    ApplyFromSericeImpl applyFromSerice;
    @Autowired
    TeamServiceImpl teamService;

    @Autowired
    UserService userService;
    @Autowired
    CompetitionServiceImpl competitionService;

    @PostMapping("/personal/{id}")
    public Map<String, Object> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size,
                                       @RequestParam(name = "name", defaultValue = "") String name,
                                       @PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<Map<String, Object>> tem = applyFromSerice.findCompetitionId(id, name, page, size);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> map : tem) {
            Integer userId = (Integer) map.get("userId");
            Optional<ScisUser> userOptional = userService.findById(userId);
            Map<String, Object> temInfo = new HashMap<>(16);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                temInfo.put(entry.getKey(), entry.getValue());
            }
            userOptional.ifPresent(scisUser -> temInfo.put("userInfo", scisUser));
            result.add(temInfo);
        }
        res.put("status", 200);
        res.put("totalElements", result.size());
        res.put("content", result);
        return res;
    }

    @GetMapping("/downloadApply/{id}")
    public ResponseEntity<byte[]> downloadApply(
            HttpServletRequest request
            , @PathVariable Integer id) {
        List<ApplyFromToExceal> excealList = new ArrayList<>();
        List<Map<String, Object>> tem = applyFromSerice.findCompetitionId(id);
        String fileName = null;

        for (Map<String, Object> map : tem) {
            ApplyFromToExceal apply = new ApplyFromToExceal();
            fileName = (String) map.get("competitionName");
            apply.setCompetitionId((Integer) map.get("competitionId"));
            apply.setCompetitionName((String) map.get("competitionName"));
            apply.setApplyTime((Date) map.get("applyTime"));
            Integer userId = (Integer) map.get("userId");
            Optional<ScisUser> userOptional = userService.findById(userId);
            if (userOptional.isPresent()) {
                ScisUser user = userOptional.get();
                apply.setName(user.getName());
                apply.setLogin(user.getLogin());
                apply.setClassName(user.getScisClass().getName());
                apply.setEmail(user.getEmail());
                apply.setIdentity(user.getIdentity());
                apply.setCollegeName(user.getScisClass().getMajor().getCollege().getName());
                apply.setPhone(user.getPhone());
                apply.setSex(user.getSex());
                apply.setPhone(user.getPhone());
                apply.setMajorName(user.getScisClass().getMajor().getName());
            }
            excealList.add(apply);
        }
        /*写入excel*/
        fileName = "/" + fileName + "-报名信息表-" + System.currentTimeMillis() + ".xlsx";
        ResponseEntity<byte[]> responseEntity = null;
        try {
            EasyExcel.write(fileName, ApplyFromToExceal.class).sheet("数据")
                    .doWrite(excealList);
            InputStream inputStream = new FileInputStream(fileName);
            byte[] bytes = new byte[2048];
            int length;
            ByteArrayOutputStream res = new ByteArrayOutputStream();
            while ((length = inputStream.read(bytes)) > 0) {
                res.write(bytes, 0, length);
            }
            inputStream.close();
            res.close();
            File file = new File(fileName);
            file.delete();
            fileName = this.getFilename(request, fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity<byte[]>(res.toByteArray(),
                    headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", fileName);
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public Map<String, Object> te(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        List<Map<String, Object>> tem = applyFromSerice.findCompetitionId(id);
        res.put("data", applyFromSerice.findCompetitionId(id));
        return res;
    }

    public String getFilename(HttpServletRequest request,
                              String filename) throws Exception {
        // IE不同版本User-Agent中出现的关键词
        String[] iebrowserkeywords = {"MSIE", "Trident", "Edge"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : iebrowserkeywords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示
                return URLEncoder.encode(filename, "UTF-8");
            }
        }

        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
}
