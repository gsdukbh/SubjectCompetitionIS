package werls.scis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisCompetition;
import werls.scis.service.CompetitionServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月08日 0:27
 */
@RestController
@RequestMapping("/tea")
public class CompetitionAdmin {

    @Autowired
    CompetitionServiceImpl competitionService;

    /**
     * 测试json 接收
     * @param competition json
     * @return json
     */
    @PostMapping("/competition/save")
    public String save(@RequestBody JSONObject competition) {
        Map<String, Object> res = new HashMap<>();
        if (competition != null) {
            ScisCompetition scisCompetition = new ScisCompetition();
            scisCompetition.setId(competition.getInteger("id"));
            scisCompetition.setName(competition.getString("name"));
            scisCompetition.setAuthor(competition.getString("author"));
            scisCompetition.setStatus(competition.getString("status"));
            scisCompetition.setStartTime(competition.getSqlDate("startTime"));
            scisCompetition.setEndTime(competition.getSqlDate("endTime"));
            scisCompetition.setLevel(competition.getString("region"));
            scisCompetition.setOrganizer(competition.getString("organizer"));
            scisCompetition.setContent(competition.getString("content"));
            scisCompetition.setNumLimit(competition.getString("type"));
            res.put("code", 200);
            res.put("message", "ok");
            competitionService.save(scisCompetition);
            return JSON.toJSONString(res);
        }else {
            res.put("code",404 );
            res.put("message", "fail");
            return JSON.toJSONString(res);
        }
    }

    /**
     * 默认 开始时间降序排序
     * @param page 分页
     * @param size 每页大小
     * @return Page
     */
    @GetMapping("/competition/findAll")
    @Cacheable(value = "CompetitionAll",key = "#page+#size",unless = "#result == null ")
     public Page<ScisCompetition> findByAll(@RequestParam(name = "page",defaultValue = "0") Integer page,
                                           @RequestParam(name ="size",defaultValue = "30" )Integer size){
        Pageable pageable1= PageRequest.of(page, size, Sort.by("startTime").descending());
        competitionService.findAll(pageable1);
        return  competitionService.findAll(pageable1);

    }

}
