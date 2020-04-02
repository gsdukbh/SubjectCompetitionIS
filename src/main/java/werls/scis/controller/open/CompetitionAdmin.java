package werls.scis.controller.open;


import com.alibaba.druid.sql.visitor.functions.Isnull;
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
import werls.scis.dao.pojo.ScisProblem;
import werls.scis.service.CompetitionServiceImpl;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月08日 0:27
 */
@RestController
@RequestMapping("/public/competition")
public class CompetitionAdmin {

    @Resource
    CompetitionServiceImpl competitionService;


    /**
     * 默认 开始时间降序排序
     *
     * @param page 分页
     * @param size 每页大小
     * @return Page
     */
    @RequestMapping("/findAll")
//    @Cacheable(value = "CompetitionAll", key = "'page:'+#page+'size:'+#size+'name:'+#name+'organizer:'+#organizer", unless = "#result == null ")
    public Page<ScisCompetition> findByAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "20") Integer size,
                                           @RequestParam(name = "name", defaultValue = "") String name,
                                           @RequestParam(name = "organizer", defaultValue = "") String organizer,
                                           @RequestParam(name = "level", defaultValue = "") String level) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime"));

        if (!"".equals(organizer) && !"".equals(name) && !"".equals(level)) {
            return competitionService.findByNameContainingAndLevelAndOrganizer(name,level,organizer,pageable);
        }else if (!"".equals(organizer) && !"".equals(name)) {
            return competitionService.findByNameContainingAndOrganizer(name,organizer, pageable);
        } else if (!"".equals(level) && !"".equals(name)) {
            return competitionService.findByNameContainingAndLevel(name,level, pageable);
        }else if(!"".equals(organizer) && !"".equals(level)){
            return competitionService.findByOrganizerAndLevel(organizer,level,pageable);
        }
        else if (!"".equals(name) || !"".equals(organizer) || !"".equals(level)) {
            return competitionService.findByNameContainingOrOrganizerOrLevel(name,organizer,level,pageable);
        } else {
            return competitionService.findAll(pageable);
        }
    }


    //    @Cacheable(value = "CompetitionAll", key = "'id:'+#id", unless = "#result == null ")
    @GetMapping("/findById/{id}")
    public Map<String, Object> findById(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        Optional<ScisCompetition> competition = competitionService.findById(id);
        if (competition.isPresent()) {
            ScisCompetition scisCompetition = competition.get();
            scisCompetition.setUser(null);
            scisCompetition.setApplyFromList(null);
            scisCompetition.setProblems(null);
            scisCompetition.setTeamApplyList(null);
            scisCompetition.setWorks(null);
            res.put("status", 200);
            res.put("message", "success");
            res.put("data", scisCompetition);
            return res;
        } else {
            res.put("status", 404);
            res.put("message", "fail");
            return res;
        }
    }

}