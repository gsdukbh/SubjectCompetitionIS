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
@RequestMapping("/public")
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
    @RequestMapping("/competition/findAll")
    @Cacheable(value = "CompetitionAll", key = "'page:'+#page+'size:'+#size", unless = "#result == null ")
    public Page<ScisCompetition> findByAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable1 = PageRequest.of(page, size, Sort.by("startTime"));
        return competitionService.findAll(pageable1);
    }
    @Cacheable(value = "CompetitionAll", key = "'id:'+#id", unless = "#result == null ")
    @GetMapping("/competition/findById/{id}")
    public Object findById(@PathVariable Integer id){
        Map<String, Object> res = new HashMap<>(10);
        if (competitionService.findById(id).isPresent()){
            res.put("code",200);
            res.put("data",competitionService.findById(id).get());
            res.put("message","Success");
            return  JSON.toJSON(res);
        }else {
            res.put("code",400);
            res.put("message","Null");
            return  JSON.toJSON(res);
        }
    }


}
