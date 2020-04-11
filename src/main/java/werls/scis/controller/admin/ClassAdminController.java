package werls.scis.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import werls.scis.dao.pojo.ScisClass;
import werls.scis.service.ClassServiceImpl;

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
    @Autowired
    ClassServiceImpl service;


    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody ScisClass scisClass) {
        Map<String, Object> res = new ConcurrentHashMap<>(16);
        res.put("status", 200);
        res.put("data", service.save(scisClass));
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
