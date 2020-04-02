package werls.scis.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisAnnouncement;
import werls.scis.dao.pojo.ScisUser;
import werls.scis.service.AnnouncementServiceImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.open
 * @Description: TODO
 * @date Date : 2020年04月01日 13:44
 */
@RestController
@RequestMapping("/public/announcement")
public class AnnouncementController {

    @Autowired
    AnnouncementServiceImpl service;

    @GetMapping("/findType")
    public Map<String, Object> findType() {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        res.put("status", 200);
        res.put("message", "Success");
        List<Map<String, Object>> ret = new ArrayList<>();
        List<String> t = service.findType();
        return getStringObjectMap(res, ret, t);
    }

    @PostMapping("/findAll")
    public Map<String, Object> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                       @RequestParam(name = "size", defaultValue = "20") Integer size,
                                       @RequestParam(name = "title", defaultValue = "") String title,
                                       @RequestParam(name = "type", defaultValue = "") String type,
                                       @RequestParam(name = "from", defaultValue = "") String from) {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
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
        } else if (!"".equals(type) || !"".equals(from) || "".equals(title)) {
            announcements = service.findByTitleContainingOrTypeContainingOrFrom(title, type, from, pageable);
        } else {
            announcements = service.findAll(pageable);
        }
        res.put("status", 200);
        res.put("message", "Success");
        res.put("content", announcements.getContent());
        res.put("totalElements", announcements.getTotalElements());
        return res;
    }

    @GetMapping("/findById/{id}")
    public Map<String, Object> findById(@PathVariable Integer id) {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        Optional<ScisAnnouncement> announcement = service.findById(id);
        if (announcement.isPresent()) {
            ScisAnnouncement scisAnnouncement = announcement.get();
            scisAnnouncement.setScisUser(null);
            res.put("status", 200);
            res.put("message", "Success");
            res.put("data", scisAnnouncement);
            return res;
        } else {
            res.put("status", 404);
            res.put("message", "fail");
            return res;
        }
    }
    @GetMapping("/findTitle")
    public Map<String, Object> findTitle() {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        List<Map<String, Object>> ret = new ArrayList<>();
        res.put("status", 200);
        res.put("message", "Success");
        List<String> strings = service.findTitle();
        return getStringObjectMap(res, ret, strings);
    }

    private Map<String, Object> getStringObjectMap(Map<String, Object> res, List<Map<String, Object>> ret, List<String> strings) {
        for (String string : strings) {
            Map<String, Object> type = new ConcurrentHashMap<>(1);
            type.put("value", string);
            ret.add(type);
        }
        res.put("type", ret);
        return res;
    }
}