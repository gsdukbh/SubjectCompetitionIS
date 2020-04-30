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
import werls.scis.util.Tools;

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
    @Autowired
    Tools tools;

    @GetMapping("/findType")
    public Map<String, Object> findType() {
        Map<String, Object> res = new ConcurrentHashMap<>(10);
        res.put("status", 200);
        res.put("message", "Success");
        List<Map<String, Object>> ret = new ArrayList<>();
        List<String> t = service.findType();
        return tools.getStringObjectMap(res, ret, t);
    }

    @PostMapping("/findAllA")
    public Map<String, Object> findAllA(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "20") Integer size,
                                        @RequestParam(name = "title", defaultValue = "") String title,
                                        @RequestParam(name = "type", defaultValue = "") String type,
                                        @RequestParam(name = "from", defaultValue = "") String from) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("time").descending());
        Page<ScisAnnouncement> announcements;
        if (!"".equals(title) && !"".equals(type) && !"".equals(from)) {

            announcements = service.findByTitleContainingAndTypeContainingAndFromAndStatus(title, type, from, "0", pageable);
        } else if (!"".equals(title) && !"".equals(type)) {

            announcements = service.findByTitleContainingAndTypeContainingAndStatus(title, type, "0", pageable);
        } else if (!"".equals(title) && !"".equals(from)) {

            announcements = service.findByTitleContainingAndFromAndStatus(title, from, "0", pageable);
        } else if (!"".equals(type) && !"".equals(from)) {

            announcements = service.findByTypeContainingAndFromAndStatus(type, from, "0", pageable);
        } else if (!"".equals(type)) {
            announcements = service.findByTypeContainingAndStatus(type, "0", pageable);
        } else if (!"".equals(from)) {
            announcements = service.findByFromAndStatus(from, "0", pageable);
        } else if (!"".equals(title)) {
            announcements = service.findByTitleContainingAndStatus(title, "0", pageable);
        } else {

            announcements = service.findAllByStatus("0", pageable);
        }
//        System.out.println(announcements.getContent().get(0).getTitle());
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
        return tools.getStringObjectMap(res, ret, strings);
    }


}
