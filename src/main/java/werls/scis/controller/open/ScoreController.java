package werls.scis.controller.open;


import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
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
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.ApplyFromSericeImpl;
import werls.scis.service.UserService;
import werls.scis.service.WorksServiceImpl;
import werls.scis.util.ExcelToScore;
import werls.scis.util.Score;
import werls.scis.util.Tools;
import werls.scis.webSocket.WebSocket;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.open
 * @Description: TODO
 * @date Date : 2020年04月19日 15:19
 */
@RestController
@RequestMapping("/public/score")
public class ScoreController {

    @Autowired
    ApplyFromSericeImpl applyFromSerice;
    @Autowired
    UserService userService;
    @Autowired
    WebSocket webSocket;
    @Autowired
    Tools tools;


    @PostMapping("/findMy/{id}")
    public Map<String, Object> findMy(@PathVariable Integer id,
                                      @RequestParam(name = "competitionId", defaultValue = "0") Integer competitionId,
                                      @RequestParam(name = "left", defaultValue = "0") Integer left,
                                      @RequestParam(name = "right", defaultValue = "0") Integer right,
                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                      @RequestParam(name = "size", defaultValue = "100") Integer size) {
        Map<String, Object> res = new HashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("score").descending());
        if (right != 0 && competitionId != 0) {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findByScisUserIdAndCompetitionIdAndScoreBetweenAndScoreNotNull(id, competitionId, left, right, pageable);
            res.put("content", tools.addRank(scisApplyFroms.getContent()));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        } else if (right != 0) {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findByScoreBetweenAndScisUserIdAndScoreNotNull(left, right, id, pageable);
            res.put("content", tools.addRank(scisApplyFroms.getContent()));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        } else if (competitionId != 0) {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findAllByCompetitionIdAndScisUserIdAndScoreNotNull(competitionId, id, pageable);
            res.put("content", tools.addRank(scisApplyFroms.getContent()));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        } else {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findByScisUserIdAndScoreNotNull(id, pageable);
            res.put("content", tools.addRank(scisApplyFroms.getContent()));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        }
        return res;
    }


    @PostMapping("/findScore")
    public Map<String, Object> findScore(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "100") Integer size,
                                         @RequestParam(name = "userName", defaultValue = "") String userName,
                                         @RequestParam(name = "competitionId", defaultValue = "") Integer competitionId) {
        Map<String, Object> res = new HashMap<>(16);
        res.put("status", 200);
        res.put("content", applyFromSerice.findGrades(competitionId, userName, page, size));
        res.put("totalElements", applyFromSerice.findGrades(userName, competitionId).get("totalElements"));
        return res;
    }

    @Cacheable(value = "Score", unless = "#result == null ", key = "'competition:'+#id")
    @PostMapping("/findAll/{id}")
    public Map<String, Object> findAll(@PathVariable Integer id,
                                       @RequestParam(name = "value", defaultValue = "") String name,
                                       @RequestParam(name = "left", defaultValue = "0") Integer left,
                                       @RequestParam(name = "right", defaultValue = "0") Integer right,
                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("score").descending());
        if (right != 0 && !"".equals(name)) {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findByScisUserNameContainingOrScisUserLoginContainingAndScoreBetween(
                    name, name, left, right, pageable);
            res.put("content", tools.addRank(scisApplyFroms.getContent()));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        } else if (right != 0) {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findByScoreBetween(left, right, pageable);
            res.put("content", tools.addRank(scisApplyFroms.getContent()));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        } else if (!"".equals(name)) {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findByScisUserNameContainingOrScisUserLoginContaining(name, name, pageable);
            res.put("content", tools.addRank(scisApplyFroms.getContent()));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        } else {
            Page<ScisApplyFrom> scisApplyFroms = applyFromSerice.findAllByCompetitionId(id, pageable);
            List<ScisApplyFrom> scisApplyFromList = new ArrayList<>();
            for (ScisApplyFrom scisApplyFrom : scisApplyFroms.getContent()) {
                Integer userId = (Integer) applyFromSerice.findUserIdByApplyId(scisApplyFrom.getId()).get("userId");
                Optional<ScisUser> optional = userService.findById(userId);
                optional.ifPresent(scisApplyFrom::setScisUser);
                scisApplyFromList.add(scisApplyFrom);
            }
            res.put("content", tools.addRank(scisApplyFromList));
            res.put("totalElements", scisApplyFroms.getTotalElements());
            res.put("status", 200);
        }
        return res;
    }
}
