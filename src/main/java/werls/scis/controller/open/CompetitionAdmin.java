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
import werls.scis.util.Tools;

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
    @Autowired
    Tools tools;


    @RequestMapping("/findAll/s")
    public Page<ScisCompetition> findByAllS(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "20") Integer size,
                                            @RequestParam(name = "name", defaultValue = "") String name,
                                            @RequestParam(name = "organizer", defaultValue = "") String organizer,
                                            @RequestParam(name = "level", defaultValue = "") String level) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("applyTime").descending());
        String status = "草稿";
        if (!"".equals(organizer) && !"".equals(name) && !"".equals(level)) {

            return competitionService.findByNameContainingAndLevelAndOrganizerAndStatusNot(name, level, organizer, status, pageable);
        } else if (!"".equals(organizer) && !"".equals(name)) {

            return competitionService.findByNameContainingAndOrganizerAndStatusIsNot(name, organizer, status, pageable);
        } else if (!"".equals(level) && !"".equals(name)) {
            return competitionService.findByNameContainingAndLevelAndStatusIsNot(name, level, status, pageable);
        } else if (!"".equals(organizer) && !"".equals(level)) {

            return competitionService.findByOrganizerAndLevelAndStatusIsNot(organizer, level, status, pageable);
        } else if (!"".equals(name)) {
            return competitionService.findByNameContainingAndStatusIsNot(name, status, pageable);
        } else if (!"".equals(organizer)) {
            return competitionService.findByOrganizerAndStatusIsNot(organizer, status, pageable);
        } else if (!"".equals(level)) {
            return competitionService.findByLevelAndStatusIsNot(level, status, pageable);
        } else {
            return competitionService.findAllByStatusIsNot(status, pageable);
        }
    }

    @RequestMapping("/find")
    public Map<String, Object> find() {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        List<Map<String, Object>> result = new ArrayList<>();
        String status = "草稿";
        List<ScisCompetition> scisCompetitions = competitionService.findAllByStatusIsNot(status);
        for (ScisCompetition competition : scisCompetitions) {
            Map<String, Object> tem = new ConcurrentHashMap<>(2);
            tem.put("competitionId", competition.getId());
            tem.put("name", competition.getName());
            result.add(tem);
        }
        res.put("competition", result);
        res.put("status", 200);
        return res;
    }




    @GetMapping("/findById/{id}")
//    @Cacheable(value = "findById", key = "'id:'+#id", unless = "#result == null ")
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

    @GetMapping("/findName")
    public Map<String, Object> findName() {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        List<Map<String, Object>> ret = new ArrayList<>();
        List<String> strings = competitionService.findName();
        res.put("status", 200);
        return tools.getStringObjectMap(res, ret, strings);
    }


}
