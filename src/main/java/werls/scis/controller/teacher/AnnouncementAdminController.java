package werls.scis.controller.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.dao.pojo.ScisReply;
import werls.scis.service.AnnouncementServiceImpl;

import java.util.List;
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
@RequestMapping("/tea/announcement")
public class AnnouncementAdminController {
    @Autowired
    AnnouncementServiceImpl service;

    @PostMapping("/findAll")
    public Map<String, Object> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size,
                                       @RequestParam(name = "title", defaultValue = "") String title,
                                       @RequestParam(name = "type", defaultValue = "") String type,
                                       @RequestParam(name = "from", defaultValue = "") String from) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("time").descending());
        Page<ScisAnnouncement> announcements;
        if (!"".equals(title) && !"".equals(type) && !"".equals(from)) {

            announcements = service.findByTitleContainingAndTypeContainingAndFrom(title, type, from, pageable);
        } else if (!"".equals(title) && !"".equals(type)) {

            announcements = service.findByTitleContainingAndTypeContaining(title, type, pageable);
        } else if (!"".equals(title) && !"".equals(from)) {

            announcements = service.findByTitleContainingAndFrom(title, from, pageable);
        } else if (!"".equals(type) && !"".equals(from)) {

            announcements = service.findByTypeContainingAndFrom(type, from, pageable);
        } else if (!"".equals(type)) {
            announcements = service.findByTypeContaining(type, pageable);
        } else if (!"".equals(from)) {
            announcements = service.findByFrom(from, pageable);
        } else if (!"".equals(title)) {
            announcements = service.findByTitleContaining(title, pageable);
        } else {

            announcements = service.findAll(pageable);
        }
//        System.out.println(announcements.getContent().get(0).getTitle());
        res.put("status", 200);
        res.put("message", "Success");
        res.put("content", announcements.getContent());
        res.put("totalElements", announcements.getTotalElements());
        return res;
    }
    @PostMapping("/deleteAll")
    public Map<String, Object> deleteAll( @RequestBody  List<ScisAnnouncement> announcementList) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("message", "Success");
        service.deleteAll(announcementList);
        return res;

    }

    @PostMapping("/save")
    public String save(@RequestBody JSONObject json) {
        ScisAnnouncement announcement = JSONObject.toJavaObject(json, ScisAnnouncement.class);
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        if (announcement != null) {
            res.put("status", 200);
            res.put("message", "Success");
            res.put("result", service.save(announcement));
            return JSON.toJSONString(res);
        } else {
            res.put("status", 400);
            res.put("message", "Null");
            return JSON.toJSONString(res);
        }
    }

    @GetMapping("/deleteById/{id}")
    public Map<String, Object> deleteById(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        res.put("status", 200);
        res.put("message", "Success");
        service.deleteById(id);
        return res;
    }
}
