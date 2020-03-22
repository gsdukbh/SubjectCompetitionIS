package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
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

    @PostMapping("/competition/save")
    public  Map<String, Object> save( @RequestParam ScisCompetition competition) {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        if (competition != null) {
            res.put("status", 200);
            res.put("message", "ok");
            System.out.println(competition.toString());
//            competitionService.save(competition);
            return res;
        } else {
            res.put("status", 404);
            res.put("message", "fail");
            return res;
        }
    }
    @CacheEvict(value = "CompetitionAll", key = "'id:'+#id")
    @PostMapping("/competition/deleteById/{id}")
    public Map<String, Object> deleteById(@PathVariable Integer id){
        Map<String, Object> res = new ConcurrentHashMap<>();
        res.put("status",200);
        res.put("message","Success");
        competitionService.deleteById(id);
        return res;
    }
}
