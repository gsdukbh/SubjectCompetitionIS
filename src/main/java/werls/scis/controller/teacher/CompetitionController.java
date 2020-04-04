package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.service.CompetitionServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年03月15日 13:43
 */
@RestController
@RequestMapping("/tea/competition")
public class CompetitionController {
    @Autowired
    CompetitionServiceImpl competitionService;

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject json) {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        if (json != null) {
            res.put("status", 200);
            res.put("message", "ok");
            ScisCompetition competition1=JSONObject.toJavaObject(json,ScisCompetition.class) ;
//            competition1.setId(json.getInteger("id"));
//            competition1.setName(json.getString("name"));
//            competition1.setStartTime(json.getDate("startTime"));
//            competition1.setEndTime(json.getDate("endTime"));
//            competition1.setApplyTime(json.getDate("applyTime"));
//            competition1.setContent(json.getString("content"));
//            competition1.setAuthor(json.getString("author"));
//            competition1.setLevel(json.getString("level"));
//            competition1.setOrganizer(json.getString("organizer"));
//            competition1.setNumLimit(json.getString("numLimit"));
//            competition1.setPlace(json.getString("place"));
//            competition1.setType(json.getString("type"));
//            competition1.setTeam(json.getBoolean("team"));
//            competition1.setNotification(json.getBoolean("notification"));

            competitionService.save(competition1);
            return res;
        } else {
            res.put("status", 404);
            res.put("message", "fail");
            return res;
        }
    }

    @CacheEvict(value = "findById", key = "'id:'+#id")
    @PostMapping("/deleteById/{id}")
    public Map<String, Object> deleteById(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>();
        res.put("status", 200);
        res.put("message", "Success");
        competitionService.deleteById(id);
        return res;
    }
    @PostMapping("/findMyResponsible/{id}")
    public Map<String, Object> findMyResponsible(@PathVariable Integer id,
                                                 @RequestParam(name = "name", defaultValue = "") String name,
                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", defaultValue = "20") Integer size){
        Map<String, Object> res = new ConcurrentHashMap<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());
        Page<ScisCompetition> tem=competitionService.findByNameContainingAndUserId(name,id,pageable);
        res.put("content",tem.getContent());
        res.put("totalElements",tem.getTotalElements());
        res.put("status", 200);
        res.put("message", "Success");
        return res;
    }

}
