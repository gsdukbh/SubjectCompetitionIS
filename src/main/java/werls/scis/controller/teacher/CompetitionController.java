package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.service.CompetitionServiceImpl;

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
@RequestMapping("/tea")
public class CompetitionController {
    @Autowired
    CompetitionServiceImpl competitionService;

    @PostMapping("/tea/competition/save")
    public String save(@RequestBody ScisCompetition competition) {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        if (competition != null) {
            res.put("code", 200);
            res.put("message", "ok");
            competitionService.save(competition);
            return JSON.toJSONString(res);
        } else {
            res.put("code", 404);
            res.put("message", "fail");
            return JSON.toJSONString(res);
        }
    }
    @CacheEvict(value = "CompetitionAll", key = "'id:'+#id")
    @PostMapping("/competition/deleteById/{id}")
    public Object deleteById(@PathVariable Integer id){
        Map<String, Object> res = new ConcurrentHashMap<>();
        res.put("code",200);
        res.put("message","Success");
        competitionService.deleteById(id);
        return JSON.toJSON(res);
    }
}
