package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.service.AnnouncementServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.teacher
 * @Description: TODO
 * @date Date : 2020年03月15日 16:28
 */
@RestController
@RequestMapping("/tea")
public class AnnouncementController {
    @Autowired
    AnnouncementServiceImpl service;


    @PostMapping("/announcement/save")
    public String save(ScisAnnouncement announcement){
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        if (announcement != null){
            service.save(announcement);
            res.put("code",200);
            res.put("message","Success");
            return JSON.toJSONString(res);
        }else {
            res.put("code",400);
            res.put("message","Null");
            return  JSON.toJSONString(res);
        }
    }

    @GetMapping("/announcement/findAll")
    public Page<ScisAnnouncement> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                          @RequestParam(name = "size", defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("time"));
        return service.findAll(pageable);
    }
}
