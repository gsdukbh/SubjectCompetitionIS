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

    @PostMapping("/deleteAll")
    public Map<String, Object> deleteAll(@RequestBody List<ScisCompetition> data) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("message", "ok");
        competitionService.deleteAll(data);
        return res;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject json) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        if (json != null) {
            res.put("status", 200);
            res.put("message", "ok");
            ScisCompetition competition1 = JSONObject.toJavaObject(json, ScisCompetition.class);
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
                                                 @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Map<String, Object> res = new ConcurrentHashMap<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by("endTime").descending());
        Page<ScisCompetition> tem = competitionService.findByNameContainingAndUserId(name, id, pageable);
        res.put("content", tem.getContent());
        res.put("totalElements", tem.getTotalElements());
        res.put("status", 200);
        res.put("message", "Success");
        return res;
    }

    @RequestMapping("/findAll")
    public Page<ScisCompetition> findByAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "20") Integer size,
                                           @RequestParam(name = "name", defaultValue = "") String name,
                                           @RequestParam(name = "organizer", defaultValue = "") String organizer,
                                           @RequestParam(name = "level", defaultValue = "") String level) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("applyTime").descending());
        return getScisCompetitions(name, organizer, level, pageable);
    }

    private Page<ScisCompetition> getScisCompetitions(@RequestParam(name = "name", defaultValue = "") String name, @RequestParam(name = "organizer", defaultValue = "") String organizer, @RequestParam(name = "level", defaultValue = "") String level, Pageable pageable) {
        if (!"".equals(organizer) && !"".equals(name) && !"".equals(level)) {

            return competitionService.findByNameContainingAndLevelAndOrganizer(name, level, organizer, pageable);
        } else if (!"".equals(organizer) && !"".equals(name)) {

            return competitionService.findByNameContainingAndOrganizer(name, organizer, pageable);
        } else if (!"".equals(level) && !"".equals(name)) {

            return competitionService.findByNameContainingAndLevel(name, level, pageable);
        } else if (!"".equals(organizer) && !"".equals(level)) {

            return competitionService.findByOrganizerAndLevel(organizer, level, pageable);
        } else if (!"".equals(name)) {
            return competitionService.findByNameLike(name, pageable);
        } else if (!"".equals(organizer)) {
            return competitionService.findByOrganizer(organizer, pageable);
        } else if (!"".equals(level)) {
            return competitionService.findByLevel(level, pageable);
        } else {
            return competitionService.findAll(pageable);
        }
    }
}
