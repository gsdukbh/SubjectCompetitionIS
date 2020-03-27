package werls.scis.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisProblem;
import werls.scis.service.ProblemServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller
 * @Description: TODO
 * @date Date : 2020年03月20日 17:09
 */
@RestController
@RequestMapping("/public/problem")
public class ProblemController {

    @Autowired
    ProblemServiceImpl service;


    @GetMapping("/find/competition/{id}")
//    @Cacheable(value = "problemCompetition",key = "'key:'+#id",unless = "#result ==null")
    public Map<String, Object> findByCompetitionId(@PathVariable Integer id,
                                                   @RequestParam(name = "size", defaultValue = "20") Integer size,
                                                   @RequestParam(name = "page", defaultValue = "0") Integer page){
        Map<String, Object> res=new ConcurrentHashMap<>(10);
        Pageable pageable= PageRequest.of(page, size, Sort.by("problem_time"));
        Page<ScisProblem> problems=service.findByCompetitionId(id,pageable);
        if (problems!=null){
            res.put("status",200);
            res.put("message","success");
            res.put("totalElements",problems.getTotalElements());
            res.put("content",problems.getContent());
            return res;
        }else {
            res.put("status",403);
            res.put("message","fail");
            return res;
        }

    }
    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject json){
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        if (json !=null){
            ScisProblem scisProblem=JSONObject.toJavaObject(json,ScisProblem.class);
            service.save(scisProblem);
            res.put("status", 200);
            res.put("message", "success");
            return res;
        }else {
            res.put("status", 403);
            res.put("message", "fail");
            return res;
        }
    }
}
