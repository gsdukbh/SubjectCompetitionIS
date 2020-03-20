package werls.scis.controller;

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
import werls.scis.service.CompetitionServiceImpl;

import java.util.HashMap;
import java.util.Map;
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

    @Autowired
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
                                           @RequestParam(name = "name",defaultValue = "") String name,
                                           @RequestParam(name = "organizer",defaultValue = "") String organizer) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime"));
        if ( !"".equals(organizer) && !"".equals(name)){

            return competitionService.findByNameLikeAndOrganizer(name,organizer,pageable);
        }else if(!"".equals(name)){

            return competitionService.findByNameLike(name,pageable);
        }else if (!"".equals(organizer)){

            return competitionService.findByOrganizer(organizer,pageable);
        }else {

            return competitionService.findAll(pageable);
        }
    }


    @Cacheable(value = "CompetitionAll", key = "'id:'+#id", unless = "#result == null ")
    @GetMapping("/findById/{id}")
    public ScisCompetition findById(@PathVariable Integer id){

        if (competitionService.findById(id).isPresent()){
            return  competitionService.findById(id).get();
        }else {

            return  null;
        }
    }
}
