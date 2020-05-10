package werls.scis.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisClass;
import werls.scis.service.ClassServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.controller.admin
 * @Description: TODO
 * @date Date : 2020年04月11日 0:33
 */
@RestController
@RequestMapping("/admin/class")
public class ClassAdminController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ClassServiceImpl service;


    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisClass scisClass) {
        Map<String, Object> res = new HashMap<>(16);
        res.put("status", 200);
        res.put("data", service.save(scisClass));
        return res;
    }

    @PostMapping("/del")
    public Map<String, Object> del(@RequestBody ScisClass scisClass) {
        Map<String, Object> res = new HashMap<>(16);
        try {
            service.deleteById(scisClass.getId());
            res.put("status", 200);
            res.put("message", "ok");
        } catch (Exception e) {
            res.put("status", 403);
            res.put("message", "班级还有学生，拒绝删除");
            logger.warn("{},班级还有学生,异常信息：{}", scisClass.getName(), e.toString());
        }
        return res;
    }


    @PostMapping("/repeat")
    public Map<String, Object> findByNameRepeat(@RequestParam("name") String name) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        Optional<ScisClass> classOptional = service.findByName(name);
        if (classOptional.isPresent()) {
            res.put("data", "true");
        } else {
            res.put("data", "false");
        }
        return res;
    }
}
