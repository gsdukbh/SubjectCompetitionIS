package werls.scis.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisCollege;
import werls.scis.service.CollegeServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.admin
 * @Description: TODO
 * @date Date : 2020年04月10日 21:30
 */
@RestController
@RequestMapping("/admin/college")
public class CollegeAdminController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CollegeServiceImpl service;

    @PostMapping("/find")
    public Map<String, Object> find(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", defaultValue = "20") Integer size,
                                    @RequestParam(name = "name", defaultValue = "") String name) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<ScisCollege> colleges;
        if (!"".equals(name)) {
            colleges = service.findByCollegeNameLike(name, pageable);
        } else {
            colleges = service.findAll(pageable);
        }
        res.put("content", colleges.getContent());
        res.put("totalElements", colleges.getTotalElements());
        res.put("status", 200);
        return res;
    }


    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisCollege college) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("data", service.save(college));
        return res;
    }

    @PostMapping("/del")
    public Map<String, Object> del(@RequestBody ScisCollege college) {
        Map<String, Object> res = new HashMap<>(16);
        try {
            service.deleteById(college.getId());
            res.put("status", 200);
        } catch (Exception e) {
            res.put("status", 403);
            res.put("message", "该学院还有其他专业信息，拒绝删除");
            logger.warn("{}还有其他专业信息,异常信息：{}", college.getName(), e.toString());
        }
        return res;
    }

    @PostMapping("/delAll")
    public Map<String, Object> delAll(@RequestBody List<ScisCollege> colleges) {
        Map<String, Object> res = new HashMap<>(16);
        try {
            service.deleteAll(colleges);
            res.put("status", 200);
            res.put("message", "ok");
        } catch (Exception e) {
            res.put("status", 403);
            res.put("message", "学院还有其他专业信息，拒绝删除");
            logger.warn("学院还有其他专业信息,异常信息：{}", e.toString());
        }
        return res;
    }

    @PostMapping("/repeat")
    public Map<String, Object> findByName(@RequestParam("name") String name) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        Optional<ScisCollege> college = service.findByCollegeName(name);
        if (college.isPresent()) {
            res.put("CID", college.get().getId());
            res.put("data", "true");
        } else {
            res.put("data", "false");
        }
        return res;
    }
}
